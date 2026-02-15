/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.entry.LangEntry;
import static org.olanto.myterm.util.ReplaceMediaLink.replaceMediaLink;

/**
 *
 * @author simple
 */
public class JPAViewFunctions {

    static EntityManagerFactory emf;
    static EntityManager em;
    static final SimpleDateFormat DF_EN = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
    static final SimpleDateFormat DF_FR = new SimpleDateFormat("E dd.MM.yyyy");

    public static void main(String[] args) {
//        System.out.println(getSourceForThis("tuna%", "EN", "FR", "-1", " "));
//        System.out.println(getPublicSearchBySourceTarget("tunas", "EN", "FR", "-1", " "));
//        getConceptAndAssociatedTerms(3534);
//        getApproveElementsByLang("EN", 1001);
        getTermTypes("FR");
//        getCodificationByTypeAndLang("msg", "EN");
    }

    public static void init() {
        if (emf == null) {
            System.out.println("init BD connection");
            emf = Persistence.createEntityManagerFactory("JPAViewTestPU");
            em = emf.createEntityManager();
        }
    }

    public static String getTermsInfo(long conceptID, String lang, HashMap<String, String> sysMsgsrv, HashMap<String, SysFieldDTO> sysFieldsrv, String interfaceLang) {
        init();
        Query query = em.createNamedQuery("VjConceptdetail.findByIdConceptAndLanguage");
        query.setParameter("idConcept", conceptID);
        query.setParameter("idLanguage", lang);
        List<VjConceptdetail> resultQ = query.getResultList();
        StringBuilder result = new StringBuilder("");
        result.append("<div class=\"ipanel\">");
        result.append("<table>");
        result.append("<tr></tr>");
        for (VjConceptdetail res : resultQ) {
            Terms t = Queries.getTermByID(res.getIdTerm());
            result.append("<tr>");
            String first = getFirstTermInfo(t, sysMsgsrv, sysFieldsrv, interfaceLang);
            String second = getSecondTermInfo(t, sysMsgsrv, sysFieldsrv, interfaceLang);
            if (!first.isEmpty()) {
                result.append("<td>").append(first).append("</td>");
            }
            if (!second.isEmpty()) {
                result.append("<td>").append(second).append("</td>");
            }
            result.append("</tr>");
        }
        result.append("</table>");
        result.append("</div>");
        resultQ.clear();
        resultQ = null;
        return result.toString();
    }

    private static String getFirstTermInfo(Terms t, HashMap<String, String> sysMsgsrv, HashMap<String, SysFieldDTO> sysFieldsrv, String interfaceLang) {
        StringBuilder result = new StringBuilder("");
        if ((t.getTermForm() != null) && (!t.getTermForm().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_FORM).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"tform\">").append(sysMsgsrv.get(GuiConstant.LBL_T_FORM)).append(": </span>").append(t.getTermForm()).append("<br/>");
        }
        if ((t.getTermSource() != null) && (!t.getTermSource().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_SOURCE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_SOURCE)).append(": </span>").append(replaceMediaLink(t.getTermSource())).append("<br/>");
        }
        if ((t.getTermDefinition() != null) && (!t.getTermDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_DEFINITION).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"def\">").append(sysMsgsrv.get(GuiConstant.LBL_T_DEFINITION)).append(": </span>").append(replaceMediaLink(t.getTermDefinition())).append("<br/>");
        }

        if ((t.getTermSourceDefinition() != null) && (!t.getTermSourceDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_SOURCE_DEFINITION).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"defsrc\">").append(sysMsgsrv.get(GuiConstant.LBL_T_SOURCE_DEFINITION)).append(": </span>").append(replaceMediaLink(t.getTermSourceDefinition())).append("<br/>");
        }      
        if ((t.getTermNote() != null) && (!t.getTermNote().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_NOTE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_NOTE)).append(": </span>").append(replaceMediaLink(t.getTermNote())).append("<br/>");
        }
        if ((t.getTermPartofspeech() != null) && (!t.getTermPartofspeech().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_POS).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_POS)).append(": </span>").append(
            DecodeCodeValue(interfaceLang,"term_partofspeech", t.getTermPartofspeech())).append("<br/>");
        }
        if ((t.getTermType() != null) && (!t.getTermType().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_TYPE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_TYPE)).append(": </span>").append(
                    DecodeCodeValue(interfaceLang,"term_type", t.getTermType())).append("<br/>");
        }
        if ((t.getTermUsage() != null) && (!t.getTermUsage().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_USAGE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_USAGE)).append(": </span>").append(replaceMediaLink(t.getTermUsage())).append("<br/>");
        }
        if ((t.getTermContext() != null) && (!t.getTermContext().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_CONTEXT).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_CONTEXT)).append(": </span>").append(replaceMediaLink(t.getTermContext())).append("<br/>");
        }
        if ((t.getTermSourceContext() != null) && (!t.getTermSourceContext().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_SOURCE_CONTEXT).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_SOURCE_CONTEXT)).append(": </span>").append(replaceMediaLink(t.getTermSourceContext())).append("<br/>");
        }
       if ((t.getSup0() != null) && (!t.getSup0().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_TECH_NOTE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_TECH_NOTE)).append(": </span>").append(replaceMediaLink(t.getSup0())).append("<br/>");
        }
       if ((t.getSup3() != null) && (!t.getSup3().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_TECH_NOTE_SRC).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_TECH_NOTE_SRC)).append(": </span>").append(replaceMediaLink(t.getSup3())).append("<br/>");
        }
        if ((t.getSup1() != null) && (!t.getSup1().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_LING_NOTE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_LING_NOTE)).append(": </span>").append(replaceMediaLink(t.getSup1())).append("<br/>");
        }
        if ((t.getSup2() != null) && (!t.getSup2().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_REFERENCE_NOTE).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_T_REFERENCE_NOTE)).append(": </span>").append(replaceMediaLink(t.getSup2())).append("<br/>");
        }
        return result.toString();
    }

    private static String getSecondTermInfo(Terms t, HashMap<String, String> sysMsgsrv, HashMap<String, SysFieldDTO> sysFieldsrv, String interfaceLang) {
        StringBuilder result = new StringBuilder("");
        if ((t.getCreateBy() != null) && (sysFieldsrv.get(GuiConstant.T_CREATED_BY).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_CREATED_BY)).append(": </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(t.getCreateBy().toString()))).append("<br/>");
        }
        if (GuiConstant.INTERFACE_LANG.equalsIgnoreCase("fr")) {
            if ((t.getCreation() != null) && (sysFieldsrv.get(GuiConstant.T_CREATION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_CREATION)).append(": </span>").append(DF_FR.format(t.getCreation())).append("<br/>");
            }
        } else {
            if ((t.getCreation() != null) && (sysFieldsrv.get(GuiConstant.T_CREATION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_CREATION)).append(": </span>").append(DF_EN.format(t.getCreation())).append("<br/>");
            }
        }

        if ((t.getLastmodifiedBy() != null) && (sysFieldsrv.get(GuiConstant.T_LAST_MODIF_BY).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_LAST_MODIF_BY)).append(": </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(t.getLastmodifiedBy().toString()))).append("<br/>");
        }
        if (GuiConstant.INTERFACE_LANG.equalsIgnoreCase("fr")) {
            if ((t.getLastmodified() != null) && (sysFieldsrv.get(GuiConstant.T_MODIFICATION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_MODIFICATION)).append(": </span>").append(DF_FR.format(t.getLastmodified())).append("<br/>");
            }
        } else {
            if ((t.getLastmodified() != null) && (sysFieldsrv.get(GuiConstant.T_MODIFICATION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_MODIFICATION)).append(": </span>").append(DF_EN.format(t.getLastmodified())).append("<br/>");
            }
        }
        if ((t.getCrossref() != null) && (!t.getCrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_CROSS_REF).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_CROSS_REF)).append(": </span>").append(t.getCrossref()).append("<br/>");
        }
        if ((t.getExtcrossref() != null) && (!t.getExtcrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_EXTRA_CROSS_REF).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_EXTRA_CROSS_REF)).append(": </span>").append(t.getExtcrossref()).append("<br/>");
        }
        if ((t.getTermGender() != null) && (!t.getTermGender().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_GENDER).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_GENDER)).append(": </span>").append(
                    DecodeCodeValue(interfaceLang,"term_gender",t.getTermGender())).append("<br/>");
        }
        if ((t.getTermGeoUsage() != null) && (!t.getTermGeoUsage().isEmpty()) && (sysFieldsrv.get(GuiConstant.T_GEO_USG).getVisibilityPublic())) {
            result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_T_GEO_USG)).append(": </span>").append(t.getTermGeoUsage()).append("<br/>");
        }
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
                    resultQ.clear();
                    resultQ = null;
                    conceptEntry.listlang.add(langE);
//                    System.out.println("copying lang set from query: " + langE.lan.getIdLanguage());
                }
            }
            langsets.clear();
            langsets = null;
            cpt = null;
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
                    resultQ.clear();
                    resultQ = null;
                    conceptEntry.listlang.add(langE);
//                    System.out.println("copying lang set from query: " + langE.lan.getIdLanguage());
                }
            }
            langsets.clear();
            langsets = null;
            cpt = null;
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
                    resultQ.clear();
                    resultQ = null;
                    conceptEntry.listlang.add(langE);
//                    System.out.println("copying lang set from query: " + langE.lan.getIdLanguage());
                }
            }
            langsets.clear();
            langsets = null;
            cpt = null;
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
        // add max result as a parameter of this method
        query.setMaxResults(1000);
        List<Long> resultQ = query.getResultList();
        if (!resultQ.isEmpty()) {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#TS").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getSourcesForLang(result, talang)).append("</td>");
                res.append("</tr>");
            }
            resultQ.clear();
            resultQ = null;
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
        return Collections.EMPTY_LIST;
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
            res.append("<tr></tr>");
            for (VjSourcetarget result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getTarget()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        resultQ.clear();
        resultQ = null;
        return res.toString();
    }

    public static String getTargetsForThisLang(long conceptID, String solang) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjGetformsbyconcept.findformsExceptsolang");
        query.setParameter("idLanguage", solang);
        query.setParameter("idConcept", conceptID);
        List<VjGetformsbyconcept> resultQ = query.getResultList();

        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            res.append("<tr></tr>");
            for (VjGetformsbyconcept result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getSource()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        resultQ.clear();
        resultQ = null;
        return res.toString();
    }

    public static boolean getResourceActivity(long resID) {
        init();
        if (Queries.getResourceActivity(resID)) {
            return true;
        }
        Query query = em.createNamedQuery("VjSource.findByUsageIdResource");
        query.setParameter("idResource", resID);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean getLanguageActivity(String langID) {
        init();
        Query query = em.createNamedQuery("VjUsersLanguages.findByIdLanguage");
        query.setParameter("idLanguage", langID);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    public static String getSourcesForLang(long conceptID, String talang) {
        init();
        StringBuilder res = new StringBuilder("");

        Query query = em.createNamedQuery("VjGetformsbyconcept.findByLC");
        query.setParameter("idConcept", conceptID);
        query.setParameter("idLanguage", talang);
        List<VjGetformsbyconcept> resultQ = query.getResultList();
        if (!resultQ.isEmpty()) {
            res.append("<table class =\"nost\">");
            res.append("<tr></tr>");
            for (VjGetformsbyconcept result : resultQ) {
                res.append("<tr>");
                res.append("<td>").append(result.getSource()).append("</td>");
                res.append("</tr>");
            }
            res.append("</table>");
        }
        resultQ.clear();
        resultQ = null;
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
        resultQ.clear();
        resultQ = null;
//        System.out.println(res.toString());
        return res.toString();
    }

    public static List<VjUsersResources> getUsersResources(long ownerID, long resID, String ownerRole) {
        init();
        Query query;
        if (ownerID <= 0) {
            if (resID <= 0) {
                if ((ownerRole.equals(" ") || ownerRole.length() < 2)) {
                    query = em.createNamedQuery("VjUsersResources.findAll");
                } else {
                    query = em.createNamedQuery("VjUsersResources.findByOwnerRoles");
                    query.setParameter("ownerRoles", ownerRole);
                }
            } else {
                if ((ownerRole.equals(" ") || ownerRole.length() < 2)) {
                    query = em.createNamedQuery("VjUsersResources.findByIdResource");
                } else {
                    query = em.createNamedQuery("VjUsersResources.findByIdResourceOwnerRoles");
                    query.setParameter("ownerRoles", ownerRole);
                }
                query.setParameter("idResource", resID);
            }
        } else {
            if (resID <= 0) {
                if ((ownerRole.equals(" ") || ownerRole.length() < 2)) {
                    query = em.createNamedQuery("VjUsersResources.findByIdOwner");
                } else {
                    query = em.createNamedQuery("VjUsersResources.findByIdOwnerOwnerRoles");
                    query.setParameter("ownerRoles", ownerRole);
                }
            } else {
                if ((ownerRole.equals(" ") || ownerRole.length() < 2)) {
                    query = em.createNamedQuery("VjUsersResources.findByIdOwnerIdResource");
                } else {
                    query = em.createNamedQuery("VjUsersResources.findByIdOwnerIdResourceOwnerRoles");
                    query.setParameter("ownerRoles", ownerRole);
                }
                query.setParameter("idResource", resID);
            }
            query.setParameter("idOwner", ownerID);
        }
        return query.getResultList();
    }

    public static List<VjUsersResources> getUsersResources(long id) {
        init();
        Query query = em.createNamedQuery("VjUsersResources.findByIdLink");
        query.setParameter("idLink", id);

        return query.getResultList();
    }

    public static List<VjUsersLanguages> getUsersLanguages(long ownerID, String langID) {
        init();
        Query query;
        if (ownerID <= 0) {
            if ((langID.equals(" ") || langID.length() < 2)) {
                query = em.createNamedQuery("VjUsersLanguages.findAll");
            } else {
                query = em.createNamedQuery("VjUsersLanguages.findByIdLanguage");
                query.setParameter("idLanguage", langID);
            }
        } else {
            if ((langID.equals(" ") || langID.length() < 2)) {
                query = em.createNamedQuery("VjUsersLanguages.findByIdOwner");
            } else {
                query = em.createNamedQuery("VjUsersLanguages.findByIdOwnerIdLanguage");
                query.setParameter("idLanguage", langID);
            }
            query.setParameter("idOwner", ownerID);
        }
        return query.getResultList();
    }

    public static List<VjUsersLanguages> getUsersLanguages(long id) {
        init();
        Query query = em.createNamedQuery("VjUsersLanguages.findByIdLink");
        query.setParameter("idLink", id);
        return query.getResultList();
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
                res.append("<td>").append(getTargetsForThisLang(result, solang)).append("</td>");
                res.append("</tr>");
            }
        }
        resultQ.clear();
        resultQ = null;
//        System.out.println(res.toString());
        return res.toString();
    }

    public static String getSourceForLang(long conceptID, String solang) {
        init();
        String source = "";
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
        source = result.get(0).getSource();
        result.clear();
        result = null;
        return source;
    }

    public static String getApproveElements(String term, String solang, ArrayList<String> ls, ArrayList<Long> resID, String domID, long ownerID) {
        init();
        StringBuilder res = new StringBuilder("");
        Query query;
        if ((term.isEmpty()) || (term.equals(" "))) {
            if ((domID.equals(" ") || domID.length() < 2)) {
                query = em.createNamedQuery("VjSource.findBySolangResourceStatus");
            } else {
                query = em.createNamedQuery("VjSource.findBySolangResourceStatusSubjectField");
                query.setParameter("subjectField", domID);
            }
        } else {
            if ((domID.equals(" ") || domID.length() < 2)) {
                query = em.createNamedQuery("VjSource.findBySourceSolangResourceStatus");
            } else {
                query = em.createNamedQuery("VjSource.findBySourceSolangResourceStatusSubjectField");
                query.setParameter("subjectField", domID);
            }
            query.setParameter("source", term);
        }

        query.setParameter("status", 'r');
        query.setParameter("idResource", resID);
        query.setParameter("solang", ls);

        List<Long> resultQ = query.getResultList();

        if (resultQ.isEmpty()) {
            return null;
        } else {
            for (long result : resultQ) {
                res.append("<tr>");
                res.append("<td><a href=\"#Appnew").append(result).append("\" onClick=\"return gwtnav(this);\">").append(getSourceForLang(result, solang)).append("</a></td>").append("</td>");
                res.append("<td>").append(getTargetsForThisLang(result, solang)).append("</td>");
                res.append("</tr>");
            }
        }
        resultQ.clear();
        resultQ = null;
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
        return Collections.EMPTY_LIST;
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
        resultQ.clear();
        resultQ = null;
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
//        resultQ.clear();
       // resultQ = null;
        return res.toString();
    }

    public static List<VjCodifications> getCodificationByTypeAndLang(String codeType, String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findFieldsByLanguage");
            query.setParameter("idLanguage", langID);
            query.setParameter("codeType", codeType);
            List<VjCodifications> result = query.getResultList();
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<VjCodifications> getTermTypes(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermTypesByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
            for (VjCodifications s : result) {
                System.out.println(s.getCodeValue());
            }
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<VjCodifications> getTermPOS(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermPOSByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
//            for (String s : result) {
//                System.out.println(s);
//            }
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static String DecodeCodeValue(String interfaceLang, String codeType, String codeValue) {
        init();
        //System.out.println("INTERFACE_LANG: " + interfaceLang + ", codeType:" + codeType + ", codeValue:" + codeValue);
        if (!interfaceLang.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findFieldsByValues");
            query.setParameter("idLanguage", interfaceLang);
            query.setParameter("codeType", codeType);
            query.setParameter("codeValue", codeValue);
            List<VjCodifications> result = query.getResultList();
            //System.out.println("result size: " + result.size());
            if (result.size() != 1) {

                return codeValue;
            }
            //System.out.println(result);
            return result.get(0).getCodeValueLang();
        }
        return codeValue;
    }

    public static List<VjCodifications> getTermGender(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findTermGenderByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
//            for (String s : result) {
//                System.out.println(s);
//            }
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<VjCodifications> getOwnerRoles(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findOwnerRolesByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<VjCodifications> getOwnerStatus(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findOwnerStatusByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<VjCodifications> getResourcePrivacy(String langID) {
        init();
        if (!langID.isEmpty()) {
            Query query = em.createNamedQuery("VjCodifications.findResourcePrivacyByLanguage");
            query.setParameter("idLanguage", langID);
            List<VjCodifications> result = query.getResultList();
            return result;
        }
        return Collections.EMPTY_LIST;
    }
}
