/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpaviewtest.entities.VjCodifications;
import jpaviewtest.entities.VjUsersLanguages;
import jpaviewtest.entities.VjUsersResources;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.ResourceDTO;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.shared.ConceptDTO;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.LangSetDTO;
import olanto.myTerm.shared.OwnerDTO;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;
import olanto.myTerm.shared.UserLanguageDTO;
import olanto.myTerm.shared.UserResourceDTO;
import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.ManageLanguages;
import org.olanto.myterm.coredb.ManageOwner;
import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.ManageTerm;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Domains;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.entityclasses.UsersLanguages;
import org.olanto.myterm.coredb.entityclasses.UsersResources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.entry.LangEntry;
import org.olanto.myterm.print.xml.PrintXMLFromDB;
import static org.olanto.myterm.util.ReplaceMediaLink.*;

/**
 *
 * @author simple
 */
public class myTermServiceImpl extends RemoteServiceServlet implements myTermService {

    private HashMap<String, String> sysMsgsrv;
    private HashMap<String, SysFieldDTO> sysFieldsrv;
    static final SimpleDateFormat DF_EN = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
    static final SimpleDateFormat DF_FR = new SimpleDateFormat("E dd.MM.yyyy");

    @Override
    public String getSearchResult(String s, String ls, String lt, ArrayList<Long> resID, String domID) {
        TermDB.restart();
        String response = JPAViewFunctions.getPublicSearchBySourceTarget(s, ls, lt, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<td>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</td>");
            result.append("<td>").append(Queries.getLanguageByID(lt).getLanguageDefaultName()).append("</td>");
            result.append("</tr>");
            result.append(response);
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getInventory() {
        StringBuilder result = new StringBuilder("");
        result.append("<div class =\"panel\">");
        result.append(JPAViewFunctions.getReslang());
        result.append("</div>");
        return result.toString();
    }

    @Override
    public Collection<String> getResults(String s, String ls, String lt, long ownerID) {
        return JPAViewFunctions.getListForThis(s, ls, lt);
    }

    @Override
    public Collection<LanguageDTO> getLanguages() {
        TermDB.restart();
        List<Languages> l = Queries.getLanguages();
        List<LanguageDTO> languages = new ArrayList<>();
        for (Languages lang : l) {
            LanguageDTO li = new LanguageDTO();
            li.setIdLanguage(lang.getIdLanguage());
            li.setLanguageDefaultName(lang.getLanguageDefaultName());
            languages.add(li);
        }
        return languages;
    }

    @Override
    public Collection<LanguageDTO> getLanguagesByOwner(long ownerID) {
        // try and catch, problem with the ownerID
        if (ownerID > 0) {
            List<VjUsersLanguages> l = JPAViewFunctions.getLanguagesByOwner(ownerID);
            if (!l.isEmpty()) {
                List<LanguageDTO> languages = new ArrayList<>();
                for (VjUsersLanguages res : l) {
                    LanguageDTO ln = new LanguageDTO(res.getIdLanguage(), res.getLanguageDefaultName());
                    languages.add(ln);
                }
                return languages;
            }
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ResourceDTO> getResourcesByOwner(String ownerMailing, String role) {
        // try and catch, problem with the ownerID
        if (!ownerMailing.isEmpty()) {
            List<VjUsersResources> l = JPAViewFunctions.getResourcesByOwner(ownerMailing, role);
            if (!l.isEmpty()) {
                List<ResourceDTO> resources = new ArrayList<>();
                for (VjUsersResources res : l) {
                    ResourceDTO r = new ResourceDTO(res.getIdResource(), ownerMailing, res.getResourceName(), res.getResourcePrivacy());
                    resources.add(r);
                }
                return resources;
            }
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<DomainDTO> getDomains() {
        TermDB.restart();
        List<Domains> l = Queries.getDomains();
        List<DomainDTO> domains = new ArrayList<>();
        for (Domains res : l) {
            DomainDTO d = new DomainDTO();
            d.setIdDomain(res.getIdDomain());
            d.setDomainDefaultName(res.getDomainDefaultName());
            domains.add(d);
        }
        return domains;
    }

    @Override
    public String getdetailsForConcept(long conceptID, long ownerID) {
        StringBuilder result = new StringBuilder("");
        Concepts c = Queries.getConceptByID(conceptID);
        if (c != null) {
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            if ((sysFieldsrv == null) || (sysFieldsrv.isEmpty())) {
                sysFieldsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("sys_field", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    if (!field.getCodeExtra().isEmpty()) {
                        String[] values = field.getCodeExtra().split(";");
                        if ((values.length > 1) && (!field.getCodeValue().isEmpty())) {
                            SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim(), values[2].trim());
                            sysFieldsrv.put(field.getCodeValue(), s);
                        }
                    }
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.MSG_DEF_DETAILS)).append(" : ").append(c.getIdConcept()).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.MSG_RESOURCE_DETAILS)).append(" : ").append(Queries.getIdResources(c.getIdResource()).getResourceName()).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.MSG_CREATION_DETAILS)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.MSG_EXTRA_DETAILS)).append("</th>");
            result.append("</tr>");
            result.append("<tr>");
            result.append("<td>");

            if ((c.getSubjectField() != null) && (!c.getSubjectField().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_SUBJECT_FIELD).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"sfield\">").append(sysMsgsrv.get(GuiConstant.LBL_C_SUBJECT_FIELD)).append(": </span>").append(c.getSubjectField()).append("<br/>");
            }
            if ((c.getConceptDefinition() != null) && (!c.getConceptDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_DEFINITION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"def\">").append(sysMsgsrv.get(GuiConstant.LBL_C_DEFINITION)).append(": </span>").append(replaceMediaLink(c.getConceptDefinition())).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getConceptSourceDefinition() != null) && (!c.getConceptSourceDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_SOURCE_DEFINITION).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"defsrc\">").append(sysMsgsrv.get(GuiConstant.LBL_C_SOURCE_DEFINITION)).append(": </span>").append(replaceMediaLink(c.getConceptSourceDefinition())).append("<br/>");
            }
            if ((c.getConceptNote() != null) && (!c.getConceptNote().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_NOTE).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_C_NOTE)).append(": </span>").append(replaceMediaLink(c.getConceptNote())).append("<br/>");
            }
            if ((c.getCrossref() != null) && (!c.getCrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_CROSS_REF).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CROSS_REF)).append(": </span>").append(Utils.extractCrossRef(c.getCrossref())).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getExtcrossref() != null) && (!c.getExtcrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_EXTRA_CROSS_REF).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_EXTRA_CROSS_REF)).append(": </span>").append(c.getExtcrossref()).append("<br/>");
            }
            if ((c.getCreateBy() != null) && (sysFieldsrv.get(GuiConstant.C_CREATED_BY).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CREATED_BY)).append(": </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getCreateBy().toString()))).append("<br/>");
            }
            if (GuiConstant.INTERFACE_LANG.equalsIgnoreCase("fr")) {
                if ((c.getCreation() != null) && (sysFieldsrv.get(GuiConstant.C_CREATION).getVisibilityPublic())) {
                    result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CREATION)).append(": </span>").append(DF_FR.format(c.getCreation())).append("<br/>");
                }
            } else {
                if ((c.getCreation() != null) && (sysFieldsrv.get(GuiConstant.C_CREATION).getVisibilityPublic())) {
                    result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CREATION)).append(": </span>").append(DF_EN.format(c.getCreation())).append("<br/>");
                }
            }
            result.append("</td>").append("<td>");
            if ((c.getLastmodifiedBy() != null) && (sysFieldsrv.get(GuiConstant.C_LAST_MODIF_BY).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_LAST_MODIF_BY)).append(": </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getLastmodifiedBy().toString()))).append("<br/>");
            }
            if (GuiConstant.INTERFACE_LANG.equalsIgnoreCase("fr")) {
                if ((c.getLastmodified() != null) && (sysFieldsrv.get(GuiConstant.C_MODIFICATION).getVisibilityPublic())) {
                    result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_MODIFICATION)).append(": </span>").append(DF_FR.format(c.getLastmodified())).append("<br/>");
                }
            } else {
                if ((c.getLastmodified() != null) && (sysFieldsrv.get(GuiConstant.C_MODIFICATION).getVisibilityPublic())) {
                    result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_MODIFICATION)).append(": </span>").append(DF_EN.format(c.getLastmodified())).append("<br/>");
                }
            }
            if ((c.getImage() != null) && (!c.getImage().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_IMAGE).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_IMAGE)).append(": </span>").append(c.getImage()).append("<br/>");
            }
            if ((c.getExtra() != null) && (!c.getExtra().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_EXTRA).getVisibilityPublic())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_EXTRA)).append(": </span>").append(replaceMediaLink(c.getExtra())).append("<br/>");
            }
            result.append("</td>").append("</tr>");
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, String interfaceLang) {
        StringBuilder result = new StringBuilder("");
        if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
            sysMsgsrv = new HashMap<>();
            List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
            for (VjCodifications field : codes) {
                sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
            }
        }
        if ((sysFieldsrv == null) || (sysFieldsrv.isEmpty())) {
            sysFieldsrv = new HashMap<>();
            List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("sys_field", GuiConstant.INTERFACE_LANG);
            for (VjCodifications field : codes) {
                if (!field.getCodeExtra().isEmpty()) {
                    String[] values = field.getCodeExtra().split(";");
                    if ((values.length > 1) && (!field.getCodeValue().isEmpty())) {
                        SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim(), values[2].trim());
                        sysFieldsrv.put(field.getCodeValue(), s);
                    }
                }
            }
        }
        result.append("<div class =\"tpanel\">");
        result.append("<table>");
        result.append("<tr>");
        result.append("<th>").append(Queries.getLanguageByID(langS).getLanguageDefaultName()).append("</th>");
        result.append("<th>").append(Queries.getLanguageByID(langT).getLanguageDefaultName()).append("</th>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td>")
                .append(JPAViewFunctions.getTermsInfo(conceptID, langS, sysMsgsrv, sysFieldsrv, interfaceLang))
                .append("</td>");
        result.append("<td>")
                .append(JPAViewFunctions.getTermsInfo(conceptID, langT, sysMsgsrv, sysFieldsrv, interfaceLang))
                .append("</td>");
        result.append("</tr>");
        result.append("</table>");
        result.append("</div>");
        return result.toString();
    }

    @Override
    public String getAddResult(String s, String ls, String resID, String domID, long ownerID) {
        TermDB.restart();
        String response = JPAViewFunctions.getSourceForThis(s, ls, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<td>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</td>");
            result.append("<td>").append("Targets").append("</td>");
            result.append("</tr>");
            result.append(response);
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getWorkspaceElements(String ls, long ownerID) {
        TermDB.restart();
        String response = JPAViewFunctions.getWorkspaceElementsByLang(ls, ownerID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<td>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</td>");
            result.append("<td>").append("Targets").append("</td>");
            result.append("</tr>");
            result.append(response);
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public ConceptEntryDTO getRedactorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList) {
        ConceptEntryDTO conceptEntryDTO = new ConceptEntryDTO();
        ConceptEntry c = JPAViewFunctions.getConceptAndAssociatedEditonTerms(conceptID, ownerID, lsList);
        copyFromConceptEntry(conceptEntryDTO, c);
        return conceptEntryDTO;
    }

    @Override
    public ConceptEntryDTO getRevisorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList) {
        ConceptEntryDTO conceptEntryDTO = new ConceptEntryDTO();
        ConceptEntry c = JPAViewFunctions.getConceptAndAssociatedRevisionTerms(conceptID, ownerID, lsList);
        copyFromConceptEntry(conceptEntryDTO, c);
        return conceptEntryDTO;
    }

    private void copyFromConceptEntry(ConceptEntryDTO concepEntrytDTO, ConceptEntry conceptEntry) {
        copyFormConcept(concepEntrytDTO.concept, conceptEntry.getConcept());
        if (!conceptEntry.listlang.isEmpty()) {
            for (LangEntry ls : conceptEntry.listlang) {
                LangEntryDTO langE = new LangEntryDTO();
                langE.lan = copyFromLangSet(ls.lan);
                if (!ls.listterm.isEmpty()) {
                    for (Terms t : ls.listterm) {
                        langE.listterm.add(copyFromTerm(t));
                    }
                }
                concepEntrytDTO.listlang.add(langE);
            }
        }
    }

    private ConceptEntry createFromConceptEntryDTO(ConceptEntryDTO conceptEntryDTO) {
        if (conceptEntryDTO != null) {
            ConceptEntry conceptEntry = new ConceptEntry(true);
            copyFromConceptDTO(conceptEntry.getConcept(), conceptEntryDTO.concept);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                for (LangEntryDTO ls : conceptEntryDTO.listlang) {
                    LangEntry langE = new LangEntry();
                    langE.lan = copyFromLangSetDTO(ls.lan);
                    if (!ls.listterm.isEmpty()) {
                        for (TermDTO tDTO : ls.listterm) {
                            Terms t = copyFromTermDTO(tDTO);
                            if (t != null) {
                                langE.listterm.add(t);
                            }
                        }
                    }
                    conceptEntry.listlang.add(langE);
                }
            }
            return conceptEntry;
        }
        return null;
    }

    private void copyFormConcept(ConceptDTO cDTO, Concepts c) {
        cDTO.setConceptDefinition(c.getConceptDefinition());
        cDTO.setConceptNote(c.getConceptNote());
        cDTO.setConceptSourceDefinition(c.getConceptSourceDefinition());
        cDTO.setCreateBy(c.getCreateBy());
        cDTO.setCreation(c.getCreation());
        cDTO.setCrossref(c.getCrossref());
        cDTO.setExtcrossref(c.getExtcrossref());
        cDTO.setExtra(c.getExtra());
        cDTO.setIdConcept(c.getIdConcept());
        cDTO.setIdResource(c.getIdResource());
        cDTO.setImage(c.getImage());
        cDTO.setLastmodified(c.getLastmodified());
        cDTO.setLastmodifiedBy(c.getLastmodifiedBy());
        cDTO.setSubjectField(c.getSubjectField());
    }

    private void copyFromConceptDTO(Concepts c, ConceptDTO cDTO) {
        if (cDTO != null) {
            c.setConceptDefinition(cDTO.getConceptDefinition());
            c.setConceptNote(cDTO.getConceptNote());
            c.setConceptSourceDefinition(cDTO.getConceptSourceDefinition());
            c.setCreateBy(cDTO.getCreateBy());
            c.setCreation(cDTO.getCreation());
            c.setCrossref(cDTO.getCrossref());
            c.setExtcrossref(cDTO.getExtcrossref());
            c.setExtra(cDTO.getExtra());
            c.setIdConcept(cDTO.getIdConcept());
            c.setIdResource(cDTO.getIdResource());
            c.setImage(cDTO.getImage());
            c.setLastmodified(cDTO.getLastmodified());
            c.setLastmodifiedBy(cDTO.getLastmodifiedBy());
            c.setSubjectField(cDTO.getSubjectField());
        }
    }

    private LangSetDTO copyFromLangSet(Langsets ls) {
        if (ls != null) {
            LangSetDTO lsDTO = new LangSetDTO();
            lsDTO.setExtra(ls.getExtra());
            lsDTO.setIdConcept(ls.getIdConcept());
            lsDTO.setIdLangset(ls.getIdLangset());
            lsDTO.setIdLanguage(ls.getIdLanguage());
            lsDTO.setLangsetNote(ls.getLangsetNote());
            lsDTO.setSeq(ls.getSeq());
            return lsDTO;
        }
        return null;
    }

    private Langsets copyFromLangSetDTO(LangSetDTO lsDTO) {
        if (lsDTO != null) {
            Langsets ls;
            if (lsDTO.getIdLangset() != null) {
                ls = new Langsets(lsDTO.getIdLangset());
            } else {
                ls = new Langsets();
                ls.setIdLangset(lsDTO.getIdLangset());
            }
            ls.setExtra(lsDTO.getExtra());
            ls.setIdConcept(lsDTO.getIdConcept());
            ls.setIdLanguage(lsDTO.getIdLanguage());
            ls.setLangsetNote(lsDTO.getLangsetNote());
            ls.setSeq(lsDTO.getSeq());
            return ls;
        }
        return null;
    }

    private TermDTO copyFromTerm(Terms t) {
        if (t != null) {
            TermDTO tDTO = new TermDTO();
            tDTO.setCreateBy(t.getCreateBy());
            tDTO.setCreation(t.getCreation());
            tDTO.setCrossref(t.getCrossref());
            tDTO.setExtcrossref(t.getExtcrossref());
            tDTO.setExtra(t.getExtra());
            tDTO.setIdLangset(t.getIdLangset());
            tDTO.setLangName(Queries.getLanguageByID(t.getIdLanguage()).getLanguageDefaultName());
            tDTO.setIdLanguage(t.getIdLanguage());
            tDTO.setIdTerm(t.getIdTerm());
            tDTO.setLastmodified(t.getLastmodified());
            tDTO.setModifiedBy(t.getLastmodifiedBy());
            tDTO.setSeq(t.getSeq());
            tDTO.setStatus(t.getStatus());
            tDTO.setTermAdminStatus(t.getTermAdminStatus());
            tDTO.setTermContext(t.getTermContext());
            tDTO.setTermDefinition(t.getTermDefinition());
            tDTO.setTermForm(t.getTermForm());
            tDTO.setTermGender(t.getTermGender());
            tDTO.setTermGeoUsage(t.getTermGeoUsage());
            tDTO.setTermNote(t.getTermNote());
            tDTO.setTermPartofspeech(t.getTermPartofspeech());
            tDTO.setTermSource(t.getTermSource());
            tDTO.setTermSourceContext(t.getTermSourceContext());
            tDTO.setTermSourceDefinition(t.getTermSourceDefinition());
            tDTO.setTermType(t.getTermType());
            tDTO.setTermUsage(t.getTermUsage());
            if (t.getCreateBy() != null) {
                tDTO.setCreatedBy(Queries.getOwnerFullNamebyID(Long.parseLong(t.getCreateBy().toString())) + " (" + DF_FR.format(t.getCreation()) + ")");
            }
            if (t.getLastmodifiedBy() != null) {
                tDTO.setLastModifiedBy(Queries.getOwnerFullNamebyID(Long.parseLong(t.getLastmodifiedBy().toString())) + " (" + DF_FR.format(t.getLastmodified()) + ")");
            }

            // technical, linguistic and reference
            tDTO.setTechnicalNote(t.getSup0());
            tDTO.setLinguisticNote(t.getSup1());
            tDTO.setReferenceNote(t.getSup2());
            tDTO.setTechnicalNoteSource(t.getSup3());
            return tDTO;
        }
        return null;
    }

    private Terms copyFromTermDTO(TermDTO tDTO) {
        if (tDTO != null) {
            Terms t = new Terms();
            if (t.getIdTerm() != null) {
                t = new Terms(t.getIdTerm());
            } else {
                t = new Terms();
            }
            t.setCreateBy(tDTO.getCreateBy());
            t.setCreation(tDTO.getCreation());
            t.setCrossref(tDTO.getCrossref());
            t.setExtcrossref(tDTO.getExtcrossref());
            t.setExtra(tDTO.getExtra());
            t.setIdLangset(tDTO.getIdLangset());
            t.setIdLanguage(tDTO.getIdLanguage());
            t.setIdTerm(tDTO.getIdTerm());
            t.setLastmodified(tDTO.getLastmodified());
            t.setLastmodifiedBy(tDTO.getModifiedBy());
            t.setSeq(tDTO.getSeq());
            t.setStatus(tDTO.getStatus());
            t.setTermAdminStatus(tDTO.getTermAdminStatus());
            t.setTermContext(tDTO.getTermContext());
            t.setTermDefinition(tDTO.getTermDefinition());
            t.setTermForm(tDTO.getTermForm());
            t.setTermGender(tDTO.getTermGender());
            t.setTermGeoUsage(tDTO.getTermGeoUsage());
            t.setTermNote(tDTO.getTermNote());
            t.setTermPartofspeech(tDTO.getTermPartofspeech());
            t.setTermSource(tDTO.getTermSource());
            t.setTermSourceContext(tDTO.getTermSourceContext());
            t.setTermSourceDefinition(tDTO.getTermSourceDefinition());
            t.setTermType(tDTO.getTermType());
            t.setTermUsage(tDTO.getTermUsage());
            // technical, linguistic and reference
            t.setSup0(tDTO.getTechnicalNote());
            t.setSup1(tDTO.getLinguisticNote());
            t.setSup2(tDTO.getReferenceNote());
            t.setSup3(tDTO.getTechnicalNoteSource());
            return t;
        }
        return null;
    }

    @Override
    public String createConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        if (cEntry.flushFromInterface() != null) {
            return "sucess";
        }
        return null;
    }

    @Override
    public ConceptEntryDTO RedactorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        Concepts c = cEntry.updateFromInterface();
        if (c != null) {
            return getRedactorDetailsForConcept(c.getIdConcept(), ownerID, lsList);
        }
        return null;
    }

    @Override
    public String RedactorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        Concepts c = cEntry.updateFromInterface();
        if (c != null) {
            c = null;
            return "success";
        }
        return null;
    }

    @Override
    public ConceptEntryDTO RevisorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        Concepts c = cEntry.updateFromInterface();
        if (c != null) {
            return getRevisorDetailsForConcept(c.getIdConcept(), ownerID, lsList);
        }
        return null;
    }

    @Override
    public String RevisorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        Concepts c = cEntry.updateFromInterface();
        if (c != null) {
            c = null;
            return "success";
        }
        return null;
    }

    @Override
    public String deleteConceptEntry(long conceptID, long ownerID) {
        if (ManageConcept.remove(conceptID)) {
            return "Sucess";
        }
        return null;
    }

    @Override
    public String deleteTermEntry(long term) {
        ManageTerm.remove(term);
        return "Success";
    }

    @Override
    public String submitConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        if (cEntry.submitFromInterface() != null) {
            return "Sucess";
        }
        return null;
    }

    @Override
    public String getApproveElements(String s, String ls, ArrayList<String> lsList, ArrayList<Long> resID, String domID, long ownerID) {
        String response = JPAViewFunctions.getApproveElements(s, ls, lsList, resID, domID, ownerID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<td>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</td>");
            result.append("<td>").append("Targets").append("</td>");
            result.append("</tr>");
            result.append(response);
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String publishTermEntry(long termID) {
        if (ManageTerm.approve(termID) != null) {
            return "Success";
        }
        return null;
    }

    @Override
    public String disapproveTermEntry(long termID) {
        if (ManageTerm.disapprove(termID) != null) {
            return "Success";
        }
        return null;
    }

    @Override
    public String approveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        if (cEntry.approveFromInterface() != null) {
            return "Sucess";
        }
        return null;
    }

    @Override
    public String disapproveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID) {
        ConceptEntry cEntry = createFromConceptEntryDTO(conceptEntryDTO);
        if (cEntry.disapproveFromInterface() != null) {
            return "Sucess";
        }
        return null;
    }

    @Override
    public Map<String, SysFieldDTO> getSysFieldsByLang(String langID) {
        Map<String, SysFieldDTO> sysFields = new HashMap<>();
        if (!langID.isEmpty()) {
            List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("sys_field", langID);
            for (VjCodifications field : codes) {
                if (!field.getCodeExtra().isEmpty()) {
                    String[] values = field.getCodeExtra().split(";");
                    if ((values.length > 1) && (!field.getCodeValue().isEmpty())) {
                        SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim(), values[2].trim());
                        sysFields.put(field.getCodeValue(), s);
                    }
                }
            }
        }
        return sysFields;
    }

    @Override
    public Map<String, String> getTermTypes(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getTermTypes(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public Map<String, String> getTermPOS(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getTermPOS(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public Map<String, String> getTermGender(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getTermGender(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public Map<String, String> getSysMsgByLang(String langID) {
        Map<String, String> sysMsg = new HashMap<>();
        sysMsgsrv = new HashMap<>();
        if (!langID.isEmpty()) {
            List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", langID);
            for (VjCodifications field : codes) {
                sysMsg.put(field.getCodeValue(), field.getCodeExtraLang());
            }
        }
        sysMsgsrv.putAll(sysMsg);
        return sysMsg;
    }

    @Override
    public Map<String, String> getOwnerRoles(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getOwnerRoles(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public Map<String, String> getOwnerStatus(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getOwnerStatus(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public Collection<ResourceDTO> getResources() {
        List<Resources> l = Queries.getResources();
        if (!l.isEmpty()) {
            List<ResourceDTO> resources = new ArrayList<>();
            for (Resources res : l) {
                ResourceDTO r = new ResourceDTO(res.getIdResource(), res.getIdOwner(), res.getResourceName(), res.getResourcePrivacy(), res.getResourceNote(), res.getExtra());
                resources.add(r);
            }
            return resources;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<OwnerDTO> getOwners() {
        List<Owners> o = Queries.getOwners();
        if (!o.isEmpty()) {
            List<OwnerDTO> owners = new ArrayList<>();
            for (Owners own : o) {
                OwnerDTO owner = new OwnerDTO(own.getIdOwner(), own.getOwnerFirstName(), own.getOwnerLastName(), own.getOwnerHash(), own.getOwnerMailing(), own.getOwnerRoles(), own.getOwnerStatus());
                owners.add(owner);
            }
            return owners;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, String> getResourcePrivacy(String langID) {
        Map<String, String> map = new HashMap<>();
        for (VjCodifications codes : JPAViewFunctions.getResourcePrivacy(langID)) {
            map.put(codes.getCodeValueLang(), codes.getCodeValue());
        }
        return map;
    }

    @Override
    public String getOwnersDetails(String ownerMailing, String ownerStatus, String ownerRole) {
        List<Owners> o = Queries.getOwners(ownerMailing, ownerStatus, ownerRole);
        if (!o.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
//            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_ID)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_FIRST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_LAST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_MAILING)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_PWD)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_ROLE)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_STATUS)).append("</th>");
            result.append("</tr>");
            for (Owners own : o) {
                result.append("<tr>");
//                result.append("<td>").append(own.getIdOwner()).append("</td>");
                result.append("<td><a href=\"#UM").append(own.getIdOwner()).append("\" onClick=\"return gwtnav(this);\">").append(own.getOwnerFirstName()).append("</a></td>");
                result.append("<td>").append(own.getOwnerLastName()).append("</td>");
                result.append("<td>").append(own.getOwnerMailing()).append("</td>");
                result.append("<td>").append(own.getOwnerHash()).append("</td>");
                result.append("<td>").append(own.getOwnerRoles()).append("</td>");
                result.append("<td>").append(own.getOwnerStatus()).append("</td>");
                result.append("</tr>");
            }
            o.clear();
            o = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getDomainsDetails(String domName) {
        List<Domains> dset = Queries.getDomains(domName);
        if (!dset.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_D_DEFAULT_NAME)).append("</th>");
            result.append("</tr>");
            for (Domains d : dset) {
                result.append("<tr>");
                result.append("<td><a href=\"#DM").append(d.getIdDomain()).append("\" onClick=\"return gwtnav(this);\">").append(d.getDomainDefaultName()).append("</a></td>");
                result.append("</tr>");
            }
            dset.clear();
            dset = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getResourcesDetails(String resName, String resPrivacy) {
        TermDB.restart();
        List<Resources> r = Queries.getResources(resName, resPrivacy);
        if (!r.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_PRIVACY)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NOTE)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_OWNER)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_EXTRA)).append("</th>");
            result.append("</tr>");
            for (Resources res : r) {
                result.append("<tr>");
                result.append("<td><a href=\"#RM").append(res.getIdResource()).append("\" onClick=\"return gwtnav(this);\">").append(res.getResourceName()).append("</a></td>");
                result.append("<td>").append(res.getResourcePrivacy()).append("</td>");
                result.append("<td>").append(res.getResourceNote()).append("</td>");
                result.append("<td>").append(Queries.getOwnerbyID(res.getIdOwner()).getOwnerMailing()).append("</td>");
                result.append("<td>").append(res.getExtra()).append("</td>");
                result.append("</tr>");
            }
            r.clear();
            r = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getLanguagesDetails(String langID, String langName) {
        TermDB.restart();
        List<Languages> lset = Queries.getLanguages(langID, langName);
        if (!lset.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_L_ID)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_L_DEFAULT_NAME)).append("</th>");
            result.append("</tr>");
            for (Languages l : lset) {
                result.append("<tr>");
                result.append("<td><a href=\"#LM").append(l.getIdLanguage()).append("\" onClick=\"return gwtnav(this);\">").append(l.getIdLanguage()).append("</a></td>");
                result.append("<td>").append(l.getLanguageDefaultName()).append("</td>");
                result.append("</tr>");
            }
            lset.clear();
            lset = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getUsersResourcesDetails(long ownerID, long resID, String role) {
        TermDB.restart();
        List<VjUsersResources> usRes = JPAViewFunctions.getUsersResources(ownerID, resID, role);
        if (!usRes.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_FIRST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_LAST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_MAILING)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NOTE)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_PRIVACY)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_ROLE)).append("</th>");
            result.append("</tr>");
            for (VjUsersResources ur : usRes) {
                result.append("<tr>");
                result.append("<td><a href=\"#UR").append(ur.getIdLink()).append("\" onClick=\"return gwtnav(this);\">").append(ur.getOwnerFirstName()).append("</a></td>");
                result.append("<td>").append(ur.getOwnerLastName()).append("</td>");
                result.append("<td>").append(ur.getOwnerMailing()).append("</td>");
                result.append("<td>").append(ur.getResourceName()).append("</td>");
                result.append("<td>").append(ur.getResourceNote()).append("</td>");
                result.append("<td>").append(ur.getResourcePrivacy()).append("</td>");
                result.append("<td>").append(ur.getOwnerRoles()).append("</td>");
                result.append("</tr>");
            }
            usRes.clear();
            usRes = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getUsersResourcesDetails(long id) {
        TermDB.restart();
        List<VjUsersResources> usRes = JPAViewFunctions.getUsersResources(id);
        if (!usRes.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_FIRST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_LAST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_MAILING)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_NOTE)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_R_PRIVACY)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_ROLE)).append("</th>");
            result.append("</tr>");
            for (VjUsersResources ur : usRes) {
                result.append("<tr>");
                result.append("<td><a href=\"#UR").append(ur.getIdLink()).append("\" onClick=\"return gwtnav(this);\">").append(ur.getOwnerFirstName()).append("</a></td>");
                result.append("<td>").append(ur.getOwnerLastName()).append("</td>");
                result.append("<td>").append(ur.getOwnerMailing()).append("</td>");
                result.append("<td>").append(ur.getResourceName()).append("</td>");
                result.append("<td>").append(ur.getResourceNote()).append("</td>");
                result.append("<td>").append(ur.getResourcePrivacy()).append("</td>");
                result.append("<td>").append(ur.getOwnerRoles()).append("</td>");
                result.append("</tr>");
            }
            usRes.clear();
            usRes = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getUsersLanguagesDetails(long ownerID, String langID) {
        TermDB.restart();
        List<VjUsersLanguages> usLan = JPAViewFunctions.getUsersLanguages(ownerID, langID);
        if (!usLan.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_FIRST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_LAST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_MAILING)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_L_DEFAULT_NAME)).append("</th>");
            result.append("</tr>");
            for (VjUsersLanguages ul : usLan) {
                result.append("<tr>");
                result.append("<td><a href=\"#UL").append(ul.getIdLink()).append("\" onClick=\"return gwtnav(this);\">").append(ul.getOwnerFirstName()).append("</a></td>");
                result.append("<td>").append(ul.getOwnerLastName()).append("</td>");
                result.append("<td>").append(ul.getOwnerMailing()).append("</td>");
                result.append("<td>").append(ul.getLanguageDefaultName()).append("</td>");
                result.append("</tr>");
            }
            usLan.clear();
            usLan = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getUsersLanguagesDetails(long id) {
        TermDB.restart();
        List<VjUsersLanguages> usLan = JPAViewFunctions.getUsersLanguages(id);
        if (!usLan.isEmpty()) {
            StringBuilder result = new StringBuilder("");
            if ((sysMsgsrv == null) || (sysMsgsrv.isEmpty())) {
                sysMsgsrv = new HashMap<>();
                List<VjCodifications> codes = JPAViewFunctions.getCodificationByTypeAndLang("msg", GuiConstant.INTERFACE_LANG);
                for (VjCodifications field : codes) {
                    sysMsgsrv.put(field.getCodeValue(), field.getCodeExtraLang());
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_FIRST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_LAST_NAME)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_O_MAILING)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.LBL_L_DEFAULT_NAME)).append("</th>");
            result.append("</tr>");
            for (VjUsersLanguages ul : usLan) {
                result.append("<tr>");
                result.append("<td><a href=\"#UL").append(ul.getIdLink()).append("\" onClick=\"return gwtnav(this);\">").append(ul.getOwnerFirstName()).append("</a></td>");
                result.append("<td>").append(ul.getOwnerLastName()).append("</td>");
                result.append("<td>").append(ul.getOwnerMailing()).append("</td>");
                result.append("<td>").append(ul.getLanguageDefaultName()).append("</td>");
                result.append("</tr>");
            }
            usLan.clear();
            usLan = null;
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getEntitiesDetails(String s, String langID, long resID, String domID) {
        TermDB.restart();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OwnerDTO getOwnerDetails(long ownerID) {
        TermDB.restart();
        Owners own = Queries.getOwnerbyID(ownerID);
        if (own != null) {
            OwnerDTO owner = new OwnerDTO(own.getIdOwner(), own.getOwnerFirstName(), own.getOwnerLastName(), own.getOwnerHash(), own.getOwnerMailing(), own.getOwnerRoles(), own.getOwnerStatus());
            own = null;
            return owner;
        }
        return null;
    }

    @Override
    public ResourceDTO getResourceDetails(long resID) {
        TermDB.restart();
        Resources res = Queries.getResourceByID(resID);
        if (res != null) {
            ResourceDTO rsrc = new ResourceDTO(res.getIdResource(), res.getIdOwner(), res.getResourceName(), res.getResourcePrivacy(), res.getResourceNote(), res.getExtra());
            res = null;
            return rsrc;
        }
        return null;
    }

    @Override
    public DomainDTO getDomainDetails(long domID) {
        TermDB.restart();
        Domains dom = Queries.getDomainByID(domID);
        if (dom != null) {
            DomainDTO doms = new DomainDTO(dom.getIdDomain(), dom.getDomainDefaultName());
            dom = null;
            return doms;
        }
        return null;
    }

    @Override
    public LanguageDTO getLanguageDetails(String langID) {
        TermDB.restart();
        Languages lan = Queries.getLanguageByID(langID);
        if (lan != null) {
            LanguageDTO lang = new LanguageDTO(lan.getIdLanguage(), lan.getLanguageDefaultName());
            lan = null;
            return lang;
        }
        return null;
    }

    @Override
    public String createUser(OwnerDTO ownerDTO) {
        ManageOwner.create(ownerDTO.getFirstName(), ownerDTO.getLastName(),
                ownerDTO.getEmail(), ownerDTO.getStatus(), ownerDTO.getHash(), ownerDTO.getRole());
        return "sucess";
    }

    @Override
    public OwnerDTO AdminUpdateUser(OwnerDTO ownerDTO) {
        Owners oa = new Owners();
        oa.setIdOwner(ownerDTO.getId());
        oa.setOwnerFirstName(ownerDTO.getFirstName());
        oa.setOwnerLastName(ownerDTO.getLastName());
        oa.setOwnerMailing(ownerDTO.getEmail());
        oa.setOwnerRoles(ownerDTO.getRole());
        oa.setOwnerStatus(ownerDTO.getStatus());
        oa.setOwnerHash(ownerDTO.getHash());
        oa = ManageOwner.edit(oa);
        if (oa != null) {
            return getOwnerDetails(oa.getIdOwner());
        }
        return null;
    }

    @Override
    public String AdminSaveUser(OwnerDTO ownerDTO) {
        Owners oa = new Owners();
        oa.setIdOwner(ownerDTO.getId());
        oa.setOwnerFirstName(ownerDTO.getFirstName());
        oa.setOwnerLastName(ownerDTO.getLastName());
        oa.setOwnerMailing(ownerDTO.getEmail());
        oa.setOwnerRoles(ownerDTO.getRole());
        oa.setOwnerStatus(ownerDTO.getStatus());
        oa.setOwnerHash(ownerDTO.getHash());
        oa = ManageOwner.edit(oa);
        if (oa != null) {
            oa = null;
            return "sucess";
        }
        return null;
    }

    @Override
    public Boolean getOwnerUsage(long ownerID) {
        return Queries.getOwnerActivity(ownerID);
    }

    @Override
    public String deleteUser(long ownerID) {
        ManageOwner.remove(ownerID);
        return "Success";
    }

    @Override
    public Boolean getResourceUsage(long resID) {
        return JPAViewFunctions.getResourceActivity(resID);
    }

    @Override
    public String deleteResource(long resID) {
        ManageResource.remove(resID);
        return "Success";
    }

    @Override
    public ResourceDTO AdminUpdateResource(ResourceDTO resourceDTO) {
        Resources rs = new Resources();
        rs.setIdResource(resourceDTO.getIdResource());
        rs.setIdOwner(resourceDTO.getIdOwner());
        rs.setResourceName(resourceDTO.getResourceName());
        rs.setResourceNote(resourceDTO.getResourceNote());
        rs.setResourcePrivacy(resourceDTO.getResourcePrivacy());
        rs.setExtra(resourceDTO.getExtra());
        rs = ManageResource.edit(rs);
        if (rs != null) {
            return getResourceDetails(rs.getIdResource());
        }
        return null;
    }

    @Override
    public String AdminSaveResource(ResourceDTO resourceDTO) {
        Resources rs = new Resources();
        rs.setIdResource(resourceDTO.getIdResource());
        rs.setIdOwner(resourceDTO.getIdOwner());
        rs.setResourceName(resourceDTO.getResourceName());
        rs.setResourceNote(resourceDTO.getResourceNote());
        rs.setResourcePrivacy(resourceDTO.getResourcePrivacy());
        rs.setExtra(resourceDTO.getExtra());
        rs = ManageResource.edit(rs);
        if (rs != null) {
            rs = null;
            return "sucess";
        }
        return null;
    }

    @Override
    public String createResource(ResourceDTO resourceDTO) {
        ManageResource.create(resourceDTO.getResourceName(), resourceDTO.getResourcePrivacy(), resourceDTO.getIdOwner(), resourceDTO.getExtra());
        return "sucess";
    }

    @Override
    public Boolean getLanguageUsage(String langID) {
        return JPAViewFunctions.getLanguageActivity(langID);
    }

    @Override
    public String createLanguage(LanguageDTO langDTO) {
        Languages lan = new Languages();
        lan.setIdLanguage(langDTO.getIdLanguage());
        lan.setLanguageDefaultName(langDTO.getLanguageDefaultName());
        if (ManageLanguages.create(lan) != null) {
            return "sucess";
        }
        return null;
    }

    @Override
    public String deleteLanguage(String langID) {
        ManageLanguages.remove(langID);
        return "success";
    }

    @Override
    public LanguageDTO AdminUpdateLanguage(LanguageDTO langDTO) {
        Languages lan = new Languages();
        lan.setIdLanguage(langDTO.getIdLanguage());
        lan.setLanguageDefaultName(langDTO.getLanguageDefaultName());
        lan = ManageLanguages.update(lan);
        if (lan != null) {
            return getLanguageDetails(lan.getIdLanguage());
        }
        return null;
    }

    @Override
    public String AdminSaveLanguage(LanguageDTO langDTO) {
        Languages lan = new Languages();
        lan.setIdLanguage(langDTO.getIdLanguage());
        lan.setLanguageDefaultName(langDTO.getLanguageDefaultName());
        lan = ManageLanguages.update(lan);
        if (lan != null) {
            lan = null;
            return "sucess";
        }
        return null;
    }

    @Override
    public String createUserResource(UserResourceDTO userResource) {
        UsersResources usrRes = new UsersResources();
        usrRes.setIdOwner(userResource.getIdOwner());
        usrRes.setIdResource(userResource.getIdResource());
        usrRes.setOwnerRoles(userResource.getOwnerRole());
        TermDB.usersResourcesJC.create(usrRes);
        return "success";
    }

    @Override
    public String deleteUserResource(long id) {
        try {
            TermDB.usersResourcesJC.destroy(id);
            return "success";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UserResourceDTO getUserResource(long id) {
        UsersResources ures = Queries.getUserResource(id);
        UserResourceDTO usrRes = new UserResourceDTO();
        usrRes.setIdResource(ures.getIdResource());
        usrRes.setId(ures.getIdLink());
        usrRes.setIdOwner(ures.getIdOwner());
        usrRes.setOwnerRole(ures.getOwnerRoles());
        return usrRes;
    }

    @Override
    public UserResourceDTO AdminUpdateUserResource(UserResourceDTO userResource) {
        UsersResources usrRes = new UsersResources();
        usrRes.setIdOwner(userResource.getIdOwner());
        usrRes.setIdLink(userResource.getId());
        usrRes.setIdResource(userResource.getIdResource());
        usrRes.setOwnerRoles(userResource.getOwnerRole());
        try {
            TermDB.usersResourcesJC.edit(usrRes);
            return getUserResource(usrRes.getIdLink());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String AdminSaveUserResource(UserResourceDTO userResource) {
        UsersResources usrRes = new UsersResources();
        usrRes.setIdOwner(userResource.getIdOwner());
        usrRes.setIdLink(userResource.getId());
        usrRes.setIdResource(userResource.getIdResource());
        usrRes.setOwnerRoles(userResource.getOwnerRole());
        try {
            TermDB.usersResourcesJC.edit(usrRes);
            return "success";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String createUserLanguage(UserLanguageDTO userLanguage) {
        UsersLanguages usrLang = new UsersLanguages();
        usrLang.setIdLanguage(userLanguage.getIdLang());
        usrLang.setIdOwner(userLanguage.getIdOwner());
        TermDB.usersLanguagesJC.create(usrLang);
        return "success";
    }

    @Override
    public String deleteUserLanguage(long id) {
        try {
            TermDB.usersLanguagesJC.destroy(id);
            return "success";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UserLanguageDTO getUserLanguage(long id) {
        UsersLanguages ulang = Queries.getUserLanguage(id);
        UserLanguageDTO usrLang = new UserLanguageDTO();
        usrLang.setIdLang(ulang.getIdLanguage());
        usrLang.setId(ulang.getIdLink());
        usrLang.setIdOwner(ulang.getIdOwner());
        return usrLang;
    }

    @Override
    public UserLanguageDTO AdminUpdateUserLanguage(UserLanguageDTO userLanguage) {
        UsersLanguages usrLang = new UsersLanguages();
        usrLang.setIdLanguage(userLanguage.getIdLang());
        usrLang.setIdOwner(userLanguage.getIdOwner());
        usrLang.setIdLink(userLanguage.getId());
        try {
            TermDB.usersLanguagesJC.edit(usrLang);
            return getUserLanguage(usrLang.getIdLink());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String AdminSaveUserLanguage(UserLanguageDTO userLanguage) {
        UsersLanguages usrLang = new UsersLanguages();
        usrLang.setIdLanguage(userLanguage.getIdLang());
        usrLang.setIdOwner(userLanguage.getIdOwner());
        usrLang.setIdLink(userLanguage.getId());
        try {
            TermDB.usersLanguagesJC.edit(usrLang);
            return "success";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean getDomainUsage(long domID) {
        return Queries.getDomainActivity(domID);
    }

    @Override
    public String createDomain(DomainDTO domain) {
        Domains dom = new Domains();
        dom.setIdDomain(domain.getIdDomain());
        dom.setDomainDefaultName(domain.getDomainDefaultName());
        TermDB.domainsJC.create(dom);
        return "success";
    }

    @Override
    public String deleteDomain(long domID) {
        try {
            TermDB.domainsJC.destroy(domID);
            return "sucess";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public DomainDTO AdminUpdateDomain(DomainDTO domain) {
        Domains dom = new Domains();
        dom.setIdDomain(domain.getIdDomain());
        dom.setDomainDefaultName(domain.getDomainDefaultName());
        try {
            TermDB.domainsJC.edit(dom);
            return getDomainDetails(dom.getIdDomain());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String AdminSaveDomain(DomainDTO domain) {
        Domains dom = new Domains();
        dom.setIdDomain(domain.getIdDomain());
        dom.setDomainDefaultName(domain.getDomainDefaultName());
        try {
            TermDB.domainsJC.edit(dom);
            return "success";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(myTermServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean printConceptEntry(String resourceName, long conceptId, String language) {
         System.out.println("printConceptEntry");
        String fileName = "C:\\MYTERM\\includehtmltag\\print\\Concept" + conceptId + ".xml";
        PrintXMLFromDB.init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\print\\xml\\print.properties");
        String contentXML = PrintXMLFromDB.doIt(resourceName, conceptId, language);
        if (contentXML != null && !contentXML.isEmpty()) {
            if (UtilsFiles.String2File(fileName, contentXML) != null) {
                return true;
            }
        }
        return false;
    }
    
    

    @Override
    public Boolean printConceptEntry4RR(String resourceName, long conceptid, String language) {
        System.out.println("printConceptEntry4RR");
  String fileName = "C:\\MYTERM\\includehtmltag\\print\\Concept" + conceptid + ".xml";
        PrintXMLFromDB.init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\print\\xml\\print4RR.properties");
        String contentXML = PrintXMLFromDB.doIt(resourceName, conceptid, language);
        if (contentXML != null && !contentXML.isEmpty()) {
            if (UtilsFiles.String2File(fileName, contentXML) != null) {
                return true;
            }
        }
        return false;
    }
}
