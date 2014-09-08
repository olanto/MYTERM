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

import simple.myTerm.client.Main.request.myTermService;

/**
 *
 * @author nizar ghoula - simple
 */
public class myTermServiceImpl extends RemoteServiceServlet implements myTermService {

    @Override
    public String getSearchResult(String s, String ls, String lt) {
        String result = "<div class =\"panel\">"
                + "<p>Results for query</p>"
                + TestView.getTargetForThis(s, ls, lt)
                + "</div>";
        return result;
    }

    @Override
    public String getInventory() {

        String result = "<div class =\"panel\">"
                + "<P>Terminology Manager's Statistics</p>"
                + TestView.getReslang()
                + "</div>";
        return result;
    }

    @Override
    public ArrayList<String> getResults(String s, String ls, String lt) {
        ArrayList<String> termsList = new ArrayList<String>();
        termsList.addAll(TestView.getListForThis(s, ls, lt));
        return termsList;
    }

    @Override
    public ArrayList<String> getLanguages() {
        List<Languages> l = Queries.getLanguages();
        ArrayList<String> languages = new ArrayList<String>();
        for (Languages lang : l) {
            languages.add(lang.getIdLanguage());
        }
        return languages;
    }
}
