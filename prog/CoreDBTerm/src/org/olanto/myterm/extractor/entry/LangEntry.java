package org.olanto.myterm.extractor.entry;

/**
 * ********
 * Copyright � 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
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
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
import java.util.Vector;
import org.olanto.myterm.coredb.ManageLangsets;
import org.olanto.myterm.coredb.ManageTerm;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;

public class LangEntry {

    public Langsets lan;
    public Vector<Terms> listterm = new Vector<>();

    public LangEntry(String lang) {
        lan = new Langsets();
        lan.setIdLanguage(lang);
    }

    public void addTerm(String term_form) {
        Terms term = new Terms(null, term_form, 'p'); // minimal information
        listterm.add(term);
    }
   
    public Terms getTerm(int i) {
        return listterm.get(i);
    }
    
    public Terms getTermLast() {
        return listterm.get(listterm.size()-1);
    }

    public void addLangToDB(Concepts concept) {
        lan.setIdConcept(concept.getIdConcept());
        lan = ManageLangsets.addLangsetToConcept(concept, lan.getIdLanguage());
        for (int i = 0; i < listterm.size(); i++) {
            Terms term = listterm.get(i);
             term = ManageTerm.addTermToLangset(lan, term);
        }

    }
}
