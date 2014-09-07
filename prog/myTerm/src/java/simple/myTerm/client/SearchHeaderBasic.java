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
package simple.myTerm.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderBasic extends HorizontalPanel {

    public Label termLabel = new Label("Input your search expression: ");
    public TextBox searchField = new TextBox();
    public LangList langSrc = new LangList("source");
    public LangList langTgt = new LangList("target");
    public Button btnSend = new Button("Search");
    public Button inventory = new Button("Inventory");
    
    public SearchHeaderBasic(){
        add(termLabel);
        add(new HTML("&nbsp;"));
        add(searchField);
        add(new HTML("&nbsp;"));
        add(new Label("Source Lang. "));
        add(langSrc);
        add(new HTML("&nbsp;"));
        add(new Label("Target Lang. "));
        add(langTgt);
        add(new HTML("&nbsp;"));
        add(btnSend);
        add(new HTML("&nbsp;"));
        add(inventory);
        setStyleName("searchMenu");
        langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangS, langSrc.getItemText(langSrc.getSelectedIndex()));
            }
        });
        langTgt.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangS, langTgt.getItemText(langTgt.getSelectedIndex()));
            }
        });
        langSrc.setSelectedIndex(langSrc.getlangIndex(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangS)));
    }
}
