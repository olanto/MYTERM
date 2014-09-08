/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.shared.UserDto;

public class LoginServiceImpl extends LoginCheckServlet implements LoginService {

    @Override
    public void logout() {
        setUser(null);
    }

    @Override
    public UserDto isAuthenticated() {
        return getUser(); //helper method from LoginCheckServlet
    }

    @Override
    public UserDto authenticate(String email, String password) {
        //check to see if the email exist in the datastore
        //check passwords
        // *** Test Code ****
        UserDto user = new UserDto();
        if (email.contains("go")) {
            user.setEmail(email);
            user.setId(12);
            user.setName("NameFromDb");

            //set the current_user for this session
            setUser(user);//see LoginCheckServlet (Parent Class)

        } else {
            user = null;//force showLogin
        }

        return user;
    }
}
