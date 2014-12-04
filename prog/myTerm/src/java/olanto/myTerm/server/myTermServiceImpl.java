/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import jpaviewtest.TestView;
import jpaviewtest.entities.VjUsersResources;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.ResourceDTO;

import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.shared.ConceptDTO;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.LangSetDTO;
import olanto.myTerm.shared.TermDTO;
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

    @Override
    public String getSearchResult(String s, String ls, String lt, String resID, String domID) {
        String response = TestView.getTargetForThis(s, ls, lt, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append(Queries.getLanguageByID(lt).getLanguageDefaultName()).append("</th>");
            result.append("</tr>");
            result.append(TestView.getTargetForThis(s, ls, lt, resID, domID));
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
        result.append(TestView.getReslang());
        result.append("</div>");
        return result.toString();
    }

    @Override
    public ArrayList<String> getResults(String s, String ls, String lt) {
        ArrayList<String> termsList = new ArrayList<>();
        termsList.addAll(TestView.getListForThis(s, ls, lt));
        return termsList;
    }

    @Override
    public ArrayList<LanguageDTO> getLanguages() {
        List<Languages> l = Queries.getLanguages();
        ArrayList<LanguageDTO> languages = new ArrayList<>();
        for (Languages lang : l) {
            LanguageDTO li = new LanguageDTO();
            li.setIdLanguage(lang.getIdLanguage());
            li.setLanguageDefaultName(lang.getLanguageDefaultName());
            languages.add(li);
        }
        return languages;
    }

    @Override
    public ArrayList<ResourceDTO> getResources(long ownerID) {
        // try and catch, problem with the ownerID
        if (ownerID > 0) {
            List<VjUsersResources> l = TestView.getResourcesByOwner(ownerID);
            if (!l.isEmpty()) {
                ArrayList<ResourceDTO> resources = new ArrayList<>();
                for (VjUsersResources res : l) {
                    ResourceDTO r = new ResourceDTO();
                    r.setIdResource(res.getIdResource());
                    r.setResourceName(res.getResourceName());
                    resources.add(r);
                }
                return resources;
            }
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<DomainDTO> getDomains() {
        List<Domains> l = Queries.getDomains();
        ArrayList<DomainDTO> domains = new ArrayList<>();
        for (Domains res : l) {
            DomainDTO d = new DomainDTO();
            d.setIdDomain(res.getIdDomain());
            d.setDomainDefaultName(res.getDomainDefaultName());
            domains.add(d);
        }
        return domains;
    }

    @Override
    public String getdetailsForConcept(long conceptID) {
        StringBuilder result = new StringBuilder("");
        Concepts c = Queries.getConceptByID(conceptID);
        if (c != null) {
            result.append("<div class =\"cpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append("Details for concept: ").append(c.getIdConcept()).append("</th>");
            result.append("<th>").append("From resource: ").append(Queries.getIdResources(c.getIdResource()).getResourceName()).append("</th>");
            result.append("<th>").append("Other details").append("</th>");
            result.append("<th>").append("Extra information").append("</th>");
            result.append("</tr>");
            result.append("<tr>");
            result.append("<td>");
            if ((c.getSubjectField() != null)) {
                result.append("&nbsp").append("<span class = \"sfield\">Subject field: </span>").append(c.getSubjectField()).append("<br/>");
            }
            if ((c.getConceptDefinition() != null)) {
                result.append("&nbsp").append("<span class = \"def\">Definition: </span>").append(c.getConceptDefinition()).append("<br/>");
            }
            if ((c.getConceptSourceDefinition() != null)) {
                result.append("&nbsp").append("<span class = \"defsrc\">Definition's source: </span>").append(c.getConceptSourceDefinition()).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getConceptNote() != null)) {
                result.append("&nbsp").append("<span class = \"note\">Note: </span>").append(c.getConceptNote()).append("<br/>");
            }
            if ((c.getCrossref() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Cross reference: </span>").append(c.getCrossref()).append("<br/>");
            }
            if ((c.getExtcrossref() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Extra cross reference: </span>").append(c.getExtcrossref()).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if ((c.getCreateBy() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Created By: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getCreateBy().toString()))).append("<br/>");
            }
            if (c.getCreation() != null) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Creation Date: </span>").append(c.getCreation()).append("<br/>");
            }
            if (c.getLastmodifiedBy() != null) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Last modified by: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getLastmodifiedBy().toString()))).append("<br/>");
            }
            result.append("</td>").append("<td>");
            if (c.getLastmodified() != null) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Last modification on: </span>").append(c.getLastmodified()).append("<br/>");
            }
            if ((c.getImage() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Image: </span>").append(c.getImage()).append("<br/>");
            }
            if ((c.getExtra() != null)) {
                result.append("&nbsp").append("<span class = \"extrainfo\">Other information: </span>").append(c.getExtra()).append("<br/>");
            }
            result.append("</td>").append("</tr>");
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public String getdetailsForTerms(long conceptID, String langS, String langT) {
        StringBuilder result = new StringBuilder("");
        result.append("<div class =\"tpanel\">");
        result.append("<table>");
        result.append("<tr>");
        result.append("<th>").append(Queries.getLanguageByID(langS).getLanguageDefaultName()).append("</th>");
        result.append("<th>").append(Queries.getLanguageByID(langT).getLanguageDefaultName()).append("</th>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td>")
                .append(TestView.getTermsInfo(conceptID, langS))
                .append("</td>");
        result.append("<td>")
                .append(TestView.getTermsInfo(conceptID, langT))
                .append("</td>");
        result.append("</tr>");
        result.append("</table>");
        result.append("</div>");
        return result.toString();
    }

    @Override
    public String getAddResult(String s, String ls, String lt, String resID, String domID) {
        String response = TestView.getSourceForThis(s, ls, lt, resID, domID);
        if (response != null) {
            StringBuilder result = new StringBuilder("");
            result.append("<div class =\"rpanel\">");
            result.append("<table>");
            result.append("<tr>");
            result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
            result.append("<th>").append(Queries.getLanguageByID(lt).getLanguageDefaultName()).append("</th>");
            result.append("</tr>");
            result.append(TestView.getSourceForThis(s, ls, lt, resID, domID));
            result.append("</table>");
            result.append("</div>");
            return result.toString();
        }
        return null;
    }

    @Override
    public ConceptEntryDTO getAddDetailsForConcept(long conceptID) {
        ConceptEntryDTO conceptDTO = new ConceptEntryDTO();
        ConceptEntry c = TestView.getConceptAndAssociatedTerms(conceptID);
        copyConceptEntry(conceptDTO, c);
        return conceptDTO;
    }

    private void copyConceptEntry(ConceptEntryDTO conceptDTO, ConceptEntry concept) {
        copyConcept(conceptDTO.concept, concept.getConcept());
        if (!concept.listlang.isEmpty()) {
            for (LangEntry ls : concept.listlang) {
                LangEntryDTO langE = new LangEntryDTO();
                langE.lan = copyLangSet(ls.lan);
                if (!ls.listterm.isEmpty()) {
                    for (Terms t : ls.listterm) {
                        langE.listterm.add(copyTerm(t));
                    }
                }
                conceptDTO.listlang.add(langE);
            }
        }
    }

    private void copyConcept(ConceptDTO cDTO, Concepts c) {
        cDTO.setConceptDefinition(c.getConceptDefinition());
        cDTO.setConceptNote(c.getConceptNote());
        cDTO.setConceptSourceDefinition(c.getConceptDefinition());
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

    private LangSetDTO copyLangSet(Langsets ls) {
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

    private TermDTO copyTerm(Terms t) {
        if (t != null) {
            TermDTO tDTO = new TermDTO();
            tDTO.setCreateBy(t.getCreateBy());
            tDTO.setCreation(t.getCreation());
            tDTO.setCrossref(t.getCrossref());
            tDTO.setExtcrossref(t.getExtcrossref());
            tDTO.setExtra(t.getExtra());
            tDTO.setIdLangset(t.getIdLangset());
            tDTO.setIdLanguage(t.getIdLanguage());
            tDTO.setIdTerm(t.getIdTerm());
            tDTO.setLastmodified(t.getLastmodified());
            tDTO.setLastmodifiedBy(t.getLastmodifiedBy());
            tDTO.setSeq(t.getSeq());
            tDTO.setStatus(t.getStatus());
            tDTO.setTermAdminStatus(t.getTermAdminStatus());
            tDTO.setTermContext(t.getTermContext());
            tDTO.setTermContext(t.getTermDefinition());
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
}
