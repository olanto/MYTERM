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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Resources.ResourceList;
import olanto.myTerm.shared.ConceptDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ConceptFormREVISOR extends HorizontalPanel {

    private Grid cform = new Grid(2, 3);
    private Label label_sf = new Label("Subject field:");
    private Label label_rsrc = new Label("Add to resource:");
    private Label label_def = new Label("Definition:");
    private TextArea text_def = new TextArea();
    private Label label_sdef = new Label("Definition's source:");
    private TextArea text_sdef = new TextArea();
    private Label label_nt = new Label("Note:");
    private Label label_dom = new Label("");
    private Label label_rs = new Label("");
    private TextArea text_nt = new TextArea();
    private HorizontalPanel sfPanel = new HorizontalPanel();
    private HorizontalPanel rsrcPanel = new HorizontalPanel();
    private HorizontalPanel ctrlPanel = new HorizontalPanel();
    private VerticalPanel defPAnel = new VerticalPanel();
    private VerticalPanel defsPanel = new VerticalPanel();
    private VerticalPanel ntPanel = new VerticalPanel();
    public Button approve = new Button("APPROVE ALL");
    public Button save = new Button("SAVE");
    public Button disapprove = new Button("DISAPPROVE ALL");
    public Button escape = new Button("ESCAPE");
    public ResourceList rsrc;

    public ConceptFormREVISOR(ResourceList rs) {
        rsrc = rs;
        setStyleName("conceptForm");
        add(cform);
        cform.setStyleName("edpanel");
        cform.setCellSpacing(4);
        cform.setWidget(0, 0, sfPanel);
        cform.setWidget(0, 1, rsrcPanel);
        cform.setWidget(0, 2, ctrlPanel);
        cform.setWidget(1, 0, defPAnel);
        cform.setWidget(1, 1, defsPanel);
        cform.setWidget(1, 2, ntPanel);
        sfPanel.add(label_sf);
        sfPanel.add(label_dom);
        rsrcPanel.add(label_rsrc);
        rsrcPanel.add(label_rs);
        ctrlPanel.add(approve);
        ctrlPanel.add(save);
        ctrlPanel.add(disapprove);
        ctrlPanel.add(escape);
        defPAnel.add(label_def);
        defPAnel.add(text_def);
        defsPanel.add(label_sdef);
        defsPanel.add(text_sdef);
        ntPanel.add(label_nt);
        ntPanel.add(text_nt);
        approve.setTitle("Save and publish all terms of the current entry");
        escape.setTitle("Abort all modifications");
        save.setTitle("Save changes (updates in database)");
        disapprove.setTitle("Save and disapprove all terms of the current entry");
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
    }

    public void adjustSize(int w) {
        sfPanel.setWidth(w * 1 / 3 + "px");
        sfPanel.setCellHorizontalAlignment(label_sf, HorizontalPanel.ALIGN_LEFT);
        sfPanel.setCellHorizontalAlignment(label_rs, HorizontalPanel.ALIGN_RIGHT);
        rsrcPanel.setWidth(w * 1 / 3 + "px");
        ctrlPanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(approve, HorizontalPanel.ALIGN_RIGHT);
        ctrlPanel.setWidth(w * 1 / 3 + "px");
        ctrlPanel.setCellHorizontalAlignment(disapprove, HorizontalPanel.ALIGN_LEFT);
        ctrlPanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_RIGHT);
        defPAnel.setWidth(w * 1 / 3 + "px");
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
        label_dom.setText(conceptDTO.getSubjectField());
        label_rs.setText(rsrc.getResName(conceptDTO.getIdResource()));
    }

    public void clearAllText() {
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
    }

    public void setReadOnly(Boolean edit) {
        text_def.setReadOnly(edit);
        text_sdef.setReadOnly(edit);
        text_nt.setReadOnly(edit);
    }

    public void setConceptDTOFromContent(ConceptDTO conceptDTO) {
        conceptDTO.setConceptDefinition(text_def.getText());
        conceptDTO.setConceptNote(text_nt.getText());
        conceptDTO.setConceptSourceDefinition(text_sdef.getText());
        conceptDTO.setSubjectField(label_dom.getText());
    }
}
