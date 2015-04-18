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
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LanguageFormADMIN extends VerticalPanel {

    private Grid rform;
    private Label label_lid;
    private Label label_ldn;
    private TextBoxMyTerm text_lid;
    private TextBoxMyTerm text_ldn;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel lidPanel;
    private HorizontalPanel ldnPanel;
    public Button save;
    public Button escape;
    public Button delete;
    private static AsyncCallback<Boolean> languagesUsageCallback;

    public LanguageFormADMIN(HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        label_lid = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_L_ID), sFields.get(GuiConstant.L_ID));
        label_ldn = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_L_DEFAULT_NAME), sFields.get(GuiConstant.L_NAME));
        text_lid = new TextBoxMyTerm(GuiConstant.L_ID, sFields, isEdited);
        text_ldn = new TextBoxMyTerm(GuiConstant.L_NAME, sFields, isEdited);

        ctrlPanel = new HorizontalPanel();
        lidPanel = new HorizontalPanel();
        ldnPanel = new HorizontalPanel();
        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));

        rform = new Grid(3, 2);
        add(rform);

        rform.setCellSpacing(4);
        rform.setStyleName("edpanel");
        rform.setWidget(0, 0, lidPanel);
        rform.setWidget(1, 0, ldnPanel);
        rform.setWidget(2, 0, ctrlPanel);

        lidPanel.add(label_lid);
        lidPanel.add(text_lid);

        ldnPanel.add(label_ldn);
        ldnPanel.add(text_ldn);
        
        ctrlPanel.add(escape);
        ctrlPanel.add(delete);
        ctrlPanel.add(save);

        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes without submit");

        languagesUsageCallback = new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("error", "Could not get language's usage");
            }

            @Override
            public void onSuccess(Boolean result) {
//                Window.alert(""+result.booleanValue());
                delete.setEnabled(!result.booleanValue());
            }
        };
    }

    public void addEvents(String langID) {
        getService().getLanguageUsage(langID, languagesUsageCallback);
    }

    public void adjustSize(int wdth) {
        setWidth(wdth + "px");
        setCellHorizontalAlignment(rform, HorizontalPanel.ALIGN_CENTER);
        int w = wdth * 5 / 6;
        lidPanel.setWidth(w + "px");
        lidPanel.setCellHorizontalAlignment(label_lid, HorizontalPanel.ALIGN_LEFT);
        lidPanel.setCellHorizontalAlignment(text_lid, HorizontalPanel.ALIGN_RIGHT);
        ldnPanel.setWidth(w + "px");
        ldnPanel.setCellHorizontalAlignment(label_ldn, HorizontalPanel.ALIGN_LEFT);
        ldnPanel.setCellHorizontalAlignment(text_ldn, HorizontalPanel.ALIGN_RIGHT);
        
        ctrlPanel.setWidth(w + "px");
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_CENTER);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_RIGHT);
        text_lid.setWidth(w * 2 / 3 + "px");
        text_ldn.setWidth(w * 2 / 3 + "px");
    }

    public void setContentFromLanguageDTO(LanguageDTO langDTO, BooleanWrap isEdited) {
        text_lid.setText(langDTO.getIdLanguage());
        text_ldn.setText(langDTO.getLanguageDefaultName());       
    }

    public void setReadOnly(Boolean edit) {
        text_lid.setReadOnly(edit);
        text_ldn.setReadOnly(edit);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public void setLanguageDTOFromContent(LanguageDTO langDTO) {
        langDTO.setIdLanguage(text_lid.getText());
        langDTO.setLanguageDefaultName(text_ldn.getText());
    }
}
