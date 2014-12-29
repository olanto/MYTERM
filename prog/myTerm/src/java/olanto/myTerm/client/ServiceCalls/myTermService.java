/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.ResourceDTO;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("mytermservice")
public interface myTermService extends RemoteService {

    public String getdetailsForConcept(long conceptID, long ownerID);

    public String getdetailsForTerms(long conceptID, String langS, String langT, long ownerID);

    public String getSearchResult(String s, String ls, String lt, String resID, String domID, long ownerID);

    public ConceptEntryDTO getAddDetailsForConcept(long conceptID, long ownerID);

    public String getAddResult(String s, String ls, String resID, String domID, long ownerID);

    public String getApproveResult(String s, String ls, String resID, String domID, long ownerID);

    public String getWorkspaceElements(String ls, long ownerID);

    public String getApproveElements(String ls, long ownerID);

    public ArrayList<String> getResults(String s, String ls, String lt, long ownerID);

    public ArrayList<LanguageDTO> getLanguages();

    public ArrayList<DomainDTO> getDomains();

    public ArrayList<ResourceDTO> getResourcesByOwner(long ownerID);

    public ArrayList<LanguageDTO> getLanguagesByOwner(long ownerID);

    public String getInventory();

    public String createConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String submitConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String approveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String disapproveConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String updateConceptEntry(ConceptEntryDTO conceptEntryDTO, long ownerID);

    public String deleteConceptEntry(long conceptID, long ownerID);

    public String deleteTermEntry(long termID);

    public String publishTermEntry(long termID);

    public String disapproveTermEntry(long termID);
}
