/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@RemoteServiceRelativePath("mytermservice")
public interface myTermService extends RemoteService {

    public String getdetailsForConcept(long conceptID, long ownerID);

    public String getdetailsForTerms(long conceptID, String langS, String langT, long ownerID, String interfaceLang);

    public String getSearchResult(String s, String ls, String lt, ArrayList<Long> resID, String domID);

    public ConceptEntryDTO getRedactorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList);

    public ConceptEntryDTO getRevisorDetailsForConcept(long conceptID, long ownerID, ArrayList<String> lsList);

    public String getAddResult(String s, String ls, String resID, String domID, long ownerID);

    public String getWorkspaceElements(String ls, long ownerID);

    public String getApproveElements(String s, String ls, ArrayList<String> lsList, ArrayList<Long> resID, String domID, long ownerID);

    public Collection<String> getResults(String s, String ls, String lt, long ownerID);

    public Collection<LanguageDTO> getLanguages();

    public Collection<DomainDTO> getDomains();

    public Collection<ResourceDTO> getResources();

    public Collection<ResourceDTO> getResourcesByOwner(String ownerMailing, String role);

    public Collection<LanguageDTO> getLanguagesByOwner(long ownerID);

    public String getInventory();

    public String createConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String submitConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String approveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String disapproveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public ConceptEntryDTO RedactorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList);

    public String RedactorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public ConceptEntryDTO RevisorUpdateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID, ArrayList<String> lsList);

    public String RevisorSaveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String deleteConceptEntry(long conceptID, long ownerID);

    public String deleteTermEntry(long termID);

    public String publishTermEntry(long termID);

    public String disapproveTermEntry(long termID);

    public Map<String, SysFieldDTO> getSysFieldsByLang(String langID);

    public Map<String, String> getSysMsgByLang(String langID);

    public Map<String, String> getTermTypes(String langID);

    public Map<String, String> getTermPOS(String langID);

    public Map<String, String> getTermGender(String langID);

    public Map<String, String> getOwnerRoles(String langID);

    public Map<String, String> getOwnerStatus(String langID);

    public Map<String, String> getResourcePrivacy(String langID);

    public OwnerDTO getOwnerDetails(long ownerID);

    public String getOwnersDetails(String ownlerMailing, String ownerStatus, String ownerRole);

    public String getDomainsDetails(String domName);

    public String getResourcesDetails(String resName, String resPrivacy);

    public String getLanguagesDetails(String langID, String langName);

    public String getUsersResourcesDetails(long ownerID, long resID, String role);

    public String getUsersResourcesDetails(long id);

    public UserResourceDTO getUserResource(long id);

    public String getUsersLanguagesDetails(long ownerID, String langID);

    public String getUsersLanguagesDetails(long id);

    public UserLanguageDTO getUserLanguage(long id);

    public String getEntitiesDetails(String s, String langID, long resID, String domID);

    public Collection<OwnerDTO> getOwners();

    public Boolean getOwnerUsage(long ownerID);

    public String createUser(OwnerDTO ownerDTO);

    public String deleteUser(long ownerID);

    public OwnerDTO AdminUpdateUser(OwnerDTO ownerDTO);

    public String AdminSaveUser(OwnerDTO ownerDTO);

    public ResourceDTO getResourceDetails(long resID);

    public Boolean getResourceUsage(long resID);

    public LanguageDTO getLanguageDetails(String LangID);

    public Boolean getLanguageUsage(String langID);

    public String createLanguage(LanguageDTO langDTO);

    public String deleteLanguage(String langID);

    public LanguageDTO AdminUpdateLanguage(LanguageDTO langDTO);

    public String AdminSaveLanguage(LanguageDTO langDTO);

    public String createResource(ResourceDTO resourceDTO);

    public String deleteResource(long resID);

    public ResourceDTO AdminUpdateResource(ResourceDTO resourceDTO);

    public String AdminSaveResource(ResourceDTO resourceDTO);

    public String createUserResource(UserResourceDTO userResource);

    public String deleteUserResource(long id);

    public UserResourceDTO AdminUpdateUserResource(UserResourceDTO userResource);

    public String AdminSaveUserResource(UserResourceDTO userResource);

    public String createUserLanguage(UserLanguageDTO userLanguage);

    public String deleteUserLanguage(long id);

    public UserLanguageDTO AdminUpdateUserLanguage(UserLanguageDTO userLanguage);

    public String AdminSaveUserLanguage(UserLanguageDTO userLanguage);

    public DomainDTO getDomainDetails(long domID);

    public Boolean getDomainUsage(long domID);

    public String createDomain(DomainDTO domain);

    public String deleteDomain(long domID);

    public DomainDTO AdminUpdateDomain(DomainDTO domain);

    public String AdminSaveDomain(DomainDTO domain);

    public Boolean printConceptEntry(String resourceName, long conceptid, String language);
    
   public Boolean printConceptEntry4RR(String resourceName, long conceptid, String language);
}
