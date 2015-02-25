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
package olanto.myTerm.client.ContainerPanels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.Lists.OwnerRolesList;
import olanto.myTerm.client.Lists.OwnersList;
import olanto.myTerm.client.Lists.ResourcesList;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderUSER_RESOURCE extends HorizontalPanel {

    public OwnerRolesList ownerRole;
    public ResourcesList rsrcList;
    public OwnersList ownerList;
    public Button btnAdd;

    public SearchHeaderUSER_RESOURCE(HashMap<String, String> sysMsg) {
        ownerRole = new OwnerRolesList(GuiConstant.INTERFACE_LANG);
        rsrcList = new ResourcesList();
        ownerList = new OwnersList();
        btnAdd = new Button(sysMsg.get(GuiConstant.BTN_ADD));
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        add(new Label(sysMsg.get(GuiConstant.LBL_O_MAILING)));
        add(new HTML("&nbsp;"));
        add(ownerList);
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.LBL_R_NAME)));
        add(new HTML("&nbsp;"));
        add(rsrcList);
        add(new HTML("&nbsp;"));
        add(new Label(sysMsg.get(GuiConstant.LBL_O_ROLE)));
        add(new HTML("&nbsp;"));
        add(ownerRole);
        add(new HTML("&nbsp;"));
        add(btnAdd);
        btnAdd.setTitle(sysMsg.get(GuiConstant.MSG_ADD_TITLE));
        setStyleName("searchMenu");
    }
}
