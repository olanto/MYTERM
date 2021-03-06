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
import olanto.myTerm.shared.OwnerDTO;
import olanto.myTerm.shared.ResourceDTO;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.UserLanguageDTO;
import olanto.myTerm.shared.UserResourceDTO;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, ArrayList<Long> resID, String domID, AsyncCallback<String> callback);

    public void getAddResult(String s, String ls, String resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForConcept(long conceptID, long ownerID, AsyncCallback<String> callback);

    public void getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, String interfaceLang, AsyncCallback<String> callback);

    public void getRedactorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> callback);

    public void getRevisorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList, AsyncCallback<ConceptEntryDTO> callback);

    public void getResults(String s, String ls, String lt, long ownerID, AsyncCallback<Collection<String>> callback);

    public void getWorkspaceElements(String ls, long ownerID, AsyncCallback<String> callback);

    public void getApproveElements(String s, String ls, ArrayList<String> lsList, ArrayList<Long> resID, String domID, long ownerID, AsyncCallback<String> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<Collection<LanguageDTO>> asyncCallback);

    public void getDomains(AsyncCallback<Collection<DomainDTO>> asyncCallback);

    public void getResources(AsyncCallback<Collection<ResourceDTO>> asyncCallback);

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

    public void getTermTypes(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getTermPOS(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getTermGender(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getOwnerRoles(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getOwnerStatus(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getResourcePrivacy(String langID, AsyncCallback<Map<String, String>> asyncCallback);

    public void getOwners(AsyncCallback<Collection<OwnerDTO>> asyncCallback);

    public void getOwnerUsage(long ownerID, AsyncCallback<Boolean> asyncCallback);

    public void createUser(OwnerDTO ownerDTO, AsyncCallback<String> asyncCallback);

    public void deleteUser(long ownerID, AsyncCallback<String> asyncCallback);

    public void getOwnerDetails(long ownerID, AsyncCallback<OwnerDTO> asyncCallback);

    public void getOwnersDetails(String ownlerMailing, String ownerStatus, String ownerRole, AsyncCallback<String> asyncCallback);

    public void getDomainsDetails(String domName, AsyncCallback<String> asyncCallback);

    public void getResourcesDetails(String resName, String resPrivacy, AsyncCallback<String> asyncCallback);

    public void getLanguagesDetails(String langID, String langName, AsyncCallback<String> asyncCallback);

    public void getUsersResourcesDetails(long ownerID, long resID, String role, AsyncCallback<String> asyncCallback);

    public void getUsersLanguagesDetails(long ownerID, String langID, AsyncCallback<String> asyncCallback);

    public void getEntitiesDetails(String s, String langID, long resID, String domID, AsyncCallback<String> asyncCallback);

    public void AdminUpdateUser(OwnerDTO ownerDTO, AsyncCallback<OwnerDTO> asyncCallback);

    public void AdminSaveUser(OwnerDTO ownerDTO, AsyncCallback<String> asyncCallback);

    public void getResourceUsage(long resID, AsyncCallback<Boolean> asyncCallback);

    public void createResource(ResourceDTO resourceDTO, AsyncCallback<String> asyncCallback);

    public void deleteResource(long resID, AsyncCallback<String> asyncCallback);

    public void AdminUpdateResource(ResourceDTO resourceDTO, AsyncCallback<ResourceDTO> asyncCallback);

    public void AdminSaveResource(ResourceDTO resourceDTO, AsyncCallback<String> asyncCallback);

    public void getResourceDetails(long resID, AsyncCallback<ResourceDTO> asyncCallback);

    public void getLanguageDetails(String langID, AsyncCallback<LanguageDTO> asyncCallback);

    public void getLanguageUsage(String langID, AsyncCallback<Boolean> asyncCallback);

    public void createLanguage(LanguageDTO langDTO, AsyncCallback<String> asyncCallback);

    public void deleteLanguage(String langID, AsyncCallback<String> asyncCallback);

    public void AdminUpdateLanguage(LanguageDTO langDTO, AsyncCallback<LanguageDTO> asyncCallback);

    public void AdminSaveLanguage(LanguageDTO langDTO, AsyncCallback<String> asyncCallback);

    public void getDomainDetails(long domID, AsyncCallback<DomainDTO> asyncCallback);

    public void getUsersResourcesDetails(long id, AsyncCallback<String> asyncCallback);

    public void getUserResource(long id, AsyncCallback<UserResourceDTO> asyncCallback);

    public void createUserResource(UserResourceDTO userResource, AsyncCallback<String> asyncCallback);

    public void deleteUserResource(long id, AsyncCallback<String> asyncCallback);

    public void AdminUpdateUserResource(UserResourceDTO userResource, AsyncCallback<UserResourceDTO> asyncCallback);

    public void AdminSaveUserResource(UserResourceDTO userResource, AsyncCallback<String> asyncCallback);

    public void getUsersLanguagesDetails(long id, AsyncCallback<String> asyncCallback);

    public void getUserLanguage(long id, AsyncCallback<UserLanguageDTO> asyncCallback);

    public void createUserLanguage(UserLanguageDTO userLanguage, AsyncCallback<String> asyncCallback);

    public void deleteUserLanguage(long id, AsyncCallback<String> asyncCallback);

    public void AdminUpdateUserLanguage(UserLanguageDTO userLanguage, AsyncCallback<UserLanguageDTO> asyncCallback);

    public void AdminSaveUserLanguage(UserLanguageDTO userLanguage, AsyncCallback<String> asyncCallback);

    public void getDomainUsage(long domID, AsyncCallback<Boolean> asyncCallback);

    public void createDomain(DomainDTO domain, AsyncCallback<String> asyncCallback);

    public void deleteDomain(long domID, AsyncCallback<String> asyncCallback);

    public void AdminUpdateDomain(DomainDTO domain, AsyncCallback<DomainDTO> asyncCallback);

    public void AdminSaveDomain(DomainDTO domain, AsyncCallback<String> asyncCallback);

    public void printConceptEntry(String resourceName, long conceptid, String language, AsyncCallback<Boolean> asyncCallback);
    
    public void printConceptEntry4RR(String resourceName, long conceptid, String language, AsyncCallback<Boolean> asyncCallback);
}
