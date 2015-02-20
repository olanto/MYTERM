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
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;
import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.ManageTerm;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Domains;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.entry.LangEntry;

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
        String response = JPAViewFunctions.getPublicSearchBySourceTarget(s, ls, lt, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append(Queries.getLanguageByID(lt).getLanguageDefaultName()).append("</th>");
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
                            SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim());
                            sysFieldsrv.put(field.getCodeValue(), s);
                        }
                    }
                }
            }
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.DEF_DETAILS)).append(c.getIdConcept()).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.RESOURCE_DETAILS)).append(Queries.getIdResources(c.getIdResource()).getResourceName()).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.CREATION_DETAILS)).append("</th>");
            result.append("<th>").append(sysMsgsrv.get(GuiConstant.EXTRA_DETAILS)).append("</th>");
            result.append("</tr>");
            result.append("<tr>");
            result.append("<td>");

            if ((c.getSubjectField() != null) && (!c.getSubjectField().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_SUBJECT_FIELD).getVisibility())) {
                result.append("&nbsp").append("<span class = \"sfield\">").append(sysMsgsrv.get(GuiConstant.LBL_C_SUBJECT_FIELD)).append(" </span>").append(c.getSubjectField()).append("<br/>");
            }
            if ((c.getConceptDefinition() != null) && (!c.getConceptDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_DEFINITION).getVisibility())) {
                result.append("&nbsp").append("<span class = \"def\">").append(sysMsgsrv.get(GuiConstant.LBL_C_DEFINITION)).append(" </span>").append(c.getConceptDefinition()).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getConceptSourceDefinition() != null) && (!c.getConceptSourceDefinition().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_SOURCE_DEFINITION).getVisibility())) {
                result.append("&nbsp").append("<span class = \"defsrc\">").append(sysMsgsrv.get(GuiConstant.LBL_C_SOURCE_DEFINITION)).append(" </span>").append(c.getConceptSourceDefinition()).append("<br/>");
            }
            if ((c.getConceptNote() != null) && (!c.getConceptNote().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_NOTE).getVisibility())) {
                result.append("&nbsp").append("<span class = \"note\">").append(sysMsgsrv.get(GuiConstant.LBL_C_NOTE)).append(" </span>").append(c.getConceptNote()).append("<br/>");
            }
            if ((c.getCrossref() != null) && (!c.getCrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_CROSS_REF).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CROSS_REF)).append(" </span>").append(c.getCrossref()).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getExtcrossref() != null) && (!c.getExtcrossref().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_EXTRA_CROSS_REF).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_EXTRA_CROSS_REF)).append(" </span>").append(c.getExtcrossref()).append("<br/>");
            }
            if ((c.getCreateBy() != null) && (sysFieldsrv.get(GuiConstant.C_CREATED_BY).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CREATED_BY)).append(" </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getCreateBy().toString()))).append("<br/>");
            }
            if ((c.getCreation() != null) && (sysFieldsrv.get(GuiConstant.C_CREATION).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_CREATION)).append(" </span>").append(DF_FR.format(c.getCreation())).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getLastmodifiedBy() != null) && (sysFieldsrv.get(GuiConstant.C_LAST_MODIF_BY).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_LAST_MODIF_BY)).append(" </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getLastmodifiedBy().toString()))).append("<br/>");
            }
            if ((c.getLastmodified() != null) && (sysFieldsrv.get(GuiConstant.C_MODIFICATION).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_MODIFICATION)).append(" </span>").append(DF_FR.format(c.getLastmodified())).append("<br/>");
            }
            if ((c.getImage() != null) && (!c.getImage().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_IMAGE).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_IMAGE)).append(" </span>").append(c.getImage()).append("<br/>");
            }
            if ((c.getExtra() != null) && (!c.getExtra().isEmpty()) && (sysFieldsrv.get(GuiConstant.C_EXTRA).getVisibility())) {
                result.append("&nbsp").append("<span class = \"extrainfo\">").append(sysMsgsrv.get(GuiConstant.LBL_C_EXTRA)).append(" </span>").append(c.getExtra()).append("<br/>");
            }
            result.append("</td>").append("</tr>");
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }

        return null;
    }

    @Override
    public String getdetailsForTerms(long conceptID, String langS, String langT, long ownerID) {
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
                        SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim());
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
                .append(JPAViewFunctions.getTermsInfo(conceptID, langS, sysMsgsrv, sysFieldsrv))
                .append("</td>");
        result.append("<td>")
                .append(JPAViewFunctions.getTermsInfo(conceptID, langT, sysMsgsrv, sysFieldsrv))
                .append("</td>");
        result.append("</tr>");
        result.append("</table>");
        result.append("</div>");
        return result.toString();
    }

    @Override
    public String getAddResult(String s, String ls, String resID, String domID, long ownerID) {
        String response = JPAViewFunctions.getSourceForThis(s, ls, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append("Targets").append("</th>");
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
        String response = JPAViewFunctions.getWorkspaceElementsByLang(ls, ownerID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append("Targets").append("</th>");
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
            tDTO.setLastmodifiedBy(t.getLastmodifiedBy());
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
            t.setLastmodifiedBy(tDTO.getLastmodifiedBy());
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
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append("Targets").append("</th>");
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
                        SysFieldDTO s = new SysFieldDTO(field.getCodeValue(), values[0].trim(), values[1].trim());
                        sysFields.put(field.getCodeValue(), s);
                    }
                }
            }
        }
        return sysFields;
    }

    @Override
    public Collection<String> getTermTypes(String langID) {
        return JPAViewFunctions.getTermTypes(langID);
    }

    @Override
    public Collection<String> getTermPOS(String langID) {
        return JPAViewFunctions.getTermPOS(langID);
    }

    @Override
    public Collection<String> getTermGender(String langID) {
        return JPAViewFunctions.getTermGender(langID);
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
}
