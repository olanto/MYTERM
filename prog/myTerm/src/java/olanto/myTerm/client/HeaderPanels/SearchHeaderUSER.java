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
import olanto.myTerm.client.Lists.OwnerRolesList;
import olanto.myTerm.client.Lists.OwnerStatusList;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderUSER extends HorizontalPanel {

    public TextBox mailingField;
    public OwnerStatusList ownerStatus;
    public OwnerRolesList ownerRole;
    public Button btnAdd;
    public Button btnDispAll;

    public SearchHeaderUSER(HashMap<String, String> sysMsg) {
        ownerStatus = new OwnerStatusList(GuiConstant.INTERFACE_LANG);
        ownerRole = new OwnerRolesList(GuiConstant.INTERFACE_LANG);
        btnAdd = new Button(sysMsg.get(GuiConstant.BTN_ADD));
        btnDispAll = new Button(sysMsg.get(GuiConstant.BTN_DISPLAY_ALL));
        mailingField = new TextBox();
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        add(new Label(sysMsg.get(GuiConstant.LBL_O_MAILING)));
        add(new HTML("&nbsp;"));
        add(mailingField);
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.LBL_O_ROLE)));
        add(new HTML("&nbsp;"));
        add(ownerRole);
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.LBL_O_STATUS)));
        add(new HTML("&nbsp;"));
        add(ownerStatus);
        add(new HTML("&nbsp;"));
        add(btnAdd);
        add(new HTML("&nbsp;"));
        add(btnDispAll);
        btnAdd.setTitle(sysMsg.get(GuiConstant.MSG_ADD_TITLE));
        btnDispAll.setTitle(sysMsg.get(GuiConstant.MSG_DISPLAY_ALL_TITLE));
        setStyleName("searchMenu");
    }
}
