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
import olanto.myTerm.client.Lists.DomainList;
import olanto.myTerm.client.Lists.ResourcesList;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.shared.ConceptDTO;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ConceptFormREVISOR extends HorizontalPanel {

    private Grid cform;
    private Label label_sf;
    public DomainList sf;
    private Label label_rsrc;
    private Label label_def;
    private TextAreaMyTerm text_def;
    private Label label_sdef;
    private TextAreaMyTerm text_sdef;
    private Label label_nt;
    private TextAreaMyTerm text_nt;
    private Label label_cr;
    private TextAreaMyTerm text_cr;
    private HorizontalPanel sfPanel;
    private HorizontalPanel rsrcPanel;
    private HorizontalPanel ctrlPanel;
    private HorizontalPanel delPanel;
    private VerticalPanel defPanel;
    private VerticalPanel defsPanel;
    private VerticalPanel ntPanel;
    private VerticalPanel crPanel;
    public Button approve;
    public Button save;
    public Button disapprove;
    public Button escape;
    public ResourcesList rsrc;
    private Label label_rs;

    public ConceptFormREVISOR(ResourcesList rs, HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        rsrc = rs;
        cform = new Grid(2, 4);
        label_sf = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_SUBJECT_FIELD), sFields.get(GuiConstant.C_SUBJECT_FIELD));
        label_rsrc = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_RESOURCE), sFields.get(GuiConstant.C_RESOURCE));
        label_def = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_DEFINITION), sFields.get(GuiConstant.C_DEFINITION));
        label_sdef = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_SOURCE_DEFINITION), sFields.get(GuiConstant.C_SOURCE_DEFINITION));
        label_nt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_NOTE), sFields.get(GuiConstant.C_NOTE));
        label_cr = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_C_CROSS_REF), sFields.get(GuiConstant.C_CROSS_REF));
        sfPanel = new HorizontalPanel();
        rsrcPanel = new HorizontalPanel();
        ctrlPanel = new HorizontalPanel();
        delPanel = new HorizontalPanel();
        defPanel = new VerticalPanel();
        defsPanel = new VerticalPanel();
        ntPanel = new VerticalPanel();
        crPanel = new VerticalPanel();
        approve = new Button(sysMsg.get(GuiConstant.BTN_APPROVEALL));
        save = new Button(sysMsg.get(GuiConstant.BTN_SAVE));
        disapprove = new Button(sysMsg.get(GuiConstant.BTN_DISAPPROVEALL));
        escape = new Button(sysMsg.get(GuiConstant.BTN_ESCAPE));
        label_rs = new Label("");

        setStyleName("conceptForm");
        add(cform);
        sf = new DomainList(isEdited, sysMsg.get(GuiConstant.MSG_ALL_VALUE));
        text_def = new TextAreaMyTerm(sFields.get(GuiConstant.C_DEFINITION), isEdited);
        text_sdef = new TextAreaMyTerm(sFields.get(GuiConstant.C_SOURCE_DEFINITION), isEdited);
        text_nt = new TextAreaMyTerm(sFields.get(GuiConstant.C_NOTE), isEdited);
        text_cr = new TextAreaMyTerm(sFields.get(GuiConstant.C_CROSS_REF), isEdited);
        cform.setStyleName("edpanel");
        cform.setCellSpacing(4);
        cform.setWidget(0, 0, sfPanel);
        cform.setWidget(0, 1, rsrcPanel);
        cform.setWidget(0, 2, ctrlPanel);
        cform.setWidget(0, 3, delPanel);
        cform.setWidget(1, 0, defPanel);
        cform.setWidget(1, 1, defsPanel);
        cform.setWidget(1, 2, ntPanel);
        cform.setWidget(1, 3, crPanel);
        sfPanel.add(label_sf);
        sfPanel.add(sf);
        rsrcPanel.add(label_rsrc);
        rsrcPanel.add(label_rs);
        ctrlPanel.add(approve);
        ctrlPanel.add(save);
        delPanel.add(disapprove);
        delPanel.add(escape);
        defPanel.add(label_def);
        defPanel.add(text_def);
        defsPanel.add(label_sdef);
        defsPanel.add(text_sdef);
        ntPanel.add(label_nt);
        ntPanel.add(text_nt);
        crPanel.add(label_cr);
        crPanel.add(text_cr);
        approve.setTitle("Save and publish all terms of the current entry");
        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes (updates in database)");
        disapprove.setTitle("Save and disapprove all terms of the current entry");
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
        text_cr.setText("");
        sf.setSelectedIndex(0);
    }

    public void adjustSize(int w) {
        sfPanel.setWidth(w * 2 / 9 + "px");
        sfPanel.setCellHorizontalAlignment(label_sf, HorizontalPanel.ALIGN_LEFT);
        sfPanel.setCellHorizontalAlignment(sf, HorizontalPanel.ALIGN_RIGHT);
        rsrcPanel.setWidth(w * 2 / 9 + "px");
        rsrcPanel.setCellHorizontalAlignment(label_rs, HorizontalPanel.ALIGN_RIGHT);
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(approve, HorizontalPanel.ALIGN_RIGHT);
        ctrlPanel.setWidth(w * 2 / 9 + "px");
        delPanel.setWidth(w * 2 / 9 + "px");
        delPanel.setCellHorizontalAlignment(disapprove, HorizontalPanel.ALIGN_LEFT);
        delPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_RIGHT);
        defPanel.setWidth(w * 2 / 9 + "px");
        defsPanel.setWidth(w * 2 / 9 + "px");
        ntPanel.setWidth(w * 2 / 9 + "px");
        crPanel.setWidth(w * 2 / 9 + "px");
        text_def.setWidth(w * 2 / 9 + "px");
        text_sdef.setWidth(w * 2 / 9 + "px");
        text_nt.setWidth(w * 2 / 9 + "px");
        text_cr.setWidth(w * 2 / 9 + "px");
    }

    public void setContentFromConceptEntryDTO(ConceptDTO conceptDTO, BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        text_def.setText(conceptDTO.getConceptDefinition());
        text_sdef.setText(conceptDTO.getConceptSourceDefinition());
        text_nt.setText(conceptDTO.getConceptNote());
        text_cr.setText(conceptDTO.getCrossref());
        label_rs.setText(rsrc.getResName(conceptDTO.getIdResource()));
        sfPanel.remove(sf);
        sf = new DomainList(isEdited, conceptDTO.getSubjectField(), sysMsg.get(GuiConstant.MSG_ALL_VALUE));
        sfPanel.add(sf);
        sfPanel.setCellHorizontalAlignment(sf, HorizontalPanel.ALIGN_RIGHT);
    }

    public void setReadOnly(Boolean edit) {
        text_def.setReadOnly(edit);
        text_sdef.setReadOnly(edit);
        text_nt.setReadOnly(edit);
        text_cr.setReadOnly(edit);
        sf.setEnabled(!edit);
    }

    public void setConceptDTOFromContent(ConceptDTO conceptDTO) {
        conceptDTO.setConceptDefinition(text_def.getText());
        conceptDTO.setConceptNote(text_nt.getText());
        conceptDTO.setCrossref(text_cr.getText());
        conceptDTO.setConceptSourceDefinition(text_sdef.getText());
        conceptDTO.setSubjectField(sf.getItemText(sf.getSelectedIndex()));
    }
}
