/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import simple.myTerm.shared.UserDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 *
 * @author simple
 */
public class LoginCheckServlet extends RemoteServiceServlet {

    public UserDto getUser() {
        return (UserDto) getThreadLocalRequest().getSession().getAttribute("current_user");
    }

    public void setUser(UserDto user) {
        getThreadLocalRequest().getSession().setAttribute("current_user", user);
    }
}
