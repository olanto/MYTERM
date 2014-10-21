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
package simple.client.Main.StatusPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_JUSTIFY;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import simple.client.Login.requests.LoginService;
import simple.client.Login.requests.LoginServiceAsync;
import simple.shared.UserDto;

/**
 *
 * @author nizar ghoula - simple
 */
public class HeaderStatusPanel extends HorizontalPanel {

    public Image logo = new Image("img/olanto.jpg");
    public Anchor logout = new Anchor("Logout");
    final LoginServiceAsync loginService = GWT.create(LoginService.class);

    public HeaderStatusPanel(final UserDto user) {
        setWidth(Window.getClientWidth() + "px");
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_JUSTIFY);
        add(logo);
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new HTML("Welcome to olanto's Terminology Manager"));
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new HTML("User: " + user.getFirstName()));
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(logout);
        setStyleName("headerPanel");
        logout.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginService.logout(new AsyncCallback() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to logout: " + user.getFirstName());
                    }

                    @Override
                    public void onSuccess(Object result) {
                        Window.Location.replace(GWT.getHostPageBaseURL() + "myTermLoginCtrl");
                    }
                });
            }
        });
    }
}