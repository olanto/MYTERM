/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
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
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package org.olanto.myterm.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.olanto.myterm.util.Sha1Utils.sha1;

class SecurePassword {

    public static void main(String[] args) {
        String pwd = "password";
        String hash = encryptPassword(pwd);
        System.out.println(" pwd= " + pwd + " hash= " + hash);
        System.out.println("test =" + checkPassword(pwd, hash) + " pwd= " + pwd + " hash= " + hash);
        pwd = "PASSWORD";
        System.out.println("test =" + checkPassword(pwd, hash) + " pwd= " + pwd + " hash= " + hash);


    }

    public static String encryptPassword(String pwd) {
        try {
            return sha1(pwd);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurePassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecurePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }

    public static boolean checkPassword(String pwd, String hashpwd) {
        try {
            return sha1(pwd).equals(hashpwd);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurePassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecurePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
