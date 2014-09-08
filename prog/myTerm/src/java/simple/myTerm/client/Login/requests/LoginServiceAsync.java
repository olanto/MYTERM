/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Login.requests;

import com.google.gwt.user.client.rpc.AsyncCallback;
import simple.myTerm.shared.UserDto;

/**
 *
 * @author simple
 */
public interface LoginServiceAsync {
	void isAuthenticated(AsyncCallback<UserDto> callback);
	void authenticate(String email, String password, AsyncCallback<UserDto> callback);
	void logout(AsyncCallback callback);
}
