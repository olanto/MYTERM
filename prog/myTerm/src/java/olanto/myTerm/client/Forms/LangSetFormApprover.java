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
import java.util.ArrayList;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LangSetFormApprover extends VerticalPanel {

    public VerticalPanel desc = new VerticalPanel();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button addTerm = new Button("Add Term");
    private ArrayList<TermFormApprover> terms;
    private long ownerID;

    public LangSetFormApprover(long idOwner) {
        ownerID = idOwner;
        this.terms = new ArrayList<>();
        this.setStyleName("langSetForm");
        add(desc);
        add(controls);
        controls.add(addTerm);
        addTerm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final TermFormApprover ter = new TermFormApprover(ownerID, 1);
                terms.add(ter);
                desc.add(ter);
                ter.adjustSize(getOffsetWidth() - 10);
                ter.form3.setWidget(4, 0, new HTML("Term number: " + (terms.size())));
            }
        });
    }

    public void refreshContentFromLangEntryDTO(final LangEntryDTO langEntryDTO) {
        if (!langEntryDTO.listterm.isEmpty()) {
            int i = 0;
            for (final TermDTO tDTO : langEntryDTO.listterm) {
                final TermFormApprover ter = new TermFormApprover(ownerID, 0);
                terms.add(ter);
                desc.add(ter);
                ter.adjustSize(getOffsetWidth() - 10);
                ter.refreshContentFromTermDTO(tDTO);
                ter.form3.setWidget(4, 0, new HTML("Term number: " + i));
                i++;
            }
        }
    }

    public void adjustSize(int w) {
        setWidth(w + "px");
        controls.setWidth(w + "px");
        controls.setCellHorizontalAlignment(addTerm, HorizontalPanel.ALIGN_RIGHT);
    }

    public void sortTermDTOByLangSet(ArrayList<LangEntryDTO> listlang) {
        if (!terms.isEmpty()) {
            for (TermFormApprover tf : terms) {
                int i = getLangEntryIdx(tf.getIdLanguage(), listlang);
                if (i > -1) {
                    if (tf.type == 0) {
                        int j = getTermDTOIdx(tf.getTermForm(), listlang.get(i).listterm);
                        tf.updateTermDTOFromContent(listlang.get(i).listterm.get(j));
                    } else {
                        TermDTO termDTO = new TermDTO(null);
                        tf.updateTermDTOFromContent(termDTO);
                        listlang.get(i).listterm.add(termDTO);
                    }
                } else {
                    TermDTO termDTO = new TermDTO(null);
                    tf.updateTermDTOFromContent(termDTO);
                    LangEntryDTO lsDTO = new LangEntryDTO(termDTO.getIdLanguage());
                    lsDTO.listterm.add(termDTO);
                    listlang.add(lsDTO);
                }
            }
        }
    }

    private int getLangEntryIdx(String langID, ArrayList<LangEntryDTO> listlang) {
        if (!listlang.isEmpty()) {
            int i = 0;
            for (LangEntryDTO lE : listlang) {
                if (lE.lan.getIdLanguage().equalsIgnoreCase(langID)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private int getTermDTOIdx(String termform, ArrayList<TermDTO> listterm) {
        if (!listterm.isEmpty()) {
            int i = 0;
            for (TermDTO tDTO : listterm) {
                if (tDTO.getTermForm().equalsIgnoreCase(termform)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }
    
    public void clearAllText(){
        for (TermFormApprover term : terms){
            term.clearAllText();
        }
    }
}
