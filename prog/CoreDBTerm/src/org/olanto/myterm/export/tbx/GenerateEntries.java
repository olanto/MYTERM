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
package org.olanto.myterm.export.tbx;

import java.util.List;
import javax.persistence.Query;
import static org.olanto.myterm.export.tbx.ExportTBXFromDB.*;
import static org.olanto.myterm.export.tbx.JdomUtilities.*;

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
            Element termentry = makeElem("termEntry");
            if (con.getImportedref() == null) {
                termentry.setAttribute("id", con.getIdConcept().toString());
            } else termentry.setAttribute("id", con.getImportedref());
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
            termentry.addContent(makeElem("note", con.getConceptNote()));
        }  
        if (con.getCrossref()!= null) {
            String ref=con.getCrossref().replace("\n", "");
            String[] part=ref.split(";");
            termentry.addContent(makeElem("ref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
        }
       if (con.getExtcrossref()!= null) {
            String ref=con.getExtcrossref().replace("\n", "");
            String[] part=ref.split(";");
            termentry.addContent(makeElem("xref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
        }
     if (con.getImage()!= null) {
            String ref=con.getImage().replace("\n", "");
            String[] part=ref.split(";");
            termentry.addContent(makeElem("xref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
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
     
        if (ter.getTermContext() != null && ter.getTermSourceContext() != null) {
            Element descgrp = makeElem("descripGrp");
            tig.addContent(descgrp);
            descgrp.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
            descgrp.addContent(makeElem("admin", ter.getTermSourceContext()).setAttribute("type", "source"));
        }
        if (ter.getTermContext() != null && ter.getTermSourceContext() == null) {
            tig.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
        }
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
        if (ter.getTermNote() != null) {
            tig.addContent(makeElem("note", ter.getTermNote()));
        }
        if (ter.getTermPartofspeech() != null) {
            //System.out.println("partOfSpeech"+ter.getTermPartofspeech());
            tig.addContent(makeElem("termNote", ter.getTermPartofspeech()).setAttribute("type", "partOfSpeech"));
        }
        if (ter.getTermAdminStatus() != null) {
            tig.addContent(makeElem("termNote", ter.getTermAdminStatus()).setAttribute("type", "administrativeStatus"));
        }
        if (ter.getTermGender() != null) {
            tig.addContent(makeElem("termNote", ter.getTermGender()).setAttribute("type", "grammaticalGender"));
        }
        if (ter.getTermType() != null) {
            tig.addContent(makeElem("termNote", ter.getTermType()).setAttribute("type", "termType"));
        }
        if (ter.getTermGeoUsage() != null) {
            tig.addContent(makeElem("termNote", ter.getTermGeoUsage()).setAttribute("type", "geographicalUsage"));
        }
        if (ter.getTermDefinition() != null) {
            tig.addContent(makeElem("descrip", ter.getTermContext()).setAttribute("type", "context"));
        }
         if (ter.getTermDefinition() != null) {
            tig.addContent(makeElem("descrip", ter.getTermDefinition()).setAttribute("type", "definition"));
        }
        if (ter.getTermDefinition() != null) {
            tig.addContent(makeElem("admin", ter.getTermSourceDefinition()).setAttribute("type", "sourceDefinition"));
        }
        if (ter.getSup0() != null) {
            tig.addContent(makeElem("termNote", ter.getSup0()).setAttribute("type", "sup0"));
        }
        if (ter.getSup1() != null) {
            tig.addContent(makeElem("termNote", ter.getSup1()).setAttribute("type", "sup1"));
        }
        if (ter.getSup2() != null) {
            tig.addContent(makeElem("termNote", ter.getSup2()).setAttribute("type", "sup2"));
        }
        if (ter.getSup3() != null) {
            tig.addContent(makeElem("termNote", ter.getSup3()).setAttribute("type", "sup3"));
        }
        if (ter.getSup4() != null) {
            tig.addContent(makeElem("termNote", ter.getSup4()).setAttribute("type", "sup4"));
        }
        if (ter.getTermSource() != null) {
            tig.addContent(makeElem("termNote", ter.getTermGeoUsage()).setAttribute("type", "geographicalUsage"));
        }
       if (ter.getTermSource() != null) {
            tig.addContent(makeElem("termNote", ter.getTermUsage()).setAttribute("type", "usage"));
        }
       if (ter.getTermSource() != null) {
            tig.addContent(makeElem("admin", ter.getTermSource()).setAttribute("type", "source"));
        }
      if (ter.getCrossref()!= null) {
            String ref=ter.getCrossref().replace("\n", "");
            String[] part=ref.split(";");
            tig.addContent(makeElem("ref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
        }
       if (ter.getExtcrossref()!= null) {
            String ref=ter.getExtcrossref().replace("\n", "");
            String[] part=ref.split(";");
            tig.addContent(makeElem("xref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
        }
     if (ter.getImage()!= null) {
            String ref=ter.getImage().replace("\n", "");
            String[] part=ref.split(";");
            tig.addContent(makeElem("xref", part[2]).setAttribute("type", part[0]).setAttribute("target", part[1]));
        }

//            if (lan.getExtra()!=null){
//                langset.addContent(makeElem("descrip",lan.getExtra()).setAttribute("type", "definition"));              
//            }

    }
}