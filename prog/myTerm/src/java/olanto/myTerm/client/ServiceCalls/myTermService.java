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

    public String getAddResult(String s, String ls, String lt, String resID, String domID, long ownerID);

    public ArrayList<String> getResults(String s, String ls, String lt, long ownerID);

    public ArrayList<LanguageDTO> getLanguages();

    public ArrayList<DomainDTO> getDomains();

    public ArrayList<ResourceDTO> getResources(long ownerID);

    public String getInventory();
}
