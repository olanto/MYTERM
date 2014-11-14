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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Types.LangSet;
import olanto.myTerm.client.Types.Term;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LangSetForm extends VerticalPanel {

    public VerticalPanel desc = new VerticalPanel();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button addTerm = new Button("Add Term");

    public LangSetForm() {
        this.setStyleName("langSetForm");
        add(desc);
        add(controls);
        controls.add(addTerm);
    }

    public void initfromvar(final LangSet ls) {
        if (!ls.termList.isEmpty()) {
            int i = 0;
            for (final Term t : ls.termList) {
                final TermForm ter = new TermForm();
                desc.add(ter);
                ter.initFormVariable(t);
                ter.adjustSize(getOffsetWidth() - 5);
                ter.form3.setWidget(4, 0, new HTML("Term number: "+i));
                ter.delete.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        ls.termList.remove(t);
                        desc.remove(ter);
                    }
                });
            }
        }
    }

    public void adjustSize(int w) {
        setWidth(w + "px");
        controls.setWidth(w + "px");
        controls.setCellHorizontalAlignment(addTerm, HorizontalPanel.ALIGN_RIGHT);
    }
}
