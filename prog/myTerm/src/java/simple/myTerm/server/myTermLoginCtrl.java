/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
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
public class myTermLoginCtrl extends LoginCheckServlet {

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
            //respond with the host page (Main.js) for module main
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
                    + "    <div id = \"header\"></div>"
                    + "    <div id = \"main\"></div>"
                    + "    <div id = \"footer\"></div>"
                    + "</body>\n"
                    + "</html>");
        }
    }
}
