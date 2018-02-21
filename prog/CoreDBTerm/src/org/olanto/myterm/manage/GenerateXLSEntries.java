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
package org.olanto.myterm.manage;

import java.util.List;
import javax.persistence.Query;
import static org.olanto.myterm.manage.JdomUtilities.*;

import org.jdom2.Element;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;
import static org.olanto.myterm.manage.ExportXLSFromDB.*;

/**
 * gÃ©nÃ©re la partie fixe de l'entÃªte du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateXLSEntries {

    /**
     * génère les entrées
     *
     * @return
     */
    static String idconcept;
    static String subjectField;
    static String cdefinition;
    static String csource;
    static String Cuser;
    static String Cdate;
    static String CMuser;
    static String CMdate;
    static String idLang;
    static String Tform;
    static String Tdef;
    static String Tuser;
    static String Tdate;
    static String TMuser;
    static String TMdate;

    public static void getTermsFromDB(String usrlang) {
        LabelCodification.init(usrlang);
        addConcept(resource);

    }

    public static void addConcept(Resources res) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdResource");
        query.setParameter("idResource", res.getIdResource());
        List<Concepts> listOfConcept = query.getResultList();
        for (Concepts con : listOfConcept) {
            idconcept = "";
            subjectField = "";
            cdefinition = "";
            csource = "";
            Cuser = "";
            Cdate = "";
            CMuser = "";
            CMdate = "";

            Element termentry = makeElem("termEntry");
            if (con.getImportedref() == null) {
                idconcept = con.getIdConcept().toString();
            } else {
                idconcept = con.getImportedref();
            }
            addConceptElem(con);
            addLangset(con);
        }
    }

    public static Element addXMLNice(Element elem, String tag, String lib, String content) {
        Element child = makeElem(tag);
        Element title = makeElem("e", LabelCodification.getMsg(lib));
        Element cont = makeElem("i", content);
        Element txt = makeElem("p");
        txt.addContent(title);
        txt.addContent(cont);
        child.addContent(txt);

        elem.addContent(child);
        return child;
    }

    public static void addConceptElem(Concepts con) {
        if (con.getSubjectField() != null) {
            subjectField = con.getSubjectField();
        }
        if (con.getConceptDefinition() != null) {
            cdefinition = con.getConceptDefinition();
            if (con.getConceptSourceDefinition() != null) {
                csource = con.getConceptSourceDefinition();
            }
        }
        if (con.getCreateBy() != null) {
            Cuser = Queries.getOwnerbyID(con.getCreateBy().longValue()).getOwnerLastName();
            if (con.getCreation() != null) {
                Cdate = getFormattedDate(con.getCreation());
            }
        }
        if (con.getLastmodifiedBy() != null) {
            CMuser = Queries.getOwnerbyID(con.getLastmodifiedBy().longValue()).getOwnerLastName();
            if (con.getLastmodified() != null) {
                CMdate = getFormattedDate(con.getLastmodified());
            }
        }
    }

    public static void addLangset(Concepts con) {
        Query query = TermDB.em.createNamedQuery("Langsets.findByIdConcept");
        query.setParameter("idConcept", con.getIdConcept());
        List<Langsets> listOfLangsets = query.getResultList();
        for (Langsets lan : listOfLangsets) {
            idLang = lan.getIdLanguage().toString();
            addTerm(lan);
        }
    }

    public static void addTerm(Langsets lan) {
        Query query = TermDB.em.createNamedQuery("Terms.findByIdLangset");
        query.setParameter("idLangset", lan.getIdLangset());
        List<Terms> listOfTerms = query.getResultList();
        for (Terms ter : listOfTerms) {
            Tform = ter.getTermForm();
            Tdef = "";
            Tuser = "";
            Tdate = "";
            TMuser = "";
            TMdate = "";
            addTermsetElem(ter);
            ExportXLSFromDB.writeFile(
                    idconcept + "\t"
                    + subjectField + "\t"
                    + cdefinition + "\t"
                    + csource + "\t"
                    + Cuser + "\t"
                    + Cdate + "\t"
                    + CMuser + "\t"
                    + CMdate + "\t"
                    + idLang + "\t"
                    + Tform + "\t"
                    + Tdef + "\t"
                    + Tuser + "\t"
                    + Tdate + "\t"
                    + TMuser + "\t"
                    + TMdate + "\n");
        }
    }

    public static void addTermsetElem(Terms ter) {


        if (ter.getCreateBy() != null) {
            Tuser = Queries.getOwnerbyID(ter.getCreateBy().longValue()).getOwnerLastName();
            if (ter.getCreation() != null) {
                Tdate = getFormattedDate(ter.getCreation());
            }
        }
        if (ter.getLastmodifiedBy() != null) {
            TMuser = Queries.getOwnerbyID(ter.getLastmodifiedBy().longValue()).getOwnerLastName();
            if (ter.getLastmodified() != null) {
                TMdate = getFormattedDate(ter.getLastmodified());
            }
        }
         if (ter.getTermDefinition() != null) {
            //tig.addContent(makeElem("descrip", ter.getTermDefinition()).setAttribute("type", "definition"));
            Tdef = ter.getTermDefinition();
        }
//        if (ter.getTermSourceDefinition() != null) {
//            //tig.addContent(makeElem("admin", ter.getTermSourceDefinition()).setAttribute("type", "sourceDefinition"));
//            addXMLNice(tig, "tsource_definition", "lbl.t.source_definition", ter.getTermSourceDefinition());
//        }
//        if (ter.getTermContext() != null && ter.getTermSourceContext() != null) {
//            Element descgrp = makeElem("descripGrp");
//            tig.addContent(descgrp);
//            //  descgrp.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
//            addXMLNice(descgrp, "tcontext", "lbl.t.context", ter.getTermContext());
//            //descgrp.addContent(makeElem("admin", ter.getTermSourceContext()).setAttribute("type", "source"));
//            addXMLNice(descgrp, "tsource_context", "lbl.t.source_context", ter.getTermSourceContext());
//
//        }
//        if (ter.getTermContext() != null && ter.getTermSourceContext() == null) {
//            //tig.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
//            addXMLNice(tig, "tcontext", "lbl.t.context", ter.getTermContext());
//        }
//        if (ter.getTermNote() != "") {
//            //tig.addContent(makeElem("note", ter.getTermNote()));
//            addXMLNice(tig, "tnote", "lbl.t.note", ter.getTermNote());
//        }
//        if (ter.getTermPartofspeech() != null) {
//            //System.out.println("partOfSpeech"+ter.getTermPartofspeech());
//            //tig.addContent(makeElem("termNote", ter.getTermPartofspeech()).setAttribute("type", "partOfSpeech"));
//            addXMLNice(tig, "tpartOfSpeech", "lbl.t.part_of_speech", ter.getTermPartofspeech());
//        }
//        if (ter.getTermAdminStatus() != null) {
//            //tig.addContent(makeElem("termNote", ter.getTermAdminStatus()).setAttribute("type", "administrativeStatus"));
//            addXMLNice(tig, "administrativeStatus", "lbl.t.status", ter.getTermAdminStatus());
//        }
//        if (ter.getTermGender() != null) {
//            //tig.addContent(makeElem("termNote", ter.getTermGender()).setAttribute("type", "grammaticalGender"));
//            addXMLNice(tig, "grammaticalGender", "lbl.t.gender", ter.getTermGender());
//        }
//        if (ter.getTermType() != null) {
//            //tig.addContent(makeElem("termNote", ter.getTermType()).setAttribute("type", "termType"));
//            addXMLNice(tig, "termType", "lbl.t.type", ter.getTermType());
//        }
//        if (ter.getTermGeoUsage() != null) {
//            //tig.addContent(makeElem("termNote", ter.getTermGeoUsage()).setAttribute("type", "geographicalUsage"));
//            addXMLNice(tig, "geographicalUsage", "lbl.t.geo_usage", ter.getTermGeoUsage());
//        }
//        if (ter.getSup0() != null) {
//            //tig.addContent(makeElem("termNote", ter.getSup0()).setAttribute("type", "sup0"));
//            addXMLNice(tig, "sup0", "lbl.t.technical_note", ter.getSup0());
//        }
//        if (ter.getSup1() != null) {
//            //tig.addContent(makeElem("termNote", ter.getSup1()).setAttribute("type", "sup1"));
//            addXMLNice(tig, "sup1", "lbl.t.linguistic_note", ter.getSup1());
//        }
//        if (ter.getSup2() != null) {
//            tig.addContent(makeElem("termNote", ter.getSup2()).setAttribute("type", "sup2"));
//            addXMLNice(tig, "sup2", "lbl.t.reference_note", ter.getSup2());
//        }
//        if (ter.getSup3() != null) {
//            //  tig.addContent(makeElem("termNote", ter.getSup3()).setAttribute("type", "sup3"));
//            addXMLNice(tig, "sup3", null, ter.getSup3());
//        }
//        if (ter.getSup4() != null) {
//            // tig.addContent(makeElem("termNote", ter.getSup4()).setAttribute("type", "sup4"));
//            addXMLNice(tig, "sup4", null, ter.getSup4());
//        }
//        if (ter.getTermUsage() != null) {
//            // tig.addContent(makeElem("termNote", ter.getTermUsage()).setAttribute("type", "usage"));
//            addXMLNice(tig, "tusage", "lbl.t.usage", ter.getTermUsage());
//        }
//        if (ter.getTermSource() != null) {
//            // tig.addContent(makeElem("admin", ter.getTermSource()).setAttribute("type", "source"));
//            addXMLNice(tig, "tsource", "lbl.t.source", ter.getTermSource());
//        }
//        if (ter.getCrossref() != null) {
//            String ref = ter.getCrossref().replace("\n", "");
//            String[] part = ref.split(";");
//            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
//        }
//        if (ter.getExtcrossref() != null) {
//            String ref = ter.getExtcrossref().replace("\n", "");
//            String[] part = ref.split(";");
//            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
//        }
//        if (ter.getImage() != null) {
//            String ref = ter.getImage().replace("\n", "");
//            String[] part = ref.split(";");
//            tig.addContent(makeElem("p", part[0] + ": " + part[1] + ", " + part[2]));
//        }
//
//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }
}