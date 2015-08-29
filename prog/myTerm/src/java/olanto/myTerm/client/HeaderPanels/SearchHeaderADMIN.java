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
package olanto.myTerm.client.HeaderPanels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.Lists.DomainList;
import olanto.myTerm.client.Lists.LangList;
import olanto.myTerm.client.Lists.ResourcesList;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderADMIN extends HorizontalPanel {

    public Label termLabel;
    public TextBox searchField;
    public LangList langSrc;
    public ResourcesList rsrc;
    public DomainList dom;
    public Button btnSearch;

    public SearchHeaderADMIN(HashMap<String, String> sysMsg) {
        termLabel = new Label(sysMsg.get(GuiConstant.MSG_SEARCH_INPUT));
        searchField = new TextBox();
        dom = new DomainList();
        btnSearch = new Button(sysMsg.get(GuiConstant.BTN_SEARCH));
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        add(termLabel);
        add(new HTML("&nbsp;"));
        add(searchField);
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(btnSearch);
        btnSearch.setTitle(sysMsg.get(GuiConstant.MSG_SEARCH_TITLE));
        add(new HTML("&nbsp;"));
        langSrc = new LangList();
        add(new Label(sysMsg.get(GuiConstant.MSG_SOURCE_LANG)));
        add(new HTML("&nbsp;"));
        add(langSrc);
        add(new HTML("&nbsp;"));
        rsrc = new ResourcesList();
        add(new Label(sysMsg.get(GuiConstant.MSG_RESOURCE)));
        add(new HTML("&nbsp;"));
        add(rsrc);
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.MSG_DOMAIN)));
        add(new HTML("&nbsp;"));
        add(dom);
        add(new HTML("&nbsp;"));
        setStyleName("searchMenu");
    }
}
