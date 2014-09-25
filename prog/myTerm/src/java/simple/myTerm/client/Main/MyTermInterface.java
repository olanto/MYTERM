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
package simple.myTerm.client.Main;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.client.Login.requests.LoginServiceAsync;
import simple.myTerm.client.Main.AdminInterface.AdminInterface;
import simple.myTerm.client.Main.ReaderInterface.ReaderInterface;
import simple.myTerm.client.Main.RedactorInterface.RedactorInterface;
import simple.myTerm.client.Main.RevisorInterface.RevisorInterface;
import simple.myTerm.client.Main.StatusPanels.FooterStatusPanel;
import simple.myTerm.client.Main.StatusPanels.HeaderStatusPanel;
import simple.myTerm.client.Main.cookiesManager.MyTermCookies;
import simple.myTerm.client.Main.cookiesManager.MyTermCookiesNamespace;
import simple.myTerm.shared.UserDto;

/**
 * Main entry point.
 *
 * @author nizar ghoula - simple
 */
public class MyTermInterface implements EntryPoint {

    /**
     * Creates a new instance of MyTermInterface
     */
    private final LoginServiceAsync loginService = GWT.create(LoginService.class);

    public MyTermInterface() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        loginService.isAuthenticated(new AsyncCallback<UserDto>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(UserDto user) {
                if (user == null) {
                    Window.alert("User is lost or session is lost: reload");
                    Window.Location.assign(GWT.getHostPageBaseURL() + "myTermLoginCtrl");
                } else {
                    showMain(user);
                }
            }
        });
    }

    public void showMain(UserDto user) {
        initCookies();
        HeaderStatusPanel headerPanel = new HeaderStatusPanel(user);
        FooterStatusPanel statusPanel = new FooterStatusPanel();

        RootPanel.get("header").add(headerPanel);
        switch (user.getRole()) {
            case "ADMIN":
                AdminInterface vpan = new AdminInterface();
                RootPanel.get("main").add(vpan);
                vpan.adjustSize(Window.getClientHeight() - headerPanel.getOffsetHeight() - statusPanel.getOffsetHeight());
                break;
            case "READER":
                ReaderInterface rpan = new ReaderInterface();
                RootPanel.get("main").add(rpan);
                rpan.adjustSize(Window.getClientHeight() - headerPanel.getOffsetHeight() - statusPanel.getOffsetHeight());
                break;
            case "REVISOR":
                RevisorInterface rvpan = new RevisorInterface();
                RootPanel.get("main").add(rvpan);
                rvpan.adjustSize(Window.getClientHeight() - headerPanel.getOffsetHeight() - statusPanel.getOffsetHeight());
                break;
            case "REDACTOR":
                RedactorInterface vdpan = new RedactorInterface();
                RootPanel.get("main").add(vdpan);
                vdpan.adjustSize(Window.getClientHeight() - headerPanel.getOffsetHeight() - statusPanel.getOffsetHeight());
                break;
        }
        RootPanel.get("footer").add(statusPanel);
    }
    private void initCookies() {
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangS, "English");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangT, "French");
    }
}
