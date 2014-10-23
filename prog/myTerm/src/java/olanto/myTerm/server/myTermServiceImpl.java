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

import olanto.myTerm.client.myTermService;
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
        result.append("<th>Main details</th>");
        result.append("<th>Extra Information</th>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td>").append("&nbsp").append("Subject field:").append(c.getSubjectField()).append("<br/>")
                .append("&nbsp").append("Definition:").append(c.getConceptDefinition()).append("<br/>")
                .append("&nbsp").append("Definition's source:").append(c.getConceptSourceDefinition()).append("<br/>")
                .append("&nbsp").append("Note:").append(c.getConceptNote()).append("<br/>")
                .append("&nbsp").append("Cross reference:").append(c.getCrossref()).append("<br/>")
                .append("&nbsp").append("Extra cross reference:").append(c.getExtcrossref()).append("<br/>")
                .append("</td>");
        result.append("<td>").append("&nbsp").append("Created By:").append(c.getCreateBy()).append("<br/>")
                .append("&nbsp").append("Creation Date:").append(c.getCreation()).append("<br/>")
                .append("&nbsp").append("Last modified by:").append(c.getLastmodifiedBy()).append("<br/>")
                .append("&nbsp").append("Last modification on:").append(c.getLastmodified()).append("<br/>")
                .append("&nbsp").append("Image:").append(c.getImage()).append("<br/>")
                .append("&nbsp").append("Extra information:").append(c.getExtra()).append("<br/>")
                .append("</td>");
        result.append("</tr>");
        result.append("</table>");
        result.append("</div>");
        return result.toString();
    }
}
