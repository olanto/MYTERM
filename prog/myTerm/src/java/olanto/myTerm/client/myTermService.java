/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import olanto.myTerm.client.Langs.Language;
import olanto.myTerm.client.Resources.Resource;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("mytermservice")
public interface myTermService extends RemoteService {

    public String getdetailsForConcept(long conceptID);

    public String getSearchResult(String s, String ls, String lt);

    public ArrayList<String> getResults(String s, String ls, String lt);

    public ArrayList<Language> getLanguages();

    public ArrayList<Resource> getResources();

    public String getInventory();
}
