/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Login.requests;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import simple.myTerm.shared.UserDto;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService{
	UserDto isAuthenticated();
	UserDto authenticate(String email, String password);
	void logout();
}
