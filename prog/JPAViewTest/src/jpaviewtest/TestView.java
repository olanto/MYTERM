/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaviewtest.entities.VjCodifications;
import jpaviewtest.entities.VjConceptdetail;
import jpaviewtest.entities.VjGetformsbyconcept;
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

//        System.out.println(getSourceForThis("tuna%", "EN", "FR", "-1", " "));
//        System.out.println(getPublicSearchBySourceTarget("tunas", "EN", "FR", "-1", " "));
//        getConceptAndAssociatedTerms(3534);
//        getApproveElementsByLang("EN", 1001);
        getTermTypes("FR");
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
        result.append("<div class=\"ipanel\">");
        result.append("<table>");
        for (VjConceptdetail res : resultQ) {
            Terms t = Queries.getTermByID(res.getIdTerm());
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
//            if (t.getStatus() != ' ') {
//                result.append("&nbsp").append("<span class = \"extrainfo\">Status: </span>").append(t.getStatus()).append("<br/>");
//            }
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
        }
        result.append("</table>");
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

    public static ConceptEntry getConceptAndAssociatedEditonTerms(long conceptID, long ownerID, ArrayList<String> lsList) {
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
                    Query query = em.createNamedQuery("VjConceptdetail.findByIdConceptAndLangSetWorkspace");
                    query.setParameter("idConcept", conceptID);
                    query.setParameter("idLangset", ls.getIdLangset());
                    query.setParameter("idLanguage", lsList);
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

    public static ConceptEntry getConceptAndAssociatedRevisionTerms(long conceptID, long ownerID, ArrayList<String> lsList) {
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
                    Query query = em.createNamedQuery("VjConceptdetail.findByIdConceptAndLangSetRevision");
                    query.setParameter("idConcept", conceptID);
                    query.setParameter("idLangset", ls.getIdLangset());
                    query.setParameter("idLanguage", lsList);
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

    public static String getPublicSearchBySourceTarget(String term, String solang, String talang, ArrayList<Long> resID, String domID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if ((domID.equals(" ") || domID.length() < 2)) {
            query = em.createNamedQuery("VjSourcetarget.findConceptPublicBySourceTargetResource");
            query.setParameter("selectedValues", resID);
        } else {
            query = em.createNamedQuery("VjSourcetarget.findConceptPublicBySourceTargetResourceSubjectField");
            query.setParameter("selectedValues", resID);
            query.setParameter("subjectField", domID);
        }
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<Long> resultQ = query.getResultList();
        if (!resultQ.isEmpty()) {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#TS").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForLang(result, talang)).append("</td>");
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

    public static String getSourcesForThis(long conceptID, String term, String solang, String resID, String domID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if ((domID.equals(" ") || domID.length() < 2)) {
            query = em.createNamedQuery("VjSourcetarget.findPublicBySourceResourceConcept");
            query.setParameter("idResource", Long.parseLong(resID));
        } else {
            query = em.createNamedQuery("VjSourcetarget.findPublicBySourceResourceSubjectFieldConcept");
            query.setParameter("idResource", Long.parseLong(resID));
            query.setParameter("subjectField", domID);
        }
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("idConcept", conceptID);
        List<VjSourcetarget> resultQ = query.getResultList();

        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            for (VjSourcetarget result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getTarget()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        return res.toString();
    }

    public static String getTargetsForThis(long conceptID, String solang) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjGetformsbyconcept.findformsExceptsolang");
        query.setParameter("idLanguage", solang);
        query.setParameter("idConcept", conceptID);
        List<VjGetformsbyconcept> resultQ = query.getResultList();

        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            for (VjGetformsbyconcept result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getSource()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        return res.toString();
    }

    public static String getTargetsForLang(long conceptID, String talang) {
        init();
        StringBuilder res = new StringBuilder("");

        Query query = em.createNamedQuery("VjGetformsbyconcept.findByLC");
        query.setParameter("idConcept", conceptID);
        query.setParameter("idLanguage", talang);
        List<VjGetformsbyconcept> resultQ = query.getResultList();
        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            for (VjGetformsbyconcept result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getSource()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        return res.toString();
    }

    public static String getSourceForThis(String term, String solang, String resID, String domID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if ((domID.equals(" ") || domID.length() < 2)) {
            query = em.createNamedQuery("VjSource.findPublicBySourceResource");
            query.setParameter("idResource", Long.parseLong(resID));
        } else {
            query = em.createNamedQuery("VjSource.findPublicBySourceResourceSubjectField");
            query.setParameter("idResource", Long.parseLong(resID));
            query.setParameter("subjectField", domID);
        }
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();
        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#WSnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getSourcesForThis(result.getIdConcept(), term, solang, resID, domID)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static String getSubmittedForThis(String term, String solang, String resID, String domID, long ownerID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if ((domID.equals(" ") || domID.length() < 2)) {
            query = em.createNamedQuery("VjSource.findBySourceResourceStatus");
            query.setParameter("idResource", Long.parseLong(resID));
        } else {
            query = em.createNamedQuery("VjSource.findBySourceResourceStatusSubjectField");
            query.setParameter("idResource", Long.parseLong(resID));
            query.setParameter("subjectField", domID);
        }
        query.setParameter("status", 'r');
        query.setParameter("lastmodifiedBy", ownerID);
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        List<VjSource> resultQ = query.getResultList();
        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (VjSource result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append(result.getSource()).append("</a></td>").append("</td>");
                res.append("<td>").append(getSourcesForThis(result.getIdConcept(), term, solang, resID, domID)).append("</td>");
                res.append("</tr>");
            }
        }
        return res.toString();
    }

    public static String getWorkspaceElementsByLang(String solang, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSource.findByStatusAndOwner");
        query.setParameter("status", 'e');
        query.setParameter("lastmodifiedBy", ownerID);
        List<Long> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#WSnew").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result, solang)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static String getSourceForLang(long conceptID, String solang) {
        init();
        Query query = em.createNamedQuery("VjGetformsbyconcept.findByLC");
        query.setParameter("idConcept", conceptID);
        query.setParameter("idLanguage", solang);
        List<VjGetformsbyconcept> result = query.getResultList();
        if (result.size() > 1) {
//            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + conceptID);
            return result.get(0).getSource();
        }
        if (result.isEmpty()) {
//            System.out.println("NO RETURNED VALUES for :" + conceptID);
            return "?";
        }
        return result.get(0).getSource();
    }

    public static String getApproveElementsByLang(String solang, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSource.findByStatusSolang");
        query.setParameter("status", 'r');
        query.setParameter("solang", solang);
        List<Long> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result, solang)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static String getApproveElements(String solang, ArrayList<String> ls, ArrayList<Long> resID, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        query = em.createNamedQuery("VjSource.findByApproveElementsBySolangANDResource");
        query.setParameter("idResource", resID);
        query.setParameter("solang", ls);
        List<Long> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThis(result, solang)).append("</td>");
                res.append("</tr>");
            }
        }
//        System.out.println(res.toString());
        return res.toString();
    }

    public static Vector<String> getListForThis(String term, String solang, String talang) {
//        System.out.println("param:" + term);
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

    public static List<VjUsersResources> getResourcesByOwner(String ownerMailing, String role) {
        init();
        if (!ownerMailing.isEmpty()) {
            Query query = em.createNamedQuery("VjUsersResources.findByOwnerMailingOwnerRoles");
            query.setParameter("ownerRoles", role);
            query.setParameter("ownerMailing", ownerMailing);
            List<VjUsersResources> result = query.getResultList();
//            System.out.println(result.get(0).getResourceName());
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

    public static List<VjCodifications> getCodificationByTypeAndLang(String codeType, String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findSysFieldsByLanguage");
            query.setParameter("idLanguage", langID);
            query.setParameter("codeType", codeType);
            List<VjCodifications> result = query.getResultList();
//            for (VjCodifications s : result) {
//                System.out.println("Key: " + s.getUuid() + " content: " + s.getCodeValue() + ", " + s.getCodeExtra() + "\n");
//            }
            return result;
        }
        return null;
    }

    public static List<String> getTermTypes(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermTypesByLanguage");
            query.setParameter("idLanguage", langID);
            List<String> result = query.getResultList();
//            for (String s : result) {
//                System.out.println(s);
//            }
            return result;
        }
        return null;
    }

    public static List<String> getTermPOS(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermPOSByLanguage");
            query.setParameter("idLanguage", langID);
            List<String> result = query.getResultList();
//            for (String s : result) {
//                System.out.println(s);
//            }
            return result;
        }
        return null;
    }

    public static List<String> getTermGender(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermGenderByLanguage");
            query.setParameter("idLanguage", langID);
            List<String> result = query.getResultList();
//            for (String s : result) {
//                System.out.println(s);
//            }
            return result;
        }
        return null;
    }
}
