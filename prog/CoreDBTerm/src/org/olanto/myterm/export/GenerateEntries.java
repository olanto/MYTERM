/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
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
package org.olanto.myterm.export;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import static org.olanto.myterm.export.ExportTBXFromDB.*;
import static org.olanto.myterm.export.JdomUtilities.*;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.ManageLangsets;
import org.olanto.myterm.coredb.ManageTerm;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 * gÃ©nÃ©re la partie fixe de l'entÃªte du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateEntries {

    /**
     * génère les entrées
     *
     * @return
     */
    public static Element getTermsFromDB() {
        Element res = makeElem("text");
        Element body = makeElem("body");
        res.addContent(body);
        body.addContent(getEntry("1", "en", "e-mail", "fr", "couriel"));
        addConcept(body, resource);

        return res;
    }

    public static void addConcept(Element body, Resources res) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdResource");
        query.setParameter("idResource", res.getIdResource());
        List<Concepts> listOfConcept = query.getResultList();
        for (Concepts con : listOfConcept) {
            Element termentry = makeElem("termEntry").setAttribute("id", con.getIdConcept().toString());
            addConceptElem(termentry, con);
            body.addContent(termentry);
            addLangset(termentry, con);
        }
    }

    public static void addConceptElem(Element termentry, Concepts con) {
        if (con.getSubjectField() != null) {
            termentry.addContent(makeElem("descrip", con.getSubjectField()).setAttribute("type", "subjectField"));
        }
        if (con.getConceptDefinition() != null) {
            Element descgrp = makeElem("descripGrp");
            termentry.addContent(descgrp);
            descgrp.addContent(makeElem("descrip", con.getConceptDefinition()).setAttribute("type", "definition"));
            if (con.getConceptSourceDefinition() != null) {
                descgrp.addContent(makeElem("admin", con.getConceptSourceDefinition()).setAttribute("type", "source"));
            }
        }
        if (con.getConceptNote() != null) {
            termentry.addContent(makeElem("note", con.getConceptNote()));
        }
//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }

    public static void addLangset(Element termentry, Concepts con) {
        Query query = TermDB.em.createNamedQuery("Langsets.findByIdConcept");
        query.setParameter("idConcept", con.getIdConcept());
        List<Langsets> listOfLangsets = query.getResultList();
        for (Langsets lan : listOfLangsets) {
            Element langset = makeElem("langSet").setAttribute("lang", lan.getIdLanguage(), Namespace.XML_NAMESPACE);
            addLangsetElem(langset, lan);
            termentry.addContent(langset);
            addTerm(langset, lan);
        }
    }

    public static void addLangsetElem(Element langset, Langsets lan) {
        if (lan.getLangsetNote() != null) {
            langset.addContent(makeElem("descrip", lan.getLangsetNote()).setAttribute("type", "definition"));
        }
//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }

    public static void addTerm(Element langset, Langsets lan) {
        Query query = TermDB.em.createNamedQuery("Terms.findByIdLangset");
        query.setParameter("idLangset", lan.getIdLangset());
        List<Terms> listOfTerms = query.getResultList();
        for (Terms ter : listOfTerms) {
            Element tig = makeElem("tig");
            langset.addContent(tig);
            tig.addContent(makeElem("term", ter.getTermForm()));
        }
    }

    public static Element getEntry(String id, String lang1, String t1, String lang2, String t2) {
        Element res = makeElem("termEntry").setAttribute("id", id);

        Element lset1 = makeElem("langSet").setAttribute("lang", lang1, Namespace.XML_NAMESPACE);
        res.addContent(lset1);
        Element tig1 = makeElem("tig");
        lset1.addContent(tig1);
        tig1.addContent(makeElem("term", t1));

        return res;
    }
}