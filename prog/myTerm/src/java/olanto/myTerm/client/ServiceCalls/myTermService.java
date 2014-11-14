/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import olanto.myTerm.client.Domains.Domain;
import olanto.myTerm.client.Langs.Language;
import olanto.myTerm.client.Resources.Resource;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("mytermservice")
public interface myTermService extends RemoteService {

    public String getdetailsForConcept(long conceptID);

    public String getdetailsForTerms(long conceptID, String langS, String langT);

    public String getSearchResult(String s, String ls, String lt, String resID, String domID);

    public String getAddResult(String s, String ls, String lt, String resID, String domID);

    public ArrayList<String> getResults(String s, String ls, String lt);

    public ArrayList<Language> getLanguages();

    public ArrayList<Domain> getDomains();

    public ArrayList<Resource> getResources(long ownerID);

    public String getInventory();
}
