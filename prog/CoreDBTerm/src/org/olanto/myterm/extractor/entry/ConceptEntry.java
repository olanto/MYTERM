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
import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;

public class ConceptEntry {

    private Resources resource;
    private Concepts concept;
    private boolean createInDB = false;
    private String extraConcepts;
    public Vector<LangEntry> listlang;

    public ConceptEntry(Resources resource, boolean createInDB) {
        this.resource = resource;
        this.createInDB = createInDB;
        listlang = new Vector<>();
        prepareConcept();
    }

    public ConceptEntry(Concepts c, Boolean createInDB) {
        this.concept = c;
        this.createInDB = createInDB;
        listlang = new Vector<>();
        extraConcepts = "";
    }

    public void prepareConcept() {
        concept = new Concepts();
        extraConcepts = "";
    }

    public void flush() {
        if (createInDB) {
            addConceptToDB();
            for (int i = 0; i < listlang.size(); i++) {
                LangEntry lan = listlang.get(i);
                lan.addLangToDB(concept);
            }
        }
        extraConcepts = "";
    }

    public void addConceptToDB() {
        if (createInDB) {
            concept.setExtra(extraConcepts);
            concept = ManageConcept.addConceptToResource(resource, concept);
        }
    }

    public void addTerm(String lang, String term_form) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntry lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                lan.addTerm(term_form);
                return;
            }
        }
        LangEntry lan = new LangEntry(lang);
        lan.addTerm(term_form);
        listlang.add(lan);
    }

    public Terms getTerm(String lang, int position) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntry lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                return lan.getTerm(position);
            }
        }
        return null;
    }

    public Terms getTermLast(String lang) {
        for (int i = 0; i < listlang.size(); i++) {
            LangEntry lan = listlang.get(i);
            if (lan.lan.getIdLanguage().equals(lang)) {
                return lan.getTermLast();
            }
        }
        return null;
    }

    public Concepts getConcept() {
        return this.concept;
    }
}
