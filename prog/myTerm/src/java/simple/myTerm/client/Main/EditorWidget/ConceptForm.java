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
package simple.myTerm.client.Main.EditorWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ConceptForm extends VerticalPanel {

    private HorizontalPanel desc = new HorizontalPanel();
    private Grid impt = new Grid(4, 2);
    private Label label_sfd = new Label("Concept's subject field:");
    private TextBox text_sfd = new TextBox();
    private Label label_def = new Label("Concept's definition:");
    private TextBox text_def = new TextBox();
    private Label label_sdef = new Label("Definition's source:");
    private TextBox text_sdef = new TextBox();
    private Label label_nt = new Label("Note:");
    private TextBox text_nt = new TextBox();
    private HorizontalPanel controls = new HorizontalPanel();
    private Button clear = new Button("Clear All");
    public Button cancel = new Button("Cancel");
    private AddLangSetPanel addLS = new AddLangSetPanel();

    public ConceptForm() {
        setStyleName("conceptForm");
        add(desc);
        add(impt);
        add(controls);
        add(addLS);
        controls.add(clear);
        controls.add(cancel);
        impt.setStyleName("cpanel");
        impt.setCellSpacing(6);
        impt.setWidget(0, 0, label_sfd);
        impt.setWidget(0, 1, text_sfd);
        impt.setWidget(1, 0, label_def);
        impt.setWidget(1, 1, text_def);
        impt.setWidget(2, 0, label_sdef);
        impt.setWidget(2, 1, text_sdef);
        impt.setWidget(3, 0, label_nt);
        impt.setWidget(3, 1, text_nt);
        controls.setCellHorizontalAlignment(clear, HorizontalPanel.ALIGN_RIGHT);
        controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_CENTER);
        clear.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clearAllText();
            }
        });
    }

    public void clearAllText() {
        text_sfd.setText("");
        text_def.setText("");
        text_sdef.setText("");
        text_nt.setText("");
    }
}
