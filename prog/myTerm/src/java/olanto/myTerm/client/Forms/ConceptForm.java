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
import olanto.myTerm.client.ConstantsManager.GuiConstant;
import olanto.myTerm.client.Domains.DomainList;
import olanto.myTerm.client.Types.Concept;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ConceptForm extends HorizontalPanel {

    private Grid cform = new Grid(2, 3);
    private Label label_sf = new Label("Subject field:");
    public DomainList sf = new DomainList();
    private Label label_def = new Label("Definition:");
    private TextArea text_def = new TextArea();
    private Label label_sdef = new Label("Definition's source:");
    private TextArea text_sdef = new TextArea();
    private Label label_nt = new Label("Note:");
    private TextArea text_nt = new TextArea();
    private HorizontalPanel sfPanel = new HorizontalPanel();
    private HorizontalPanel savePanel = new HorizontalPanel();
    private HorizontalPanel escapePanel = new HorizontalPanel();
    private VerticalPanel defPAnel = new VerticalPanel();
    private VerticalPanel defsPanel = new VerticalPanel();
    private VerticalPanel ntPanel = new VerticalPanel();
    public Button save = new Button("SAVE");
    public Button submit = new Button("SUBMIT");
    public Button delete = new Button("DELETE");
    public Button escape = new Button("ESCAPE");

    public ConceptForm() {
        setStyleName("conceptForm");
        add(cform);
        cform.setStyleName("cpanel");
        cform.setCellSpacing(4);
        cform.setWidget(0, 0, sfPanel);
        cform.setWidget(0, 1, savePanel);
        cform.setWidget(0, 2, escapePanel);
        cform.setWidget(1, 0, defPAnel);
        cform.setWidget(1, 1, defsPanel);
        cform.setWidget(1, 2, ntPanel);
        sfPanel.add(label_sf);
        sfPanel.add(sf);
        savePanel.add(save);
        savePanel.add(submit);
        escapePanel.add(delete);
        escapePanel.add(escape);
        defPAnel.add(label_def);
        defPAnel.add(text_def);
        defsPanel.add(label_sdef);
        defsPanel.add(text_sdef);
        ntPanel.add(label_nt);
        ntPanel.add(text_nt);
    }

    public void adjustSize(int w) {
        sfPanel.setWidth(w * 1 / 3 + "px");
        sfPanel.setCellHorizontalAlignment(label_sf, HorizontalPanel.ALIGN_LEFT);
        sfPanel.setCellHorizontalAlignment(sf, HorizontalPanel.ALIGN_RIGHT);
        savePanel.setWidth(w * 1 / 3 + "px");
        savePanel.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_LEFT);
        savePanel.setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
        escapePanel.setWidth(w * 1 / 3 + "px");
        escapePanel.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_LEFT);
        escapePanel.setCellHorizontalAlignment(escape, HorizontalPanel.ALIGN_RIGHT);
        defPAnel.setWidth(w * 1 / 3 + "px");
        defsPanel.setWidth(w * 1 / 3 + "px");
        ntPanel.setWidth(w * 1 / 3 + "px");
        text_def.setPixelSize(w * 1 / 3, GuiConstant.TEXTAREA_HEIGHT);
        text_sdef.setPixelSize(w * 1 / 3, GuiConstant.TEXTAREA_HEIGHT);
        text_nt.setPixelSize(w * 1 / 3, GuiConstant.TEXTAREA_HEIGHT);
    }

    public void InitFromVariable(Concept c) {
        this.text_def.setText(c.definition);
        this.text_sdef.setText(c.defintion_source);
        this.text_nt.setText(c.note);
        sf.selectdomain(c.subject_field);
    }

    public void clearAllText() {
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
    }

    public void setEditable(Boolean edit) {
        text_def.setEnabled(edit);
        text_sdef.setEnabled(edit);
        text_nt.setEnabled(edit);
        sf.setEnabled(edit);
    }
}
