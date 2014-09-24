/**********
    Copyright © 2013-2014 Olanto Foundation Geneva

   This file is part of myTERM.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/
package org.olanto.myterm.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/** *
 * @author JG
 */
public class Sha1Utils {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
              String content = "xxxnicolas1";
        try {
            System.out.println(content + " = " + sha1(content));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Sha1Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sha1Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    public static String sha1(final File file) throws NoSuchAlgorithmException, IOException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            final byte[] buffer = new byte[1024];
            for (int read = 0; (read = is.read(buffer)) != -1;) {
                messageDigest.update(buffer, 0, read);
            }
        }

        return sha12String(messageDigest);

    }

    public static String sha1(String content) throws NoSuchAlgorithmException, IOException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

        try (InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"))) {
            final byte[] buffer = new byte[1024];
            for (int read = 0; (read = is.read(buffer)) != -1;) {
                messageDigest.update(buffer, 0, read);
            }
        }

        return sha12String(messageDigest);

    }

    public static String sha12String(MessageDigest messageDigest) {
        // Conversion des bytes en   format hex
        try (
                Formatter formatter = new Formatter()) {
            for (final byte b : messageDigest.digest()) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }
}
