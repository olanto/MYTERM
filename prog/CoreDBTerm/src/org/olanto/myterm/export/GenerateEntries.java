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
import javax.persistence.Query;
import static org.olanto.myterm.export.ExportTBXFromDB.*;
import static org.olanto.myterm.export.JdomUtilities.*;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;

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
         if (con.getCreateBy() != null) {
            Element transgrp = makeElem("transacGrp");
            termentry.addContent(transgrp);
            String user =Queries.getOwnerbyID(con.getCreateBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "origination").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (con.getCreation() != null) {               
                transgrp.addContent(makeElem("date", getFormattedDate(con.getCreation())));
            }
        }
         if (con.getLastmodifiedBy() != null) {
            Element transgrp = makeElem("transacGrp");
            termentry.addContent(transgrp);
            String user =Queries.getOwnerbyID(con.getLastmodifiedBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "modification").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (con.getLastmodified() != null) {               
                transgrp.addContent(makeElem("date", getFormattedDate(con.getLastmodified())));
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
            addTermsetElem(tig, ter);

        }
    }

      public static void addTermsetElem(Element tig, Terms ter) {
        if (ter.getTermPartofspeech() != null) {
            tig.addContent(makeElem("termNote", ter.getTermPartofspeech()).setAttribute("type", "partOfSpeech"));
        }
//        if (con.getConceptDefinition() != null) {
//            Element descgrp = makeElem("descripGrp");
//            termentry.addContent(descgrp);
//            descgrp.addContent(makeElem("descrip", con.getConceptDefinition()).setAttribute("type", "definition"));
//            if (con.getConceptSourceDefinition() != null) {
//                descgrp.addContent(makeElem("admin", con.getConceptSourceDefinition()).setAttribute("type", "source"));
//            }
//        }
//         if (con.getCreateBy() != null) {
//            Element transgrp = makeElem("transacGrp");
//            termentry.addContent(transgrp);
//            String user =Queries.getOwnerbyID(con.getCreateBy().longValue()).getOwnerLastName();
//            transgrp.addContent(makeElem("transac", "origination").setAttribute("type", "transactionType"));
//            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
//            if (con.getCreation() != null) {               
//                transgrp.addContent(makeElem("date", getFormattedDate(con.getCreation())));
//            }
//        }
//         if (con.getLastmodifiedBy() != null) {
//            Element transgrp = makeElem("transacGrp");
//            termentry.addContent(transgrp);
//            String user =Queries.getOwnerbyID(con.getLastmodifiedBy().longValue()).getOwnerLastName();
//            transgrp.addContent(makeElem("transac", "modification").setAttribute("type", "transactionType"));
//            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
//            if (con.getLastmodified() != null) {               
//                transgrp.addContent(makeElem("date", getFormattedDate(con.getLastmodified())));
//            }
//        }
     if (ter.getTermNote()!= null) {
            tig.addContent(makeElem("note", ter.getTermNote()));
        }
//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }
 
    
 }