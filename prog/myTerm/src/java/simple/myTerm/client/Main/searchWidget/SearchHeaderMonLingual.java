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
package simple.myTerm.client.Main.searchWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.client.Login.requests.LoginServiceAsync;
import simple.myTerm.client.Main.langWidget.LangList;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderMonLingual extends HorizontalPanel {

    public Label termLabel = new Label("Input your search expression: ");
    public TextBox searchField = new TextBox();
    public LangList lang = new LangList();
    public Button btnSend = new Button("Search");
    public Button logout = new Button("Logout");
    final LoginServiceAsync loginService = GWT.create(LoginService.class);

    public SearchHeaderMonLingual() {
        add(termLabel);
        add(new HTML("&nbsp;"));
        add(searchField);
        add(new HTML("&nbsp;"));
        add(new Label("Language "));
        add(lang);
        add(new HTML("&nbsp;"));
        add(btnSend);
        add(new HTML("&nbsp;"));
        add(logout);
        setStyleName("searchMenu");
        logout.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginService.logout(new AsyncCallback() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to logout user");
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
