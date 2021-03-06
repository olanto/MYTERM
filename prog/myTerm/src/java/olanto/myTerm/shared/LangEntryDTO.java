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
package olanto.myTerm.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class LangEntryDTO implements IsSerializable {

    public LangSetDTO lan;
    public ArrayList<TermDTO> listterm = new ArrayList<>();

    public LangEntryDTO() {
    }

    public LangEntryDTO(String lang) {
        lan = new LangSetDTO();
        lan.setIdLanguage(lang);
    }

    public LangEntryDTO(String lang, long idLangset) {
        lan = new LangSetDTO();
        lan.setIdLanguage(lang);
        lan.setIdLangset(idLangset);
    }

    public void addTerm(String term_form, String idLanguage, char status, long modifBy) {
        TermDTO term = new TermDTO(null, term_form, status); // minimal information
        term.setIdLanguage(idLanguage);
        term.setCreateBy(BigInteger.valueOf(modifBy));
        term.setModifiedBy(BigInteger.valueOf(modifBy));
        term.setCreation(new Date(System.currentTimeMillis()));
        term.setLastmodified(new Date(System.currentTimeMillis()));
        listterm.add(term);
    }

    public TermDTO getTerm(int i) {
        return listterm.get(i);
    }

    public TermDTO getTermLast() {
        return listterm.get(listterm.size() - 1);
    }

    public void deleteTerm(String term_form) {
        for (int i = 0; i < listterm.size(); i++) {
            TermDTO t = listterm.get(i);
            if (t.getTermForm().equals(term_form)) {
                listterm.remove(t);
                return;
            }
        }
    }
}
