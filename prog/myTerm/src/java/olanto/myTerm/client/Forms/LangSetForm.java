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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LangSetForm extends VerticalPanel {

    public VerticalPanel desc = new VerticalPanel();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button addTerm = new Button("Add Term");
    public LangEntryDTO langEntryDTO;
    private ArrayList<TermForm> terms;

    public LangSetForm() {
        this.terms = new ArrayList<>();
        this.setStyleName("langSetForm");
        add(desc);
        add(controls);
        controls.add(addTerm);
        addTerm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final TermForm ter = new TermForm();
                terms.add(ter);
                desc.add(ter);
                ter.adjustSize(getOffsetWidth() - 5);
                final TermDTO tDTO = new TermDTO();
                tDTO.setIdLangset(langEntryDTO.lan.getIdLangset());
                tDTO.setIdLanguage(langEntryDTO.lan.getIdLanguage());
                langEntryDTO.listterm.add(tDTO);
                ter.form3.setWidget(4, 0, new HTML("Term number: " + (langEntryDTO.listterm.size() - 1)));
                ter.refreshContentFromTermDTO();
                ter.delete.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        langEntryDTO.listterm.remove(tDTO);
                        terms.remove(ter);
                        desc.remove(ter);
                    }
                });
            }
        });
    }

    public void refreshContentFromLangEntryDTO() {
        desc.clear();
        if (!langEntryDTO.listterm.isEmpty()) {
            int i = 0;
            for (final TermDTO tDTO : langEntryDTO.listterm) {
                final TermForm ter = new TermForm();
                terms.add(ter);
                desc.add(ter);
                ter.termDTO = tDTO;
                ter.refreshContentFromTermDTO();
                ter.adjustSize(getOffsetWidth() - 5);
                ter.form3.setWidget(4, 0, new HTML("Term number: " + i));
                ter.delete.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        langEntryDTO.listterm.remove(tDTO);
                        terms.remove(ter);
                        desc.remove(ter);
                    }
                });
                String oWnerID = Cookies.getCookie(MyTermCookiesNamespace.ownerID);
                if ((!oWnerID.equals(tDTO.getCreateBy().toString())) && (!oWnerID.equals(tDTO.getLastmodifiedBy().toString()))) {
                    ter.setReadOnly(true);
                }
                i++;
            }
        }
    }

    public void adjustSize(int w) {
        setWidth(w + "px");
        controls.setWidth(w + "px");
        controls.setCellHorizontalAlignment(addTerm, HorizontalPanel.ALIGN_RIGHT);
    }
}
