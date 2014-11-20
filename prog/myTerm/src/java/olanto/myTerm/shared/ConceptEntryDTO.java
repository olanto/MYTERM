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
import java.util.ArrayList;

public class ConceptEntryDTO implements IsSerializable {

    public ResourceDTO resource;
    public ConceptDTO concept;
    public String extraConcepts;
    public ArrayList<LangEntryDTO> listlang;

    public ConceptEntryDTO() {
        this.concept = new ConceptDTO();
        this.listlang = new ArrayList<>();
        this.extraConcepts = "";
    }

    public ConceptEntryDTO(ResourceDTO resource) {
        this.resource = resource;
        this.concept = new ConceptDTO();
        this.listlang = new ArrayList<>();
        this.extraConcepts = "";
    }

    public void addTerm(String lang, String term_form) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntryDTO lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                lan.addTerm(term_form);
                return;
            }
        }
        LangEntryDTO lan = new LangEntryDTO(lang);
        lan.addTerm(term_form);
        listlang.add(lan);
    }

    public void deleteTerm(String lang, String term_form) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntryDTO lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                lan.deleteTerm(term_form);
                return;
            }
        }
    }

    public TermDTO getTerm(String lang, int position) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntryDTO lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                return lan.getTerm(position);
            }
        }
        return null;
    }

    public TermDTO getTermLast(String lang) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntryDTO lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                return lan.getTermLast();
            }
        }
        return null;
    }
}