/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simple.myTerm.shared.UserDto;

/**
 *
 * @author simple
 */
public class myTermServlet extends LoginCheckServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //set up response
        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");

        //set up our response
        //if the user is already authenticated => append appropriate module js
        PrintWriter page = res.getWriter();


        // user helper from LoginCheckServlet to get local current_user if it exists
        if (((UserDto) req.getSession().getAttribute("current_user")) == null) {
            //this guy needs to login..
            //respond with the login page (login.js)
            page.append("<html>\n"
                    + "    <head>\n"
                    + "        <meta name='gwt:module' content='simple.myTerm.Login=simple.myTerm.Login'>\n"
                    + "        <title>Terminology Manager</title>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"myTerm.css\">\n"
                    + "        <script type=\"text/javascript\"  src=\"simple.myTerm.Login/simple.myTerm.Login.nocache.js\"></script>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" style=\"width:0;height:0;border:0\"></iframe>\n"
                    + "    </body>\n"
                    + "</html>");
        } else {
            //the user is authenticated
            //respond with the host page (Main.js) for module a
            page.append("<html>\n"
                    + "    <head>\n"
                    + "        <meta name='gwt:module' content='simple.myTerm.Main=simple.myTerm.Main'>\n"
                    + "        <title>Terminology Manager</title>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"myTerm.css\">\n"
                    + "        <script type=\"text/javascript\"  src=\"simple.myTerm.Main/simple.myTerm.Main.nocache.js\"></script>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" style=\"width:0;height:0;border:0\"></iframe>\n"
                    + "    </body>\n"
                    + "</html>");
        }
    }
}
