/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("mytermservice")
public interface myTermService extends RemoteService {

    public String getSearchResult(String s, String ls, String lt);
    public ArrayList<String> getResults(String s, String ls, String lt);
    public String getInventory();
}
