/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package simple.myTerm.client.Login;

import simple.myTerm.client.Login.View.LoginView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.client.Login.requests.LoginServiceAsync;
import simple.myTerm.client.Main.cookiesManager.MyTermCookies;
import simple.myTerm.client.Main.cookiesManager.MyTermCookiesNamespace;
import simple.myTerm.shared.UserDto;

/**
 *
 * @author simple
 */
public class MainLogin implements EntryPoint {

    private VerticalPanel vp = new VerticalPanel();
    private LoginView view = GWT.create(LoginView.class);
    private final LoginServiceAsync loginService = GWT.create(LoginService.class);

    @Override
    public void onModuleLoad() {
        // quick check  if this user is
        // already authenticated
        MyTermCookies.initCookie(MyTermCookiesNamespace.UserProfile, "public");
        loginService.isAuthenticated(new AsyncCallback<UserDto>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(UserDto user) {
                if (user == null) {
                    showLogin();
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.UserProfile, user.getRole());
                    Window.Location.replace(GWT.getHostPageBaseURL() + "myTermLoginCtrl");
                }
            }
        });
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

        RootLayoutPanel.get().add(vp);
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
            authenticate();
        }

        @Override
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                authenticate();
            }
        }
    }

    public void authenticate() {
        disableLogin();
        loginService.authenticate(view.getEmailValue(), view.getPasswordValue(), new AsyncCallback<UserDto>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(UserDto user) {
                if (user == null) {
                    //we can take this as a bad attempt
                    //add exceptions or other checks for better UX
                    //we will just enable the form for another try..
                    view.getMessage().setWidget(new HTML("Wrong! you have x trials left"));
                    enableLogin();
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.UserProfile, user.getRole());
                    Window.Location.assign(GWT.getHostPageBaseURL() + "myTermLoginCtrl");
                }
            }
        });
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
}
