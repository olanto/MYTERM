/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import olanto.myTerm.client.LoginService;
import olanto.myTerm.shared.UserDTO;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Owners;

/**
 *
 * @author simple
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    @Override
    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }

    @Override
    public UserDTO loginCheck(String name, String password) {
        //check to see if the email exist in the datastore
        //check passwords
        name = escapeHtml(name);
        password = escapeHtml(password);
        UserDTO user = new UserDTO();
        Owners o = Queries.getOwner(name, password);
        if (o != null) {
            user.setEmail(o.getOwnerMailing());
            user.setId(o.getIdOwner());
            user.setSessionID(o.getIdOwner().toString());
            user.setFirstName(o.getOwnerFirstName());
            user.setLastName(o.getOwnerLastName());
            user.setRole(o.getOwnerRoles());
            //set the current_user for this session
            storeUserInSession(user);
        } else {
            return null;//force showLogin
        }
        return user;
    }

    @Override
    public UserDTO loginFromSessionServer() {
        return getUserAlreadyFromSession();
    }

    @Override
    public void logout() {
        deleteUserFromSession();
    }

    @Override
    public boolean changePassword(String name, String newPassword) {
        return false;
    }

    private UserDTO getUserAlreadyFromSession() {
        UserDTO user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof UserDTO) {
            user = (UserDTO) userObj;
        }
        return user;
    }

    private void storeUserInSession(UserDTO user) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }

    private void deleteUserFromSession() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        session.invalidate();
    }

    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
