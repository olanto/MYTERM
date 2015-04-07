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
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.client.Lists.OwnerRolesList;
import olanto.myTerm.client.Lists.OwnerStatusList;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.OwnerDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class OwnerFormADMIN extends VerticalPanel {

    private Grid oform;
    private Label label_fn;
    private Label label_ln;
    private Label label_email;
    private Label label_hash;
    private Label label_role;
    private Label label_st;
    private TextBoxMyTerm text_fn;
    private TextBoxMyTerm text_ln;
    private TextBoxMyTerm text_email;
    private TextBoxMyTerm text_hash;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel fnPanel;
    private HorizontalPanel lnPanel;
    private HorizontalPanel mailPanel;
    private HorizontalPanel hashPanel;
    private HorizontalPanel rolePanel;
    private HorizontalPanel stPanel;
    public Button save;
    public Button escape;
    public Button delete;
    public OwnerStatusList ownerStatus;
    public OwnerRolesList ownerRole;
    private static AsyncCallback<Boolean> ownersUsageCallback;
    private BooleanWrap isLocallyEdited = new BooleanWrap();

    public OwnerFormADMIN(HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        label_fn = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_FIRST_NAME), sFields.get(GuiConstant.O_FIRST_NAME));
        label_ln = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_LAST_NAME), sFields.get(GuiConstant.O_LAST_NAME));
        label_email = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_MAILING), sFields.get(GuiConstant.O_MAILING));
        label_hash = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_PWD), sFields.get(GuiConstant.O_PWD));
        label_role = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_ROLE), sFields.get(GuiConstant.O_ROLE));
        label_st = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_STATUS), sFields.get(GuiConstant.O_STATUS));
        text_fn = new TextBoxMyTerm(GuiConstant.O_FIRST_NAME, sFields, isEdited);
        text_ln = new TextBoxMyTerm(GuiConstant.O_LAST_NAME, sFields, isEdited);
        text_email = new TextBoxMyTerm(GuiConstant.O_MAILING, sFields, isEdited);
        text_hash = new TextBoxMyTerm(GuiConstant.O_PWD, sFields, isEdited);
        ownerRole = new OwnerRolesList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited);
        ownerStatus = new OwnerStatusList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited);

        ctrlPanel = new HorizontalPanel();
        fnPanel = new HorizontalPanel();
        lnPanel = new HorizontalPanel();
        mailPanel = new HorizontalPanel();
        hashPanel = new HorizontalPanel();
        rolePanel = new HorizontalPanel();
        stPanel = new HorizontalPanel();
        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));

        oform = new Grid(7, 2);
        add(oform);

        oform.setCellSpacing(4);
        oform.setStyleName("edpanel");
        oform.setWidget(0, 0, fnPanel);
        oform.setWidget(1, 0, lnPanel);
        oform.setWidget(2, 0, mailPanel);
        oform.setWidget(3, 0, hashPanel);
        oform.setWidget(4, 0, rolePanel);
        oform.setWidget(5, 0, stPanel);
        oform.setWidget(6, 0, ctrlPanel);

        fnPanel.add(label_fn);
        fnPanel.add(text_fn);

        lnPanel.add(label_ln);
        lnPanel.add(text_ln);

        mailPanel.add(label_email);
        mailPanel.add(text_email);

        hashPanel.add(label_hash);
        hashPanel.add(text_hash);

        rolePanel.add(label_role);
        rolePanel.add(ownerRole);

        stPanel.add(label_st);
        stPanel.add(ownerStatus);

        ctrlPanel.add(escape);
        ctrlPanel.add(delete);
        ctrlPanel.add(save);

        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes without submit");

        ownersUsageCallback = new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("error", "Could not get owner usage");
            }

            @Override
            public void onSuccess(Boolean result) {
                delete.setEnabled(!result);
            }
        };
    }

    public void addEvents(long ownerID) {
        getService().getOwnerUsage(ownerID, ownersUsageCallback);
    }

    public void adjustSize(int wdth) {
        setWidth(wdth + "px");
        setCellHorizontalAlignment(oform, HorizontalPanel.ALIGN_CENTER);
        int w = wdth * 5 / 6;
        fnPanel.setWidth(w + "px");
        fnPanel.setCellHorizontalAlignment(label_fn, HorizontalPanel.ALIGN_LEFT);
        fnPanel.setCellHorizontalAlignment(text_fn, HorizontalPanel.ALIGN_RIGHT);
        lnPanel.setWidth(w + "px");
        lnPanel.setCellHorizontalAlignment(label_ln, HorizontalPanel.ALIGN_LEFT);
        lnPanel.setCellHorizontalAlignment(text_ln, HorizontalPanel.ALIGN_RIGHT);
        mailPanel.setWidth(w + "px");
        mailPanel.setCellHorizontalAlignment(label_email, HorizontalPanel.ALIGN_LEFT);
        mailPanel.setCellHorizontalAlignment(text_email, HorizontalPanel.ALIGN_RIGHT);
        hashPanel.setWidth(w + "px");
        hashPanel.setCellHorizontalAlignment(label_hash, HorizontalPanel.ALIGN_LEFT);
        hashPanel.setCellHorizontalAlignment(text_hash, HorizontalPanel.ALIGN_RIGHT);
        rolePanel.setWidth(w + "px");
        rolePanel.setCellHorizontalAlignment(label_role, HorizontalPanel.ALIGN_LEFT);
        rolePanel.setCellHorizontalAlignment(ownerRole, HorizontalPanel.ALIGN_RIGHT);
        stPanel.setWidth(w + "px");
        stPanel.setCellHorizontalAlignment(label_st, HorizontalPanel.ALIGN_LEFT);
        stPanel.setCellHorizontalAlignment(ownerStatus, HorizontalPanel.ALIGN_RIGHT);
        ctrlPanel.setWidth(w + "px");
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_CENTER);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_RIGHT);
        text_fn.setWidth(w * 2 / 3 + "px");
        text_ln.setWidth(w * 2 / 3 + "px");
        text_email.setWidth(w * 2 / 3 + "px");
        text_hash.setWidth(w * 2 / 3 + "px");
        ownerRole.setWidth(w * 2 / 3 + "px");
        ownerStatus.setWidth(w * 2 / 3 + "px");

    }

    public void setContentFromOwnerDTO(OwnerDTO ownerDTO, BooleanWrap isEdited) {
        text_fn.setText(ownerDTO.getFirstName());
        text_ln.setText(ownerDTO.getLastName());
        text_email.setText(ownerDTO.getEmail());
        text_hash.setText(ownerDTO.getHash());
        rolePanel.remove(ownerRole);
        stPanel.remove(ownerStatus);
        ownerRole = new OwnerRolesList(GuiConstant.INTERFACE_LANG, ownerDTO.getRole(), isEdited, isLocallyEdited);
        ownerStatus = new OwnerStatusList(GuiConstant.INTERFACE_LANG, ownerDTO.getStatus(), isEdited, isLocallyEdited);
        rolePanel.add(ownerRole);
        stPanel.add(ownerStatus);
        ownerRole.setWidth(text_fn.getOffsetWidth() + "px");
        ownerStatus.setWidth(text_fn.getOffsetWidth() + "px");
        rolePanel.setWidth(mailPanel.getOffsetWidth() + "px");
        rolePanel.setCellHorizontalAlignment(label_role, HorizontalPanel.ALIGN_LEFT);
        rolePanel.setCellHorizontalAlignment(ownerRole, HorizontalPanel.ALIGN_RIGHT);
        stPanel.setWidth(mailPanel.getOffsetWidth() + "px");
        stPanel.setCellHorizontalAlignment(label_st, HorizontalPanel.ALIGN_LEFT);
        stPanel.setCellHorizontalAlignment(ownerStatus, HorizontalPanel.ALIGN_RIGHT);
    }

    public void setReadOnly(Boolean edit) {
        text_fn.setReadOnly(edit);
        text_ln.setReadOnly(edit);
        text_email.setReadOnly(edit);
        text_hash.setReadOnly(edit);
        ownerRole.setEnabled(!edit);
        ownerStatus.setEnabled(!edit);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public void setOwnerDTOFromContent(OwnerDTO ownerDTO) {
        ownerDTO.setFirstName(text_fn.getText());
        ownerDTO.setLastName(text_ln.getText());
        ownerDTO.setEmail(text_email.getText());
        ownerDTO.setHash(text_hash.getText());
        ownerDTO.setStatus(ownerStatus.getValue(ownerStatus.getSelectedIndex()));
        ownerDTO.setRole(ownerRole.getValue(ownerRole.getSelectedIndex()));
    }
}
