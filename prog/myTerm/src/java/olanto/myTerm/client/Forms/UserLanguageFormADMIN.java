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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.Lists.LangList;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.client.Lists.OwnersList;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.UserLanguageDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class UserLanguageFormADMIN extends VerticalPanel {

    private Grid rform;
    private Label label_ow;
    private Label label_lang;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel owPanel;
    private HorizontalPanel langPanel;
    public Button save;
    public Button escape;
    public Button delete;
    public OwnersList langOwner;
    public LangList lang;
    private BooleanWrap isLocallyEdited = new BooleanWrap();

    public UserLanguageFormADMIN(HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        label_ow = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_O_FIRST_NAME), sFields.get(GuiConstant.O_FIRST_NAME));
        label_lang = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_L_DEFAULT_NAME), sFields.get(GuiConstant.L_NAME));
        lang = new LangList(isEdited, isLocallyEdited);
        langOwner = new OwnersList(isEdited, isLocallyEdited);

        ctrlPanel = new HorizontalPanel();
        owPanel = new HorizontalPanel();
        langPanel = new HorizontalPanel();
        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));

        rform = new Grid(5, 2);
        add(rform);

        rform.setCellSpacing(4);
        rform.setStyleName("edpanel");
        rform.setWidget(0, 0, owPanel);
        rform.setWidget(2, 0, langPanel);
        rform.setWidget(4, 0, ctrlPanel);

        owPanel.add(label_ow);
        owPanel.add(langOwner);

        langPanel.add(label_lang);
        langPanel.add(lang);

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
        owPanel.setWidth(w + "px");
        owPanel.setCellHorizontalAlignment(label_ow, HorizontalPanel.ALIGN_LEFT);
        owPanel.setCellHorizontalAlignment(langOwner, HorizontalPanel.ALIGN_RIGHT);
        langPanel.setWidth(w + "px");
        langPanel.setCellHorizontalAlignment(label_lang, HorizontalPanel.ALIGN_LEFT);
        langPanel.setCellHorizontalAlignment(lang, HorizontalPanel.ALIGN_RIGHT);

        ctrlPanel.setWidth(w + "px");
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_CENTER);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_RIGHT);

        langOwner.setWidth(w * 2 / 3 + "px");
        lang.setWidth(w * 2 / 3 + "px");
    }

    public void setContentFromLanguageDTO(UserLanguageDTO userLanguageDTO, BooleanWrap isEdited) {
        int w = this.getOffsetWidth();
        w = w * 5 / 6;
        owPanel.remove(langOwner);
        langPanel.remove(lang);

        lang = new LangList(userLanguageDTO.getIdLang(), isEdited, isLocallyEdited);
        langOwner = new OwnersList(userLanguageDTO.getIdOwner(), isEdited, isLocallyEdited);

        owPanel.add(langOwner);
        langPanel.add(lang);

        langOwner.setWidth(w * 2 / 3 + "px");
        lang.setWidth(w * 2 / 3 + "px");

        owPanel.setWidth(w + "px");
        owPanel.setCellHorizontalAlignment(label_ow, HorizontalPanel.ALIGN_LEFT);
        owPanel.setCellHorizontalAlignment(langOwner, HorizontalPanel.ALIGN_RIGHT);

        langPanel.setWidth(w + "px");
        langPanel.setCellHorizontalAlignment(label_lang, HorizontalPanel.ALIGN_LEFT);
        langPanel.setCellHorizontalAlignment(lang, HorizontalPanel.ALIGN_RIGHT);
    }

    public void setReadOnly(Boolean edit) {
        lang.setEnabled(!edit);
        langOwner.setEnabled(!edit);
    }

    public void setUserLanguageDTOFromContent(UserLanguageDTO userLanguageDTO) {
        userLanguageDTO.setIdOwner(Long.parseLong(langOwner.getValue(langOwner.getSelectedIndex())));
        userLanguageDTO.setIdLang(lang.getValue(lang.getSelectedIndex()));
    }
}
