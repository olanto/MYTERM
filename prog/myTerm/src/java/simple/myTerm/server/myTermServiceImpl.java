/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
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
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package simple.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import jpaviewtest.TestView;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Resources;
import simple.myTerm.client.Main.Langs.Language;
import simple.myTerm.client.Main.Resources.Resource;

import simple.myTerm.client.Main.request.myTermService;

/**
 *
 * @author nizar ghoula - simple
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
        result.append("<th>Details</th>");
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
}
