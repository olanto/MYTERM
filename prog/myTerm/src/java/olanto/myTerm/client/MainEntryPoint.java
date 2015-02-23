/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client;

import olanto.myTerm.client.ServiceCalls.LoginService;
import olanto.myTerm.client.ServiceCalls.LoginServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import java.util.Map;
import olanto.myTerm.client.Interfaces.AdminInterface;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Interfaces.ReaderInterface;
import olanto.myTerm.client.Interfaces.RedactorInterface;
import olanto.myTerm.client.Interfaces.RevisorInterface;
import olanto.myTerm.client.ContainerPanels.StatusPanel;
import olanto.myTerm.client.ContainerPanels.HeaderPanel;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.OwnerDTO;

/**
 * Main entry point.
 *
 * @author simple
 */
public class MainEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }
    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    private VerticalPanel vp = new VerticalPanel();
    private LoginView view = GWT.create(LoginView.class);
    private final LoginServiceAsync loginService = GWT.create(LoginService.class);
    public static OwnerDTO userDTO;
    public static StatusPanel statusPanel = new StatusPanel();
    public static HashMap<String, String> sysMsg;
    public static HashMap<String, SysFieldDTO> sysFields;
    AsyncCallback<Map<String, SysFieldDTO>> getSysFields;
    AsyncCallback<Map<String, String>> getSysMsg;
    AsyncCallback<OwnerDTO> login = new AsyncCallback<OwnerDTO>() {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Failed to call login service");
        }

        @Override
        public void onSuccess(OwnerDTO user) {
            if (user == null) {
                showLogin();
            } else {
                String sessionID = user.getSessionID();
                String ownerID = Long.toString(user.getId());
                MyTermCookies.updateCookie(MyTermCookiesNamespace.SessionID, sessionID);
                MyTermCookies.updateCookie(MyTermCookiesNamespace.ownerID, ownerID);
                showMain(user);
            }
        }
    };
    AsyncCallback<OwnerDTO> authenticate = new AsyncCallback<OwnerDTO>() {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Failed to call login service");
        }

        @Override
        public void onSuccess(OwnerDTO user) {
            if (user == null) {
                //we can take this as a bad attempt
                view.getMessage().setWidget(new HTML("Wrong! you have x trials left"));
                enableLogin();
            } else {
                String sessionID = user.getSessionID();
                String ownerID = Long.toString(user.getId());
                MyTermCookies.updateCookie(MyTermCookiesNamespace.SessionID, sessionID);
                MyTermCookies.updateCookie(MyTermCookiesNamespace.ownerID, ownerID);
                showMain(user);
            }
        }
    };

    @Override
    public void onModuleLoad() {
        String lang = "undefined";
        if (Window.Location.getHref().contains("?lang")) {
            lang = Window.Location.getParameter("lang");
        }
        initCookies();
        if ((!lang.equalsIgnoreCase("undefined")) && (lang.length() > 1) && (lang.length() < 4)) {
            MyTermCookies.updateCookie(MyTermCookiesNamespace.INTERFACE_LANG, lang);
            GuiConstant.INTERFACE_LANG = lang;
        } else {
            GuiConstant.INTERFACE_LANG = Cookies.getCookie(MyTermCookiesNamespace.INTERFACE_LANG);
        }
        String sessionID = Cookies.getCookie(MyTermCookiesNamespace.SessionID);
        if ((sessionID == null) || (sessionID.equalsIgnoreCase("null"))) {
            showLogin();
        } else {
            checkWithServerIfSessionIdIsStillLegal();
        }
    }

    public void showLogin() {
        RootPanel.get("login").clear();
        RootPanel.get("header").clear();
        RootPanel.get("main").clear();
        RootPanel.get("footer").clear();
        RootPanel.get("header").setVisible(false);
        RootPanel.get("main").setVisible(false);
        RootPanel.get("footer").setVisible(false);
        SubmitHandler handler = new SubmitHandler();

        view.getSubmitButton().addClickHandler(handler);
        view.getEmailBox().addKeyUpHandler(handler);
        view.getPasswordBox().addKeyUpHandler(handler);

        RootPanel.get("login").add(vp);
        RootPanel.get("login").setVisible(true);
        vp.setPixelSize(Window.getClientWidth(), Window.getClientHeight());
        vp.add(view);
        vp.setCellHorizontalAlignment(view, HorizontalPanel.ALIGN_CENTER);
        vp.setCellVerticalAlignment(view, VerticalPanel.ALIGN_TOP);
    }

    // Declare a Handler for either the Submi Button Click
    // or the KeyUp for Enter on either of the two text boxes
    class SubmitHandler implements ClickHandler, KeyUpHandler {

        @Override
        public void onClick(ClickEvent event) {
            disableLogin();
            loginService.loginCheck(view.getEmailValue(), view.getPasswordValue(), authenticate);
        }

        @Override
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                disableLogin();
                loginService.loginCheck(view.getEmailValue(), view.getPasswordValue(), authenticate);
            }
        }
    }

    private void checkWithServerIfSessionIdIsStillLegal() {
        loginService.loginFromSessionServer(login);
    }

    public void disableLogin() {
        view.getMessage().clear();
        view.getSubmitButton().setText("Checking...");
        view.getSubmitButton().setEnabled(false);
        view.getEmailBox().setEnabled(false);
        view.getPasswordBox().setEnabled(false);
    }

    public void enableLogin() {
        view.getSubmitButton().setText("Login");
        view.getSubmitButton().setEnabled(true);
        view.getEmailBox().setEnabled(true);
        view.getPasswordBox().setEnabled(true);
        view.getEmailBox().setFocus(true);
    }

    public void showMain(final OwnerDTO user) {
        sysFields = new HashMap<>();
        sysMsg = new HashMap<>();
        userDTO = user;
        String sessionID = user.getSessionID();
        String ownerID = Long.toString(user.getId());

        MyTermCookies.updateCookie(MyTermCookiesNamespace.SessionID, sessionID);
        MyTermCookies.updateCookie(MyTermCookiesNamespace.ownerID, ownerID);
        RootPanel.get("login").clear();
        RootPanel.get("header").clear();
        RootPanel.get("main").clear();
        RootPanel.get("footer").clear();
        RootPanel.get("login").setVisible(false);

        getSysFields = new AsyncCallback<Map<String, SysFieldDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(Map<String, SysFieldDTO> result) {
                sysFields.putAll(result);
                getService().getSysMsgByLang(GuiConstant.INTERFACE_LANG, getSysMsg);
            }
        };
        getSysMsg = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                sysMsg.putAll(result);
                HeaderPanel headerPanel = new HeaderPanel(user, sysMsg);
                RootPanel.get("header").add(headerPanel);
                RootPanel.get("header").setVisible(true);
                switch (user.getRole()) {
                    case "ADMIN":
                        AdminInterface vpan = new AdminInterface(user.getId(), sysFields, sysMsg);
                        RootPanel.get("main").add(vpan);
                        RootPanel.get("main").setVisible(true);
                        vpan.addSelectionHandler(new SelectionHandler<Integer>() {
                            @Override
                            public void onSelection(SelectionEvent<Integer> event) {
                                History.newItem("page" + event.getSelectedItem());
                            }
                        });
                        break;
                    case "READER":
                        ReaderInterface rpan = new ReaderInterface(user.getId(), sysFields, sysMsg);
                        RootPanel.get("main").add(rpan);
                        RootPanel.get("main").setVisible(true);
                        rpan.addSelectionHandler(new SelectionHandler<Integer>() {
                            @Override
                            public void onSelection(SelectionEvent<Integer> event) {
                                History.newItem("page" + event.getSelectedItem());
                            }
                        });
                        break;
                    case "REVISOR":
                        RevisorInterface rvpan = new RevisorInterface(user.getId(), sysFields, sysMsg);
                        RootPanel.get("main").add(rvpan);
                        RootPanel.get("main").setVisible(true);
                        rvpan.addSelectionHandler(new SelectionHandler<Integer>() {
                            @Override
                            public void onSelection(SelectionEvent<Integer> event) {
                                History.newItem("page" + event.getSelectedItem());
                            }
                        });
                        break;
                    case "REDACTOR":
                        RedactorInterface vdpan = new RedactorInterface(user.getId(), sysFields, sysMsg);
                        RootPanel.get("main").add(vdpan);
                        RootPanel.get("main").setVisible(true);
                        vdpan.addSelectionHandler(new SelectionHandler<Integer>() {
                            @Override
                            public void onSelection(SelectionEvent<Integer> event) {
                                History.newItem("page" + event.getSelectedItem());
                            }
                        });
                        break;
                }
                RootPanel.get("footer").add(statusPanel);
                RootPanel.get("footer").setVisible(true);
            }
        };
        getService().getSysFieldsByLang(GuiConstant.INTERFACE_LANG, getSysFields);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void initCookies() {
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangS, "English");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangT, "French");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangSrc, "English");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangTgt, "French");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermIDlangS, "EN");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermIDlangT, "FR");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermIDlangSrc, "EN");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermIDlangTgt, "FR");
        MyTermCookies.initCookie(MyTermCookiesNamespace.Domain, "%");
        MyTermCookies.initCookie(MyTermCookiesNamespace.Resource, "ALL");
        MyTermCookies.initCookie(MyTermCookiesNamespace.SessionID, null);
        MyTermCookies.initCookie(MyTermCookiesNamespace.ownerID, "0");
        MyTermCookies.initCookie(MyTermCookiesNamespace.INTERFACE_LANG, "EN");
    }
}
