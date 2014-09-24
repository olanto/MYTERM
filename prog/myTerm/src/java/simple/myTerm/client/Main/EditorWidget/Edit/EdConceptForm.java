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
package simple.myTerm.client.Main.EditorWidget.Edit;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.myTerm.client.Main.Types.Concept;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class EdConceptForm extends VerticalPanel {

    private Label label_sfd = new Label("Concept's subject field:");
    private TextBox text_sfd = new TextBox();
    private Label label_def = new Label("Concept's definition:");
    private TextBox text_def = new TextBox();
    private Label label_sdef = new Label("Definition's source:");
    private TextBox text_sdef = new TextBox();
    private Label label_nt = new Label("Note:");
    private TextBox text_nt = new TextBox();
    private HorizontalPanel ctrl1 = new HorizontalPanel();
    private HorizontalPanel ctrl2 = new HorizontalPanel();
    public Button cancel = new Button("Cancel");
    public Button submit = new Button("Cancel");

    public EdConceptForm() {
        setStyleName("conceptForm");
        add(ctrl1);
        add(ctrl2);
        ctrl1.setStyleName("cpanel");
        ctrl2.setStyleName("cpanel");
        ctrl1.add(label_sfd);
        ctrl1.add(text_sfd);
        ctrl1.add(label_def);
        ctrl1.add(text_def);
        ctrl1.add(cancel);
        ctrl2.add(label_sdef);
        ctrl2.add(text_sdef);
        ctrl2.add(label_nt);
        ctrl2.add(text_nt);
        ctrl2.add(submit);
        ctrl1.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_CENTER);
        ctrl2.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_CENTER);
    }
    
    public void InitFromVariable(Concept c){
        this.text_sfd.setText(c.subject_field);
        this.text_def.setText(c.definition);
        this.text_sdef.setText(c.defintion_source);
        this.text_nt.setText(c.note);
    }

    public void clearAllText() {
        text_sfd.setText("");
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
    }
}
