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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Interfaces.AdminInterface;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Interfaces.ReaderInterface;
import olanto.myTerm.client.Interfaces.RedactorInterface;
import olanto.myTerm.client.Interfaces.RevisorInterface;
import olanto.myTerm.client.ContainerPanels.FooterStatusPanel;
import olanto.myTerm.client.ContainerPanels.HeaderStatusPanel;
import olanto.myTerm.shared.UserDTO;

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
    AsyncCallback<UserDTO> login = new AsyncCallback<UserDTO>() {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Failed to call login service");
        }

        @Override
        public void onSuccess(UserDTO user) {
            if (user == null) {
                showLogin();
            } else {
                showMain(user);
            }
        }
    };
    AsyncCallback<UserDTO> authenticate = new AsyncCallback<UserDTO>() {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Failed to call login service");
        }

        @Override
        public void onSuccess(UserDTO user) {
            if (user == null) {
                //we can take this as a bad attempt
                view.getMessage().setWidget(new HTML("Wrong! you have x trials left"));
                enableLogin();
            } else {
                String sessionID = user.getSessionID();
                MyTermCookies.updateCookie(MyTermCookiesNamespace.SessionID, sessionID);
                showMain(user);
            }
        }
    };

    @Override
    public void onModuleLoad() {
        initCookies();
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

        view.getRegisterLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                PopupPanel pop = new PopupPanel(true);
                pop.setWidget(new HTML("Please register here..."));
                pop.center();
            }
        });
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
        view.getRegisterLink().setEnabled(false);
    }

    public void enableLogin() {
        view.getSubmitButton().setText("Login");
        view.getSubmitButton().setEnabled(true);
        view.getEmailBox().setEnabled(true);
        view.getPasswordBox().setEnabled(true);
        view.getRegisterLink().setEnabled(true);
        view.getEmailBox().setFocus(true);
    }

    public void showMain(UserDTO user) {
        RootPanel.get("login").clear();
        RootPanel.get("header").clear();
        RootPanel.get("main").clear();
        RootPanel.get("footer").clear();

        RootPanel.get("login").setVisible(false);
        HeaderStatusPanel headerPanel = new HeaderStatusPanel(user);
        FooterStatusPanel statusPanel = new FooterStatusPanel();
        RootPanel.get("header").add(headerPanel);
        RootPanel.get("header").setVisible(true);
        switch (user.getRole()) {
            case "ADMIN":
                AdminInterface vpan = new AdminInterface();
                RootPanel.get("main").add(vpan);
                RootPanel.get("main").setVisible(true);
                break;
            case "READER":
                ReaderInterface rpan = new ReaderInterface();
                RootPanel.get("main").add(rpan);
                RootPanel.get("main").setVisible(true);
                break;
            case "REVISOR":
                RevisorInterface rvpan = new RevisorInterface();
                RootPanel.get("main").add(rvpan);
                RootPanel.get("main").setVisible(true);
                break;
            case "REDACTOR":
                RedactorInterface vdpan = new RedactorInterface();
                RootPanel.get("main").add(vdpan);
                RootPanel.get("main").setVisible(true);
                break;
        }
        RootPanel.get("footer").add(statusPanel);
        RootPanel.get("footer").setVisible(true);
    }

    private void initCookies() {
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangS, "English");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangT, "French");
        MyTermCookies.initCookie(MyTermCookiesNamespace.SessionID, null);
    }
}
