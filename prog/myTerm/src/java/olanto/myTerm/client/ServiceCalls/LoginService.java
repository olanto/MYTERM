/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ServiceCalls;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import olanto.myTerm.shared.UserDTO;

/**
 *
 * @author simple
 */
@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

    public String myMethod(String s);

    UserDTO loginCheck(String name, String password);

    UserDTO loginFromSessionServer();

    boolean changePassword(String name, String newPassword);

    void logout();
}
