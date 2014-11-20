/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.ResourceDTO;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, String resID, String domID, AsyncCallback<String> callback);

    public void getAddResult(String s, String ls, String lt, String resID, String domID, AsyncCallback<String> callback);

    public void getdetailsForConcept(long conceptID, AsyncCallback<String> callback);

    public void getdetailsForTerms(long conceptID, String langS, String langT, AsyncCallback<String> callback);

    public void getAddDetailsForConcept(long conceptID, AsyncCallback<ConceptEntryDTO> callback);

    public void getResults(String s, String ls, String lt, AsyncCallback<ArrayList<String>> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<ArrayList<LanguageDTO>> asyncCallback);

    public void getDomains(AsyncCallback<ArrayList<DomainDTO>> asyncCallback);

    public void getResources(long ownerID, AsyncCallback<ArrayList<ResourceDTO>> asyncCallback);
}
