/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import jpaviewtest.TestView;
import olanto.myTerm.client.Langs.Language;
import olanto.myTerm.client.Resources.Resource;

import olanto.myTerm.client.ServiceCalls.myTermService;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Concepts;
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
        result.append("<td>")
                .append("&nbsp").append("<span class = \"sfield\">Subject field: </span>").append(c.getSubjectField()).append("<br/>")
                .append("&nbsp").append("<span class = \"def\">Definition: </span>").append(c.getConceptDefinition()).append("<br/>")
                .append("&nbsp").append("<span class = \"defsrc\">Definition's source: </span>").append(c.getConceptSourceDefinition()).append("<br/>")
                .append("</td>");
        result.append("<td>")
                .append("&nbsp").append("<span class = \"note\">Note: </span>").append(c.getConceptNote()).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Cross reference: </span>").append(c.getCrossref()).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Extra cross reference: </span>").append(c.getExtcrossref()).append("<br/>")
                .append("</td>");
        result.append("<td>")
                .append("&nbsp").append("<span class = \"extrainfo\">Created By: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getCreateBy().toString()))).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Creation Date: </span>").append(c.getCreation()).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Last modified by: </span>").append(Queries.getOwnerFullNamebyID(Long.parseLong(c.getLastmodifiedBy().toString()))).append("<br/>")
                .append("</td>");
        result.append("<td>")
                .append("&nbsp").append("<span class = \"extrainfo\">Last modification on: </span>").append(c.getLastmodified()).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Image: </span>").append(c.getImage()).append("<br/>")
                .append("&nbsp").append("<span class = \"extrainfo\">Extra information: </span>").append(c.getExtra()).append("<br/>")
                .append("</td>");
        result.append("</tr>");
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
