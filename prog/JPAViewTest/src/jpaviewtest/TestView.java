/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest;

import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaviewtest.entities.VjConceptdetail;
import jpaviewtest.entities.VjReslang;
import jpaviewtest.entities.VjSource;
import jpaviewtest.entities.VjSourcetarget;
import jpaviewtest.entities.VjUsersLanguages;
import jpaviewtest.entities.VjUsersResources;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.entry.LangEntry;

/**
 *
 * @author simple
 */
public class TestView {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {

//        System.out.println(getSourceForThis("tuna%", "EN", "FR", "-1", "-1"));
//        System.out.println(getTargetForThis("tunas", "EN", "FR", "-1", "-1"));
//        getConceptAndAssociatedTerms(3534);
//        getApproveForThis("EN", 1001);
    }

    public static void init() {
        if (emf == null) {
            System.out.println("init BD connection");
            emf = Persistence.createEntityManagerFactory("JPAViewTestPU");
            em = emf.createEntityManager();
        }
    }

    public static String getTermsInfo(long conceptID, String lang) {
        init();
        Query query = em.createNamedQuery("VjConceptdetail.findByIdConceptAndLanguage");
        query.setParameter("idConcept", conceptID);
        query.setParameter("idLanguage", lang);
        List<VjConceptdetail> resultQ = query.getResultList();

        StringBuilder result = new StringBuilder("");
        for (VjConceptdetail res : resultQ) {
            Terms t = Queries.getTermByID(res.getIdTerm());
            result.append("<div>");
            result.append("<table>");
            result.append("<tr>");
            result.append("<td>");
            if ((t.getTermForm() != null)) {
                result.append("&nbsp").append("<span class = \"tform\">Form: </span>").append(t.getTermForm()).append("<br/>");
            }
            if ((t.getTermDefinition() != null)) {
                result.append("&nbsp").append("<span class = \"def\">Definition: </span>").append(t.getTermDefinition()).append("<br/>");
            }
            if ((t.getTermSource() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Source: </span>").append(t.getTermSource()).append("<br/>");
            }
            if ((t.getTermSourceDefinition() != null)) {
                result.append("&nbsp").append("<span class = \"defsrc\">Source Definition: </span>").append(t.getTermSourceDefinition()).append("<br/>");
            }
            if ((t.getTermSourceContext() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Source Context: </span>").append(t.getTermSourceContext()).append("<br/>");
            }
            if ((t.getTermNote() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Note: </span>").append(t.getTermNote()).append("<br/>");
            }
            if ((t.getTermPartofspeech() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Part of Speech: </span>").append(t.getTermPartofspeech()).append("<br/>");
            }
            if ((t.getTermType() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Type: </span>").append(t.getTermType()).append("<br/>");
            }
            if ((t.getTermUsage() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Usage: </span>").append(t.getTermUsage()).append("<br/>");
            }
            if ((t.getTermContext() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Context: </span>").append(t.getTermContext()).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((t.getCreateBy() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Created By: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(t.getCreateBy().toString()))).append("<br/>");
            }
            if ((t.getCreation() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Creation Date: </span>").append(t.getCreation()).append("<br/>");
            }
            if ((t.getLastmodifiedBy() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Last modified by: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(t.getLastmodifiedBy().toString()))).append("<br/>");
            }
            if ((t.getLastmodified() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Last modification on: </span>").append(t.getLastmodified()).append("<br/>");
            }
            if ((t.getCrossref() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Cross Reference: </span>").append(t.getCrossref()).append("<br/>");
            }
            if ((t.getExtcrossref() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Extra Cross Reference: </span>").append(t.getExtcrossref()).append("<br/>");
            }
            if (t.getStatus() != ' ') {
                result.append("&nbsp").append("<span class = \"extrainfo\">Status: </span>").append(t.getStatus()).append("<br/>");
            }
            if ((t.getTermAdminStatus() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Admin Status: </span>").append(t.getTermAdminStatus()).append("<br/>");
            }
            if ((t.getTermGender() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Gender: </span>").append(t.getTermGender()).append("<br/>");
            }
            if ((t.getTermGeoUsage() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Gero Usage: </span>").append(t.getTermGeoUsage()).append("<br/>");
            }
            result.append("</td>").append("</tr>");
            result.append("</table>");
        }
        result.append("</div>");
        return result.toString();
    }

    public static ConceptEntry getConceptAndAssociatedTerms(long conceptID) {
        TermDB.restart();
        init();
        Concepts cpt = Queries.getConceptByID(conceptID);
        if (cpt != null) {
            ConceptEntry conceptEntry = new ConceptEntry(cpt, false);
//            System.out.println(conceptEntry.getConcept().getIdConcept());
            List<Langsets> langsets = Queries.getLangSetByConcept(conceptID);
            if (!langsets.isEmpty()) {
                for (Langsets ls : langsets) {
                    LangEntry langE = new LangEntry();
                    langE.lan = ls;
                    Query query = em.createNamedQuery("VjConceptdetail.findByIdConceptAndLangSet");
                    query.setParameter("idConcept", conceptID);
                    query.setParameter("idLangset", ls.getIdLangset());
                    List<VjConceptdetail> resultQ = query.getResultList();
                    if (!resultQ.isEmpty()) {
                        for (VjConceptdetail res : resultQ) {
                            Terms t = Queries.getTermByID(res.getIdTerm());
                            langE.listterm.add(t);
                        }
                    }
                    conceptEntry.listlang.add(langE);
//                    System.out.println("copying lang set from query: " + langE.lan.getIdLanguage());
                }
            }
            return conceptEntry;
        }
        return null;
    }

    public static String getTargetForThis(String term, String solang, String talang, String resID, String domID, long ownerID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if (resID.contains("-1")) {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSourcetarget.findPublicBySource");
            } else {
                query = em.createNamedQuery("VjSourcetarget.findPublicBySourceSubjectField");
                query.setParameter("subjectField", domID);
            }
        } else {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSourcetarget.findPublicBySourceResource");
                query.setParameter("idResource", Long.parseLong(resID));
            } else {
                query = em.createNamedQuery("VjSourcetarget.findPublicBySourceResourceSubjectField");
                query.setParameter("idResource", Long.parseLong(resID));
                query.setParameter("subjectField", domID);
            }
        }
        query.setParameter("idOwner", ownerID);
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<VjSourcetarget> resultQ = query.getResultList();
        if (!resultQ.isEmpty()) {
            for (VjSourcetarget result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#TS").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(result.getTarget()).append("</td>");
                res.append("</tr>");
            }
            return res.toString();
        }
        return null;
    }

    public static List<VjUsersLanguages> getLanguagesByOwner(long ownerID) {
        init();
        if (ownerID > 0) {
            Query query = em.createNamedQuery("VjUsersLanguages.findByIdOwner");
            query.setParameter("idOwner", ownerID);
            List<VjUsersLanguages> result = query.getResultList();
            return result;
        }
        return null;
    }

    public static String getTargetsForThis(long conceptID, String term, String solang, String resID, String domID, long ownerID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if (resID.contains("-1")) {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSourcetarget.findAllBySource2");
            } else {
                query = em.createNamedQuery("VjSourcetarget.findAllBySourceSubjectField2");
                query.setParameter("subjectField", domID);
            }
        } else {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSourcetarget.findAllBySourceResource2");
                query.setParameter("idResource", Long.parseLong(resID));
            } else {
                query = em.createNamedQuery("VjSourcetarget.findAllBySourceResourceSubjectField2");
                query.setParameter("idResource", Long.parseLong(resID));
                query.setParameter("subjectField", domID);
            }
        }
        query.setParameter("idOwner", ownerID);
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        List<VjSourcetarget> resultQ = query.getResultList();

        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            for (VjSourcetarget result : resultQ) {
                if (result.getIdConcept() == conceptID) {
                    res.append("<tr>");
                    res.append("<td>").append(result.getTarget()).append("</td>");
                    res.append("</tr>");
                }
            }
            res.append("</table>");
        }
        return res.toString();
    }

    public static String getTargetsForThis(long conceptID, String solang, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSourcetarget.findALLByIdOwner");
        query.setParameter("idOwner", ownerID);
        query.setParameter("solang", solang);
        List<VjSourcetarget> resultQ = query.getResultList();

        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            for (VjSourcetarget result : resultQ) {
                if (result.getIdConcept() == conceptID) {
                    res.append("<tr>");
                    res.append("<td>").append(result.getTarget()).append("</td>");
                    res.append("</tr>");
                }
            }
            res.append("</table>");
        }
        return res.toString();
    }

    public static String getSourceForThis(String term, String solang, String resID, String domID, long ownerID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        Query query1;
        if (resID.contains("-1")) {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSource.findAllByStatusSource");
                query1 = em.createNamedQuery("VjSource.findAllByStatusSource");
            } else {
                query = em.createNamedQuery("VjSource.findALLBysourceSubjectFieldStatus");
                query1 = em.createNamedQuery("VjSource.findALLBysourceSubjectFieldStatus");
                query.setParameter("subjectField", domID);
                query1.setParameter("subjectField", domID);
            }
        } else {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSource.findALLBysourceIdResourceStatus");
                query1 = em.createNamedQuery("VjSource.findALLBysourceIdResourceStatus");
                query.setParameter("idResource", Long.parseLong(resID));
                query1.setParameter("idResource", Long.parseLong(resID));
            } else {
                query = em.createNamedQuery("VjSource.findALLBySourceResourceSubjectFieldStatus");
                query1 = em.createNamedQuery("VjSource.findALLBySourceResourceSubjectFieldStatus");
                query.setParameter("idResource", Long.parseLong(resID));
                query.setParameter("subjectField", domID);
                query1.setParameter("idResource", Long.parseLong(resID));
                query1.setParameter("subjectField", domID);
            }
        }
        query.setParameter("status", 'e');
        query.setParameter("idOwner", ownerID);
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query1.setParameter("status", 'p');
        query1.setParameter("idOwner", ownerID);
        query1.setParameter("source", term);
        query1.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();
        resultQ.addAll(query1.getResultList());
        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#WSnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result.getIdConcept(), term, solang, resID, domID, ownerID)).append("</td>");
                res.append("</tr>");
            }
        }
        return res.toString();
    }
    
    public static String getCurrentForThis(String term, String solang, String resID, String domID, long ownerID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if (resID.contains("-1")) {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSource.findAllByStatusSource");
            } else {
                query = em.createNamedQuery("VjSource.findALLBysourceSubjectFieldStatus");
                query.setParameter("subjectField", domID);
            }
        } else {
            if (domID.contains("-1")) {
                query = em.createNamedQuery("VjSource.findALLBysourceIdResourceStatus");
                query.setParameter("idResource", Long.parseLong(resID));
            } else {
                query = em.createNamedQuery("VjSource.findALLBySourceResourceSubjectFieldStatus");
                query.setParameter("idResource", Long.parseLong(resID));
                query.setParameter("subjectField", domID);
            }
        }
        query.setParameter("status", 'r');
        query.setParameter("idOwner", ownerID);
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();
        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result.getIdConcept(), term, solang, resID, domID, ownerID)).append("</td>");
                res.append("</tr>");
            }
        }
        return res.toString();
    }

    public static String getCurrentForThis(String solang, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSource.findCurrentByIdOwner");
        query.setParameter("idOwner", ownerID);
        query.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#WSnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result.getIdConcept(), solang, ownerID)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static String getApproveForThis(String solang, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSource.findSubmittedByIdOwner");
        query.setParameter("idOwner", ownerID);
        query.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result.getIdConcept(), solang, ownerID)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static Vector<String> getListForThis(String term, String solang, String talang) {
        System.out.println("param:" + term);
        init();
        Vector<String> res = new Vector<>();
        Query query = em.createNamedQuery("VjSourcetarget.findPublicBySource");
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<VjSourcetarget> resultQ = query.getResultList();
        for (VjSourcetarget result : resultQ) {
            res.add(result.getSource());
        }
        return res;
    }

    public static List<VjUsersResources> getResourcesByOwner(long ownerID) {
        init();
        if (ownerID > 0) {
            Query query = em.createNamedQuery("VjUsersResources.findByIdOwner");
            query.setParameter("idOwner", ownerID);
            List<VjUsersResources> result = query.getResultList();
            return result;
        }
        return null;
    }

    public static String getReslang() {
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjReslang.findAll");
        List<VjReslang> resultQ = query.getResultList();
        res.append("<table>");
        res.append("<caption>" + "Inventory" + "</caption>");
        res.append("<tr>");
        res.append("<th>Resource</th>");
        res.append("<th>Language</th>");
        res.append("<th>Number</th>");
        res.append("</tr>");
        for (VjReslang result : resultQ) {
            res.append("<tr>");
            res.append("<td>").append(result.getResourceName()).append("</td>");
            res.append("<td>").append(result.getIdLanguage()).append("</td>");
            res.append("<td>").append(Long.toString(result.getNbTerms())).append("</td>");
            res.append("</tr>");
        }
        res.append("</table>");
        return res.toString();
    }

    public static String getTargetForThis(long conceptID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjConceptdetail.findByIdConcept");
        query.setParameter("idConcept", conceptID);
        List<VjConceptdetail> resultQ = query.getResultList();

        res.append("<table>");
        res.append("<caption>" + "Details" + "</caption>");
        res.append("<tr>");
        res.append("<th>Language</th>");
        res.append("<th>Term</th>");
        res.append("<th>Source</th>");
        res.append("<th>Definition</th>");
        res.append("<th>Note</th>");
        res.append("</tr>");
        for (VjConceptdetail result : resultQ) {
            res.append("<tr>");
            res.append("<td>").append("&nbsp").append(Queries.getLanguageByID(result.getIdLanguage()).getLanguageDefaultName()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermForm()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermSource()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermDefinition()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermNote()).append("</td>");
            res.append("</tr>");
        }
        res.append("</table>");
        return res.toString();
    }
}
