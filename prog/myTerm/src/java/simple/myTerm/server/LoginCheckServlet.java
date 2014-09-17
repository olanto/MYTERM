/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import simple.myTerm.shared.UserDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simple
 */
public class LoginCheckServlet extends RemoteServiceServlet {

    public UserDto getUser() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("current_user") == null) {
            return null;
        }
        // session and userid is available, looks like user is logged in.
        return (UserDto) session.getAttribute("current_user");
    }

    public void setUser(UserDto user) {
         // create session and store userid
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute("current_user", user);
    }
}
