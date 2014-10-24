/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import jpaviewtest.TestView;
import olanto.myTerm.client.Domains.Domain;
import olanto.myTerm.client.Langs.Language;
import olanto.myTerm.client.Resources.Resource;

import olanto.myTerm.client.ServiceCalls.myTermService;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Domains;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Resources;

/**
 *
 * @author simple
 */
public class myTermServiceImpl extends RemoteServiceServlet implements myTermService {

    @Override
    public String getSearchResult(String s, String ls, String lt) {
        StringBuilder result = new StringBuilder("");
        result.append("<div class =\"panel\">");
        result.append("<table>");
        result.append("<tr>");
        result.append("<th>").append(Queries.getLanguageByID(ls).getLanguageDefaultName()).append("</th>");
        result.append("<th>").append(Queries.getLanguageByID(lt).getLanguageDefaultName()).append("</th>");
        result.append("</tr>");
        result.append(TestView.getTargetForThis(s, ls, lt));
        result.append("</table>");
        result.append("</div>");
        return result.toString();
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
        ArrayList<String> termsList = new ArrayList<String>();
        termsList.addAll(TestView.getListForThis(s, ls, lt));
        return termsList;
    }

    @Override
    public ArrayList<Language> getLanguages() {
        List<Languages> l = Queries.getLanguages();
        ArrayList<Language> languages = new ArrayList<Language>();
        for (Languages lang : l) {
            Language li = new Language();
            li.id = lang.getIdLanguage();
            li.language = lang.getLanguageDefaultName();
            languages.add(li);
        }
        return languages;
    }

    @Override
    public ArrayList<Resource> getResources() {
        List<Resources> l = Queries.getResources();
        ArrayList<Resource> resources = new ArrayList<Resource>();
        for (Resources res : l) {
            Resource r = new Resource();
            r.id = res.getIdResource().toString();
            r.name = res.getResourceName();
            resources.add(r);
        }
        return resources;
    }
    
    @Override
    public ArrayList<Domain> getDomains() {
        List<Domains> l = Queries.getDomains();
        ArrayList<Domain> domains = new ArrayList<Domain>();
        for (Domains res : l) {
            Domain d = new Domain();
            d.id = res.getIdDomain().toString();
            d.name = res.getDomainDefaultName();
            domains.add(d);
        }
        return domains;
    }

    @Override
    public String getdetailsForConcept(long conceptID) {
        StringBuilder result = new StringBuilder("");
        result.append("<div class =\"panel\">");
        Concepts c = Queries.getConceptByID(conceptID);
        result.append("<table>");
        result.append("<tr>");
        result.append("<th>").append("Details for concept: ").append(c.getIdConcept()).append("</th>");
        result.append("<th>").append("From resource: ").append(Queries.getIdResources(c.getIdResource()).getResourceName()).append("</th>");
        result.append("<th>").append("Other details").append("</th>");
        result.append("<th>").append("Extra information").append("</th>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td>");
        if ((!c.getSubjectField().isEmpty()) && (c.getSubjectField().equalsIgnoreCase("null")) && (c.getSubjectField() != null)) {
            result.append("&nbsp").append("<span class = \"sfield\">Subject field: </span>").append(c.getSubjectField()).append("<br/>");
        }
        if ((!c.getConceptDefinition().isEmpty()) && (c.getConceptDefinition().equalsIgnoreCase("null")) && (c.getConceptDefinition() != null)) {
            result.append("&nbsp").append("<span class = \"def\">Definition: </span>").append(c.getConceptDefinition()).append("<br/>");
        }
        if ((!c.getConceptSourceDefinition().isEmpty()) && (c.getConceptSourceDefinition().equalsIgnoreCase("null")) && (c.getConceptSourceDefinition() != null)) {
            result.append("&nbsp").append("<span class = \"defsrc\">Definition's source: </span>").append(c.getConceptSourceDefinition()).append("<br/>");
        }
        result.append("</td>").append("<td>");
        if ((!c.getConceptNote().isEmpty()) && (c.getConceptNote().equalsIgnoreCase("null")) && (c.getConceptNote() != null)) {
            result.append("&nbsp").append("<span class = \"note\">Note: </span>").append(c.getConceptNote()).append("<br/>");
        }
        if ((!c.getCrossref().isEmpty()) && (c.getCrossref().equalsIgnoreCase("null")) && (c.getCrossref() != null)) {
            result.append("&nbsp").append("<span class = \"extrainfo\">Cross reference: </span>").append(c.getCrossref()).append("<br/>");
        }
        if ((!c.getExtcrossref().isEmpty()) && (c.getExtcrossref().equalsIgnoreCase("null")) && (c.getExtcrossref() != null)) {
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
        if ((!c.getImage().isEmpty()) && (c.getImage().equalsIgnoreCase("null")) && (c.getImage() != null)) {
            result.append("&nbsp").append("<span class = \"extrainfo\">Image: </span>").append(c.getImage()).append("<br/>");
        }
        if ((!c.getExtra().isEmpty()) && (c.getExtra().equalsIgnoreCase("null")) && (c.getExtra() != null)) {
            result.append("&nbsp").append("<span class = \"extrainfo\">Extra information: </span>").append(c.getExtra()).append("<br/>");
        }
        result.append("</td>").append("</tr>");
        result.append("</table>");
        result.append("</div>");
        return result.toString();
    }

    @Override
    public String getdetailsForTerms(long conceptID, String langS, String langT) {
        StringBuilder result = new StringBuilder("");
        result.append("<div class =\"panel\">");
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
}
