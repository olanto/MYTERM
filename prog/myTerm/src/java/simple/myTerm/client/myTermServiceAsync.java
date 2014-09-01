/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

/**
 *
 * @author simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, AsyncCallback<String> callback);
    public void getResults(String s, String ls, String lt, AsyncCallback<ArrayList<String>> callback);
    public void getInventory(AsyncCallback<String> callback);
}
