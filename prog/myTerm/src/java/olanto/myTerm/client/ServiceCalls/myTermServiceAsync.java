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
import olanto.myTerm.shared.TermDTO;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getAddResult(String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForConcept(long conceptID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, AsyncCallback<String> callback);

    public void getAddDetailsForConcept(long conceptID, long ownerID, AsyncCallback<ConceptEntryDTO> callback);

    public void getResults(String s, String ls, String lt, long ownerID, AsyncCallback<ArrayList<String>> callback);

    public void getWorkspaceElements(String ls, long ownerID, AsyncCallback<String> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<ArrayList<LanguageDTO>> asyncCallback);

    public void getDomains(AsyncCallback<ArrayList<DomainDTO>> asyncCallback);

    public void getResourcesByOwner(long ownerID, AsyncCallback<ArrayList<ResourceDTO>> asyncCallback);

    public void getLanguagesByOwner(long ownerID, AsyncCallback<ArrayList<LanguageDTO>> asyncCallback);

    public void SubmitConceptEntry(ConceptEntryDTO conceptEntryDTO, String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> asyncCallback);

    public void UpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<ConceptEntryDTO> asyncCallback);

    public void DeleteConceptEntry(long conceptID, String ls, long ownerID, AsyncCallback<String> asyncCallback);

    public void DeleteTermEntry(TermDTO termEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);
}
