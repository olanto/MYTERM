/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client;

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
import olanto.myTerm.client.AdminInterface.AdminInterface;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.ReaderInterface.ReaderInterface;
import olanto.myTerm.client.RedactorInterface.RedactorInterface;
import olanto.myTerm.client.RevisorInterface.RevisorInterface;
import olanto.myTerm.client.StatusPanels.FooterStatusPanel;
import olanto.myTerm.client.StatusPanels.HeaderStatusPanel;
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
                Window.alert("User logged in!!!:"+user.getEmail()+user.getSessionID());
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
        RootPanel.get().add(vp);
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
        vp.clear();
        HeaderStatusPanel headerPanel = new HeaderStatusPanel(user);
        FooterStatusPanel statusPanel = new FooterStatusPanel();
        vp.add(headerPanel);

        switch (user.getRole()) {
            case "ADMIN":
                AdminInterface vpan = new AdminInterface();
                vp.add(vpan);
                break;
            case "READER":
                ReaderInterface rpan = new ReaderInterface();
                vp.add(rpan);
                break;
            case "REVISOR":
                RevisorInterface rvpan = new RevisorInterface();
                vp.add(rvpan);
                break;
            case "REDACTOR":
                RedactorInterface vdpan = new RedactorInterface();
                vp.add(vdpan);
                break;
        }
        vp.add(statusPanel);
    }

    private void initCookies() {
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangS, "English");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangT, "French");
        MyTermCookies.initCookie(MyTermCookiesNamespace.SessionID, null);
    }
}
