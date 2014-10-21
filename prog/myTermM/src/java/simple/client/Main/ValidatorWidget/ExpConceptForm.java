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
package simple.client.Main.ValidatorWidget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class ExpConceptForm extends VerticalPanel {

    private HorizontalPanel desc = new HorizontalPanel();
    private Grid impt = new Grid(4, 2);
    private Label label_sfd = new Label("Concept's subject field:");
    private Label text_sfd = new Label();
    private Label label_def = new Label("Concept's definition:");
    private Label text_def = new Label();
    private Label label_sdef = new Label("Definition's source:");
    private Label text_sdef = new Label();
    private Label label_nt = new Label("Note:");
    private Label text_nt = new Label();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button hide = new Button("Hide");
    private ExpLangSetPanel expLS = new ExpLangSetPanel();

    public ExpConceptForm() {
        setStyleName("conceptForm");
        add(desc);
        add(impt);
        add(controls);
        add(expLS);
        controls.add(hide);
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
        controls.setCellHorizontalAlignment(hide, HorizontalPanel.ALIGN_CENTER);
    }
}
