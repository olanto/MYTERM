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

    public void getSearchResult(String s, String ls, String lt, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getAddResult(String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getApproveResult(String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForConcept(long conceptID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, AsyncCallback<String> callback);

    public void getAddDetailsForConcept(long conceptID, long ownerID, AsyncCallback<ConceptEntryDTO> callback);

    public void getResults(String s, String ls, String lt, long ownerID, AsyncCallback<ArrayList<String>> callback);

    public void getWorkspaceElements(String ls, long ownerID, AsyncCallback<String> callback);

    public void getApproveElements(String ls, long ownerID, AsyncCallback<String> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<ArrayList<LanguageDTO>> asyncCallback);

    public void getDomains(AsyncCallback<ArrayList<DomainDTO>> asyncCallback);

    public void getResourcesByOwner(long ownerID, AsyncCallback<ArrayList<ResourceDTO>> asyncCallback);

    public void getLanguagesByOwner(long ownerID, AsyncCallback<ArrayList<LanguageDTO>> asyncCallback);

    public void createConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void submitConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void approveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void disapproveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void updateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void deleteConceptEntry(long conceptID, long ownerID, AsyncCallback<String> asyncCallback);

    public void deleteTermEntry(long termID, AsyncCallback<String> asyncCallback);

    public void publishTermEntry(long termID, AsyncCallback<String> asyncCallback);

    public void disapproveTermEntry(long termID, AsyncCallback<String> asyncCallback);
}
