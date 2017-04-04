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
package org.olanto.myterm.print.xml;

import java.util.List;
import javax.persistence.Query;
import static org.olanto.myterm.print.xml.JdomUtilities.*;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;

/**
 * gÃ©nÃ©re la partie fixe de l'entÃªte du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateXMLEntries {

    /**
     * génère les entrées
     *
     * @return
     */
    public static Element getTermsFromDB(String usrlang, long conceptid) {
        LabelCodification.init(usrlang);
        Element res = makeElem("body");
        addConcept(res, conceptid);

        return res;
    }

    public static void addConcept(Element body, long conceptid) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdConcept");
        query.setParameter("idConcept", conceptid);
        List<Concepts> listOfConcept = query.getResultList();
        for (Concepts con : listOfConcept) {
            Element termentry = makeElem("termEntry");
            if (con.getImportedref() == null) {
                termentry.addContent(makeElem("idConcept").addContent("ID: " + con.getIdConcept().toString()));
            } else {
                termentry.addContent(makeElem("idConcept").addContent("ID: " + con.getImportedref()));
            }
            addConceptElem(termentry, con);
            body.addContent(termentry);
            addLangset(termentry, con);
        }
    }

    public static Element addXMLNice(Element elem, String tag, String lib, String content) {
        Element child = makeElem(tag);
        if (!content.equals("")) {
            Element title = makeElem("e", LabelCodification.getMsg(lib));
        Element cont = makeElem("i", ReplaceMediaLink.replaceMediaLink(content));
            Element txt = makeElem("p");
            txt.addContent(title);
            txt.addContent(cont);
            child.addContent(txt);
        }
        //else System.out.println("empty");
        elem.addContent(child);
        return child;
    }

 
    
    public static void addConceptElem(Element termentry, Concepts con) {
        if (con.getSubjectField() != null) {

            //termentry.addContent(makeElem("descrip", con.getSubjectField()).setAttribute("type", "subjectField"));
            addXMLNice(termentry, "subjectField", "lbl.c.subject_field", con.getSubjectField());
        }
        if (con.getConceptDefinition() != null) {
            Element descgrp = makeElem("descripGrp");
            termentry.addContent(descgrp);
            //descgrp.addContent(makeElem("descrip", con.getConceptDefinition()).setAttribute("type", "definition"));
            addXMLNice(descgrp, "cdefinition", "lbl.c.definition", con.getConceptDefinition());
            if (con.getConceptSourceDefinition() != null) {
                //descgrp.addContent(makeElem("admin", con.getConceptSourceDefinition()).setAttribute("type", "source"));
                addXMLNice(descgrp, "csource", "lbl.c.source_definition", con.getConceptSourceDefinition());
            }
        }
        if (con.getCreateBy() != null) {
            Element transgrp = makeElem("transacGrp");
            termentry.addContent(transgrp);
            String user = Queries.getOwnerbyID(con.getCreateBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "origination").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (con.getCreation() != null) {
                transgrp.addContent(makeElem("date", getFormattedDate(con.getCreation())));
            }
        }
        if (con.getLastmodifiedBy() != null) {
            Element transgrp = makeElem("transacGrp");
            termentry.addContent(transgrp);
            String user = Queries.getOwnerbyID(con.getLastmodifiedBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "modification").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (con.getLastmodified() != null) {
                transgrp.addContent(makeElem("date", getFormattedDate(con.getLastmodified())));
            }
        }
        if (con.getConceptNote() != null) {
            //termentry.addContent(makeElem("note", con.getConceptNote()));
            //descgrp.addContent(makeElem("admin", con.getConceptSourceDefinition()).setAttribute("type", "source"));
            addXMLNice(termentry, "cnote", "lbl.c.note", con.getConceptNote());
        }
        if (con.getCrossref() != null) {
            String ref = con.getCrossref().replace("\n", "");
            String[] part = ref.split(";");
            termentry.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
        }
        if (con.getExtcrossref() != null) {
            String ref = con.getExtcrossref().replace("\n", "");
            String[] part = ref.split(";");
            termentry.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
        }
        if (con.getImage() != null) {
            String ref = con.getImage().replace("\n", "");
            String[] part = ref.split(";");
            termentry.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
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
            langset.addContent(makeElem("idLang").addContent("Lang: " + lan.getIdLanguage().toString()));
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
            //tig.addContent(makeElem("term", ter.getTermForm()));
            addXMLNice(tig, "term", null, ter.getTermForm());
            addTermsetElem(tig, ter);
            //langset.addContent(makeElem("hr"));

        }
    }

    public static void addTermsetElem(Element tig, Terms ter) {


        if (ter.getCreateBy() != null) {
            Element transgrp = makeElem("transacGrp");
            tig.addContent(transgrp);
            String user = Queries.getOwnerbyID(ter.getCreateBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "origination").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (ter.getCreation() != null) {
                transgrp.addContent(makeElem("date", getFormattedDate(ter.getCreation())));
            }
        }
        if (ter.getLastmodifiedBy() != null) {
            Element transgrp = makeElem("transacGrp");
            tig.addContent(transgrp);
            String user = Queries.getOwnerbyID(ter.getLastmodifiedBy().longValue()).getOwnerLastName();
            transgrp.addContent(makeElem("transac", "modification").setAttribute("type", "transactionType"));
            transgrp.addContent(makeElem("transacNote", user).setAttribute("type", "responsibility"));
            if (ter.getLastmodified() != null) {
                transgrp.addContent(makeElem("date", getFormattedDate(ter.getLastmodified())));
            }
        }
         if (ter.getTermSource() != null) {
            // tig.addContent(makeElem("admin", ter.getTermSource()).setAttribute("type", "source"));
            addXMLNice(tig, "tsource", "lbl.t.source", ter.getTermSource());
        }
       if (ter.getTermDefinition() != null) {
            //tig.addContent(makeElem("descrip", ter.getTermDefinition()).setAttribute("type", "definition"));
            addXMLNice(tig, "tdefinition", "lbl.t.definition", ter.getTermDefinition());
        }
        if (ter.getTermSourceDefinition() != null) {
            //tig.addContent(makeElem("admin", ter.getTermSourceDefinition()).setAttribute("type", "sourceDefinition"));
            addXMLNice(tig, "tsource_definition", "lbl.t.source_definition", ter.getTermSourceDefinition());
        }
        if (ter.getTermContext() != null && ter.getTermSourceContext() != null) {
            Element descgrp = makeElem("descripGrp");
            tig.addContent(descgrp);
            //  descgrp.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
            addXMLNice(descgrp, "tcontext", "lbl.t.context", ter.getTermContext());
            //descgrp.addContent(makeElem("admin", ter.getTermSourceContext()).setAttribute("type", "source"));
            addXMLNice(descgrp, "tsource_context", "lbl.t.source_context", ter.getTermSourceContext());

        }
        if (ter.getTermContext() != null && ter.getTermSourceContext() == null) {
            //tig.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
            addXMLNice(tig, "tcontext", "lbl.t.context", ter.getTermContext());
        }
        if (ter.getTermNote() != "") {
            //tig.addContent(makeElem("note", ter.getTermNote()));
            addXMLNice(tig, "tnote", "lbl.t.note", ter.getTermNote());
        }
//        if (ter.getTermPartofspeech() != null) {
//            //System.out.println("partOfSpeech"+ter.getTermPartofspeech());
//            //tig.addContent(makeElem("termNote", ter.getTermPartofspeech()).setAttribute("type", "partOfSpeech"));
//            addXMLNice(tig, "tpartOfSpeech", "lbl.t.part_of_speech", ter.getTermPartofspeech());
//        }
        if (ter.getTermAdminStatus() != null) {
            //tig.addContent(makeElem("termNote", ter.getTermAdminStatus()).setAttribute("type", "administrativeStatus"));
            addXMLNice(tig, "administrativeStatus", "lbl.t.status", ter.getTermAdminStatus());
        }
//        if (ter.getTermGender() != null) {
//            //tig.addContent(makeElem("termNote", ter.getTermGender()).setAttribute("type", "grammaticalGender"));
//            addXMLNice(tig, "grammaticalGender", "lbl.t.gender", ter.getTermGender());
//        }
        if (ter.getTermType() != null) {
            //tig.addContent(makeElem("termNote", ter.getTermType()).setAttribute("type", "termType"));
            addXMLNice(tig, "termType", "lbl.t.type", ter.getTermType());
        }
        if (ter.getTermGeoUsage() != null) {
            //tig.addContent(makeElem("termNote", ter.getTermGeoUsage()).setAttribute("type", "geographicalUsage"));
            addXMLNice(tig, "geographicalUsage", "lbl.t.geo_usage", ter.getTermGeoUsage());
        }
        if (ter.getSup0() != null) {
            //tig.addContent(makeElem("termNote", ter.getSup0()).setAttribute("type", "sup0"));
            addXMLNice(tig, "sup0", "lbl.t.technical_note", ter.getSup0());
        }
        if (ter.getSup1() != null) {
            //tig.addContent(makeElem("termNote", ter.getSup1()).setAttribute("type", "sup1"));
            addXMLNice(tig, "sup1", "lbl.t.linguistic_note", ter.getSup1());
        }
        if (ter.getSup2() != null) {
            tig.addContent(makeElem("termNote", ter.getSup2()).setAttribute("type", "sup2"));
            addXMLNice(tig, "sup2", "lbl.t.reference_note", ter.getSup2());
        }
        if (ter.getSup3() != null) {
            //  tig.addContent(makeElem("termNote", ter.getSup3()).setAttribute("type", "sup3"));
            addXMLNice(tig, "sup3", null, ter.getSup3());
        }
        if (ter.getSup4() != null) {
            // tig.addContent(makeElem("termNote", ter.getSup4()).setAttribute("type", "sup4"));
            addXMLNice(tig, "sup4", null, ter.getSup4());
        }
        if (ter.getTermUsage() != null) {
            // tig.addContent(makeElem("termNote", ter.getTermUsage()).setAttribute("type", "usage"));
            addXMLNice(tig, "tusage", "lbl.t.usage", ter.getTermUsage());
        }
        if (ter.getCrossref() != null) {
            String ref = ter.getCrossref().replace("\n", "");
            String[] part = ref.split(";");
            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
        }
        if (ter.getExtcrossref() != null) {
            String ref = ter.getExtcrossref().replace("\n", "");
            String[] part = ref.split(";");
            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
        }
        if (ter.getImage() != null) {
            String ref = ter.getImage().replace("\n", "");
            String[] part = ref.split(";");
            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
        }

//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }
}