/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.Owners;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.shared.UserDto;

public class LoginServiceImpl extends LoginCheckServlet implements LoginService {
    
    @Override
    public void logout() {

        HttpServletRequest request = this.getThreadLocalRequest();
        // dont create a new one -> false
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        // do some logout stuff ...
        session.invalidate();
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    @Override
    public UserDto isAuthenticated() {
        return getUser(); //helper method from LoginCheckServlet
    }

    @Override
    public UserDto authenticate(String email, String password) {
        //check to see if the email exist in the datastore
        //check passwords
        email = escapeHtml(email);
        password = escapeHtml(password);
        UserDto user = new UserDto();
        Owners o = Queries.getOwner(email, password);
        if (o != null) {
            user.setEmail(o.getOwnerMailing());
            user.setId(o.getIdOwner());
            user.setFirstName(o.getOwnerFirstName());
            user.setLastName(o.getOwnerLastName());
            user.setRole(o.getOwnerRoles());
            //set the current_user for this session
            setUser(user);//see LoginCheckServlet (Parent Class)

        } else {
            return null;//force showLogin
        }
        return user;
    }
}
