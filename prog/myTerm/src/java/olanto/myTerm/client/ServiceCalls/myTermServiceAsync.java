/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import olanto.myTerm.client.Langs.Language;
import olanto.myTerm.client.Resources.Resource;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, AsyncCallback<String> callback);

    public void getdetailsForConcept(long concept, AsyncCallback<String> callback);
    
    public void getdetailsForTerms(long concept, String langS, String langT, AsyncCallback<String> callback);

    public void getResults(String s, String ls, String lt, AsyncCallback<ArrayList<String>> callback);

    public void getInventory(AsyncCallback<String> callback);

    public void getLanguages(AsyncCallback<ArrayList<Language>> asyncCallback);

    public void getResources(AsyncCallback<ArrayList<Resource>> asyncCallback);
}
