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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class AddTermPanel extends VerticalPanel {

    private HorizontalPanel desc = new HorizontalPanel();
    private Button add = new Button("+ Add Term +");

    public AddTermPanel() {
        setStyleName("tcontainerForm");
        add(desc);
        desc.add(new HTML("Add a new term for the selected language set"));
        desc.add(new HTML("&nbsp;"));
        desc.add(add);

        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final TermForm tf = new TermForm();
                add(tf);
                tf.cancel.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        remove(tf);
                    }
                });
            }
        });
    }
}
