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
package olanto.myTerm.client.CookiesManager;

import com.google.gwt.user.client.Cookies;
import java.util.Date;
import olanto.myTerm.client.ConstantsManager.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class MyTermCookies {

    public MyTermCookies() {
    }

    public static void initCookie(String name, String value) {
        Date expires = new Date(System.currentTimeMillis() + (1000L * 3600L * 24L * (long) GuiConstant.EXP_DAYS));
        if ((Cookies.getCookie(name) == null) || (Cookies.getCookie(name).equalsIgnoreCase("null"))) {
            Cookies.setCookie(name, value, expires);
        }
    }

    public static void updateCookie(String name, String value) {
        Date expires = new Date(System.currentTimeMillis() + (1000L * 3600L * 24L * (long) GuiConstant.EXP_DAYS));
        Cookies.removeCookie(name);
        Cookies.setCookie(name, value, expires , null, "/", false);
    }
}
