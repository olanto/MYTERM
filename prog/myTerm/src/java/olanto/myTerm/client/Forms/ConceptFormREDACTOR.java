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
import olanto.myTerm.client.Lists.ResourceList;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.shared.ConceptDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ConceptFormREDACTOR extends HorizontalPanel {

    private Grid cform = new Grid(2, 3);
    private Label label_sf = new Label("Subject field:");
    public DomainList sf;
    private Label label_rsrc = new Label("Added to resource:");
    private ResourceList rl;
    private Label label_def = new Label("Definition:");
    private TextAreaMyTerm text_def;
    private Label label_sdef = new Label("Definition's source:");
    private TextAreaMyTerm text_sdef;
    private Label label_nt = new Label("Note:");
    private TextAreaMyTerm text_nt;
    private HorizontalPanel sfPanel = new HorizontalPanel();
    private HorizontalPanel rsrcPanel = new HorizontalPanel();
    private HorizontalPanel ctrlPanel = new HorizontalPanel();
    private VerticalPanel defPanel = new VerticalPanel();
    private VerticalPanel defsPanel = new VerticalPanel();
    private VerticalPanel ntPanel = new VerticalPanel();
    public Button save = new Button("SAVE");
    public Button submit = new Button("SUBMIT");
    public Button delete = new Button("DELETE");
    public Button escape = new Button("ESCAPE");
    private Label label_dom = new Label("");
    private Label label_rs = new Label("");
    public int type = 1;

    public ConceptFormREDACTOR(ResourceList rsrc, HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited) {
        rl = rsrc;
        setStyleName("conceptForm");
        add(cform);
        sf = new DomainList(isEdited);
        text_def = new TextAreaMyTerm("c.definition", sFields, isEdited);
        text_sdef = new TextAreaMyTerm("c.source_definition", sFields, isEdited);
        text_nt = new TextAreaMyTerm("c.note", sFields, isEdited);
        cform.setStyleName("edpanel");
        cform.setCellSpacing(4);
        cform.setWidget(0, 0, sfPanel);
        cform.setWidget(0, 1, rsrcPanel);
        cform.setWidget(0, 2, ctrlPanel);
        cform.setWidget(1, 0, defPanel);
        cform.setWidget(1, 1, defsPanel);
        cform.setWidget(1, 2, ntPanel);
        sfPanel.add(label_sf);
        sfPanel.add(sf);
        rsrcPanel.add(label_rsrc);
        rsrcPanel.add(label_rs);
        ctrlPanel.add(save);
        ctrlPanel.add(submit);
        ctrlPanel.add(delete);
        ctrlPanel.add(escape);
        defPanel.add(label_def);
        defPanel.add(text_def);
        defsPanel.add(label_sdef);
        defsPanel.add(text_sdef);
        ntPanel.add(label_nt);
        ntPanel.add(text_nt);
        delete.setTitle("Delete the current concept");
        escape.setTitle("Abort all modifications");
        submit.setTitle("Submit changes (for review)");
        save.setTitle("Save changes without submit");
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
        sf.setSelectedIndex(0);
    }

    public void adjustSize(int w) {
        sfPanel.setWidth(w * 1 / 3 + "px");
        sfPanel.setCellHorizontalAlignment(label_sf, HorizontalPanel.ALIGN_LEFT);
        sfPanel.setCellHorizontalAlignment(sf, HorizontalPanel.ALIGN_RIGHT);
        rsrcPanel.setWidth(w * 1 / 3 + "px");
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
        ctrlPanel.setWidth(w * 1 / 3 + "px");
        ctrlPanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_RIGHT);
        defPanel.setWidth(w * 1 / 3 + "px");
        defsPanel.setWidth(w * 1 / 3 + "px");
        ntPanel.setWidth(w * 1 / 3 + "px");
        text_def.setWidth(w * 1 / 3 + "px");
        text_sdef.setWidth(w * 1 / 3 + "px");
        text_nt.setWidth(w * 1 / 3 + "px");
    }

    public void setContentFromConceptEntryDTO(ConceptDTO conceptDTO) {
        text_def.setText(conceptDTO.getConceptDefinition());
        text_sdef.setText(conceptDTO.getConceptSourceDefinition());
        text_nt.setText(conceptDTO.getConceptNote());
        if ((conceptDTO.getSubjectField() != null) && (!conceptDTO.getSubjectField().isEmpty()) && (conceptDTO.getSubjectField().length() > 1)) {
            type = 0;
            sfPanel.remove(sf);
            label_dom.setText(conceptDTO.getSubjectField());
            sfPanel.add(label_dom);
        }
        label_rs.setText(rl.getResName(conceptDTO.getIdResource()));
    }

    public void setReadOnly(Boolean edit) {
        text_def.setReadOnly(edit);
        text_sdef.setReadOnly(edit);
        text_nt.setReadOnly(edit);
        sf.setEnabled(edit);
    }

    public void setConceptDTOFromContent(ConceptDTO conceptDTO) {
        conceptDTO.setConceptDefinition(text_def.getText());
        conceptDTO.setConceptNote(text_nt.getText());
        conceptDTO.setConceptSourceDefinition(text_sdef.getText());
        if (type == 0) {
            conceptDTO.setSubjectField(label_dom.getText());
        } else {
            conceptDTO.setSubjectField(sf.getItemText(sf.getSelectedIndex()));
        }
    }
}
