/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.AsyncCallback;
import olanto.myTerm.shared.OwnerDTO;

/**
 *
 * @author simple
 */
public interface LoginServiceAsync {

    public void myMethod(String s, AsyncCallback<String> callback);

    public void loginCheck(String name, String password, AsyncCallback<OwnerDTO> callback);

    public void loginFromSessionServer(AsyncCallback<OwnerDTO> callback);

    public void changePassword(String name, String newPassword, AsyncCallback callback);

    public void logout(AsyncCallback callback);
}
