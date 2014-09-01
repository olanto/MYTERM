/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import jpaviewtest.TestView;

import simple.myTerm.client.myTermService;

/**
 *
 * @author simple
 */
public class myTermServiceImpl extends RemoteServiceServlet implements myTermService {

    @Override
    public String getSearchResult(String s, String ls, String lt) {
        String result = "<html><h1>"
                + "Results for query"
                + "</h1>"
                + TestView.getTargetForThis(s, ls, lt)
                + "</html>";
        return result;
    }

    @Override
    public String getInventory() {

          String result = "<html><h1>"
                + "Terminology Manager's Statistics"
                + "</h1>"
                + TestView.getReslang()
                + "</html>";
        return result;
    }

    @Override
    public ArrayList<String> getResults(String s, String ls, String lt) {
        ArrayList<String> termsList = new ArrayList<String>();
        termsList.addAll(TestView.getListForThis(s, ls, lt));
        return termsList;
    }
}
