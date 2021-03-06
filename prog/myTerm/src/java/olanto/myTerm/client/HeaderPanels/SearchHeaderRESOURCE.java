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
import olanto.myTerm.client.Lists.ResourcePrivacyList;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderRESOURCE extends HorizontalPanel {

    public TextBox rsrcField;
    public ResourcePrivacyList rsrcPrivay;
    public Button btnAdd;
    public Button btnDispAll;

    public SearchHeaderRESOURCE(HashMap<String, String> sysMsg) {
        rsrcPrivay = new ResourcePrivacyList(GuiConstant.INTERFACE_LANG);
        btnAdd = new Button(sysMsg.get(GuiConstant.BTN_ADD));
        btnDispAll = new Button(sysMsg.get(GuiConstant.BTN_DISPLAY_ALL));
        rsrcField = new TextBox();
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        add(new Label(sysMsg.get(GuiConstant.LBL_R_NAME)));
        add(new HTML("&nbsp;"));
        add(rsrcField);
        add(new HTML("&nbsp;"));
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.LBL_R_PRIVACY)));
        add(new HTML("&nbsp;"));
        add(rsrcPrivay);
        add(new HTML("&nbsp;"));
        add(btnAdd);
        add(new HTML("&nbsp;"));
        add(btnDispAll);
        btnAdd.setTitle(sysMsg.get(GuiConstant.MSG_ADD_TITLE));
        btnDispAll.setTitle(sysMsg.get(GuiConstant.MSG_DISPLAY_ALL_TITLE));
        setStyleName("searchMenu");
    }
}
