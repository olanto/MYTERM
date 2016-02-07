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
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class DomainFormADMIN extends VerticalPanel {

    private Grid rform;
    private Label label_dn;
    private TextBoxMyTerm text_dn;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel dnPanel;
    public Button save;
    public Button escape;
    public Button delete;
    private static AsyncCallback<Boolean> domainUsageCallback;

    public DomainFormADMIN(HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        label_dn = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_D_DEFAULT_NAME), sFields.get(GuiConstant.D_DEFAULT_NAME));
        text_dn = new TextBoxMyTerm(sFields.get(GuiConstant.D_DEFAULT_NAME), isEdited);

        ctrlPanel = new HorizontalPanel();
        dnPanel = new HorizontalPanel();
        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));

        rform = new Grid(3, 2);
        add(rform);

        rform.setCellSpacing(4);
        rform.setStyleName("edpanel");
        rform.setWidget(0, 0, dnPanel);
        rform.setWidget(2, 0, ctrlPanel);

        dnPanel.add(label_dn);
        dnPanel.add(text_dn);

        ctrlPanel.add(escape);
        ctrlPanel.add(delete);
        ctrlPanel.add(save);

        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes without submit");

        domainUsageCallback = new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("error", "Could not get domain's usage");
            }

            @Override
            public void onSuccess(Boolean result) {
//                Window.alert(""+result.booleanValue());
                delete.setEnabled(!result.booleanValue());
            }
        };
    }

    public void addEvents(long domID) {
        getService().getDomainUsage(domID, domainUsageCallback);
    }

    public void adjustSize(int wdth) {
        setWidth(wdth + "px");
        setCellHorizontalAlignment(rform, HorizontalPanel.ALIGN_CENTER);
        int w = wdth * 5 / 6;
        dnPanel.setWidth(w + "px");
        dnPanel.setCellHorizontalAlignment(label_dn, HorizontalPanel.ALIGN_LEFT);
        dnPanel.setCellHorizontalAlignment(text_dn, HorizontalPanel.ALIGN_RIGHT);

        ctrlPanel.setWidth(w + "px");
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_CENTER);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_RIGHT);
        text_dn.setWidth(w * 2 / 3 + "px");
    }

    public void setContentFromDomainDTO(DomainDTO domDTO) {
        text_dn.setText(domDTO.getDomainDefaultName());
        addEvents(domDTO.getIdDomain());
    }

    public void setReadOnly(Boolean edit) {
        text_dn.setReadOnly(edit);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public void setDomainDTOFromContent(DomainDTO domDTO) {
        domDTO.setDomainDefaultName(text_dn.getText());
    }
}
