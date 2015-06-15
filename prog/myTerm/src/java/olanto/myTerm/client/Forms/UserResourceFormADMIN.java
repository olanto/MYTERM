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
package olanto.myTerm.client.Forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.Lists.OwnerRolesList;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.client.Lists.OwnersList;
import olanto.myTerm.client.Lists.ResourcesList;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.UserResourceDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula
 */
public class UserResourceFormADMIN extends VerticalPanel {

    private Grid rform;
    private Label label_rn;
    private Label label_row;
    private Label label_rowrl;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel rnPanel;
    private HorizontalPanel rowPanel;
    private HorizontalPanel rowrlPanel;
    public Button save;
    public Button escape;
    public Button delete;
    public OwnersList resOwner;
    public OwnerRolesList resOwnerRoles;
    public ResourcesList resList;
    private BooleanWrap isLocallyEdited = new BooleanWrap();

    public UserResourceFormADMIN(HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        label_rn = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_R_NAME), sFields.get(GuiConstant.R_NAME));
        label_row = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_R_OWNER), sFields.get(GuiConstant.R_OWNER_ID));
        label_rowrl = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_ROLE), sFields.get(GuiConstant.O_ROLE));

        resOwner = new OwnersList(isEdited, isLocallyEdited);
        resOwnerRoles = new OwnerRolesList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited);
        resList = new ResourcesList(isEdited, isLocallyEdited);

        ctrlPanel = new HorizontalPanel();
        rnPanel = new HorizontalPanel();
        rowPanel = new HorizontalPanel();
        rowrlPanel = new HorizontalPanel();

        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));

        rform = new Grid(7, 2);
        add(rform);

        rform.setCellSpacing(4);
        rform.setStyleName("edpanel");

        rform.setWidget(0, 0, rnPanel);
        rform.setWidget(2, 0, rowPanel);
        rform.setWidget(4, 0, rowrlPanel);
        rform.setWidget(6, 0, ctrlPanel);

        rnPanel.add(label_rn);
        rnPanel.add(resList);

        rowPanel.add(label_row);
        rowPanel.add(resOwner);

        rowrlPanel.add(label_rowrl);
        rowrlPanel.add(resOwnerRoles);

        ctrlPanel.add(escape);
        ctrlPanel.add(delete);
        ctrlPanel.add(save);

        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes without submit");
    }

    public void adjustSize(int wdth) {
        setWidth(wdth + "px");
        setCellHorizontalAlignment(rform, HorizontalPanel.ALIGN_CENTER);
        int w = wdth * 5 / 6;
        rnPanel.setWidth(w + "px");
        rnPanel.setCellHorizontalAlignment(label_rn, HorizontalPanel.ALIGN_LEFT);
        rnPanel.setCellHorizontalAlignment(resList, HorizontalPanel.ALIGN_RIGHT);

        rowPanel.setWidth(w + "px");
        rowPanel.setCellHorizontalAlignment(label_row, HorizontalPanel.ALIGN_LEFT);
        rowPanel.setCellHorizontalAlignment(resOwner, HorizontalPanel.ALIGN_RIGHT);

        rowrlPanel.setWidth(w + "px");
        rowrlPanel.setCellHorizontalAlignment(label_rowrl, HorizontalPanel.ALIGN_LEFT);
        rowrlPanel.setCellHorizontalAlignment(resOwnerRoles, HorizontalPanel.ALIGN_RIGHT);

        ctrlPanel.setWidth(w + "px");
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_CENTER);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_RIGHT);

        resList.setWidth(w * 2 / 3 + "px");
        resOwner.setWidth(w * 2 / 3 + "px");
        resOwnerRoles.setWidth(w * 2 / 3 + "px");
    }

    public void setContentFromUserResourceDTO(UserResourceDTO userResourceDTO, BooleanWrap isEdited) {
        int w = this.getOffsetWidth();
        w = w * 5 / 6;
        rowPanel.remove(resOwner);
        rnPanel.remove(resList);
        rowrlPanel.remove(resOwnerRoles);

        resList = new ResourcesList(userResourceDTO.getIdResource(), isEdited, isLocallyEdited);
        resOwner = new OwnersList(userResourceDTO.getIdOwner(), isEdited, isLocallyEdited);
        resOwnerRoles = new OwnerRolesList(GuiConstant.INTERFACE_LANG, userResourceDTO.getOwnerRole(), isEdited, isLocallyEdited);

        rowPanel.add(resOwner);
        rnPanel.add(resList);
        rowrlPanel.add(resOwnerRoles);

        resList.setWidth(w * 2 / 3 + "px");
        resOwner.setWidth(w * 2 / 3 + "px");
        resOwnerRoles.setWidth(w * 2 / 3 + "px");

        rowPanel.setWidth(w + "px");
        rowPanel.setCellHorizontalAlignment(label_row, HorizontalPanel.ALIGN_LEFT);
        rowPanel.setCellHorizontalAlignment(resOwner, HorizontalPanel.ALIGN_RIGHT);

        rnPanel.setWidth(w + "px");
        rnPanel.setCellHorizontalAlignment(label_rn, HorizontalPanel.ALIGN_LEFT);
        rnPanel.setCellHorizontalAlignment(resList, HorizontalPanel.ALIGN_RIGHT);

        rowrlPanel.setWidth(w + "px");
        rowrlPanel.setCellHorizontalAlignment(label_rowrl, HorizontalPanel.ALIGN_LEFT);
        rowrlPanel.setCellHorizontalAlignment(resOwnerRoles, HorizontalPanel.ALIGN_RIGHT);
    }

    public void setReadOnly(Boolean edit) {
        resOwner.setEnabled(!edit);
        resList.setEnabled(!edit);
        resOwnerRoles.setEnabled(!edit);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public void setUserResourceDTOFromContent(UserResourceDTO userResourceDTO) {
        userResourceDTO.setIdResource(Long.parseLong(resList.getValue(resList.getSelectedIndex())));
        userResourceDTO.setIdOwner(Long.parseLong(resOwner.getValue(resOwner.getSelectedIndex())));
        userResourceDTO.setOwnerRole(resOwnerRoles.getValue(resOwnerRoles.getSelectedIndex()));
    }
}
