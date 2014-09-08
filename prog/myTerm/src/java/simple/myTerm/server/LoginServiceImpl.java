/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.shared.UserDto;

public class LoginServiceImpl extends LoginCheckServlet implements LoginService {

    public String login(String name, String password) throws IllegalArgumentException {
        // Verify that the input is valid.
        if (name.length() < 4) {
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }

        // Escape data from the client to avoid cross-site script
        // vulnerabilities.
        name = escapeHtml(name);
        password = escapeHtml(password);

        if (!"test".equals(name) || !"testpw".equals(password)) {
            throw new IllegalArgumentException("Nick/Password is not matching");
        }

        // create session and store userid
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute("UserID", 1);
        return session.getId();
    }

    public boolean checkLogin() {

        HttpServletRequest request = this.getThreadLocalRequest();
        // dont create a new one -> false
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UserID") == null) {
            return false;
        }
        // session and userid is available, looks like user is logged in.
        return true;
    }

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
        // *** Test Code ****
        UserDto user = new UserDto();
        if (email.contains("inputter")) {
            user.setEmail(email);
            user.setId(12);
            user.setName("NameFromDb");
            user.setRole("inputter");
            //set the current_user for this session
            setUser(user);//see LoginCheckServlet (Parent Class)

        } else if (email.contains("public")) {
            user.setEmail(email);
            user.setId(13);
            user.setName("anyway");
            user.setRole("public");
            //set the current_user for this session
            setUser(user);//see LoginCheckServlet (Parent Class)

        } else {
            user = null;//force showLogin
        }

        return user;
    }
}
