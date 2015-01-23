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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.HashMap;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LangSetFormREVISOR extends VerticalPanel {

    public VerticalPanel desc = new VerticalPanel();
    private ArrayList<TermFormREVISOR> terms;
    private long ownerID;

    public LangSetFormREVISOR(long idOwner) {
        ownerID = idOwner;
        this.terms = new ArrayList<>();
        this.setStyleName("langSetForm");
        add(desc);
    }

    public void refreshContentFromLangEntryDTO(final LangEntryDTO langEntryDTO, ArrayList<String> userLangs, HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited) {
        if (!langEntryDTO.listterm.isEmpty()) {
            int i = 0;
            for (final TermDTO tDTO : langEntryDTO.listterm) {
                if (tDTO.getStatus() == 'r') {
                    final TermFormREVISOR ter = new TermFormREVISOR(ownerID, 0, sFields, isEdited);
                    i++;
                    terms.add(ter);
                    desc.add(ter);
                    ter.adjustSize(getOffsetWidth() - 10);
                    ter.refreshContentFromTermDTO(tDTO, userLangs, sFields, isEdited);
                    ter.form3.setWidget(4, 0, new HTML("Term number: " + i));
                }
            }
        }
    }

    public void adjustSize(int w) {
        setWidth(w + "px");
    }

    public void sortTermDTOByLangSet(ArrayList<LangEntryDTO> listlang) {
        if (!terms.isEmpty()) {
            for (TermFormREVISOR tf : terms) {
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
}
