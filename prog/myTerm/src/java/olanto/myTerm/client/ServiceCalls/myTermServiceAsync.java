/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.ResourceDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, ArrayList<Long> resID, String domID, AsyncCallback<String> callback);

    public void getAddResult(String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForConcept(long conceptID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, AsyncCallback<String> callback);

    public void getRedactorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> callback);

    public void getRevisorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> callback);

    public void getResults(String s, String ls, String lt, long ownerID, AsyncCallback<Collection<String>> callback);

    public void getWorkspaceElements(String ls, long ownerID, AsyncCallback<String> callback);

    public void getApproveElements(String s, String ls, ArrayList<String> lsList, ArrayList<Long> resID,  String domID, long ownerID, AsyncCallback<String> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<Collection<LanguageDTO>> asyncCallback);

    public void getDomains(AsyncCallback<Collection<DomainDTO>> asyncCallback);

    public void getResourcesByOwner(String ownerMailing, String role, AsyncCallback<Collection<ResourceDTO>> asyncCallback);

    public void getLanguagesByOwner(long ownerID, AsyncCallback<Collection<LanguageDTO>> asyncCallback);

    public void createConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void submitConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void approveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void disapproveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void RedactorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> asyncCallback);

    public void RedactorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void RevisorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> asyncCallback);

    public void RevisorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, AsyncCallback<String> asyncCallback);

    public void deleteConceptEntry(long conceptID, long ownerID, AsyncCallback<String> asyncCallback);

    public void deleteTermEntry(long termID, AsyncCallback<String> asyncCallback);

    public void publishTermEntry(long termID, AsyncCallback<String> asyncCallback);

    public void disapproveTermEntry(long termID, AsyncCallback<String> asyncCallback);

    public void getSysFieldsByLang(String langID, AsyncCallback<Map<String, SysFieldDTO>> asyncCallback);

    public void getSysMsgByLang(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getTermTypes(String langID, AsyncCallback<Collection<String>> asyncCallback);

    public void getTermPOS(String langID, AsyncCallback<Collection<String>> asyncCallback);

    public void getTermGender(String langID, AsyncCallback<Collection<String>> asyncCallback);
}
