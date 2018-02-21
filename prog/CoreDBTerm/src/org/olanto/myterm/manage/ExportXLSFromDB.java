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
package org.olanto.myterm.manage;

import javax.swing.JTextArea;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermEnum;
import org.olanto.myterm.coredb.entityclasses.Resources;
import static org.olanto.myterm.export.xml.ExportXMLFromDB.resourceName;
import static org.olanto.myterm.manage.JdomUtilities.*;

/**
 *
 * @author jg
 */
public class ExportXLSFromDB {

    public static String outFileName;
    public static String resourceName;
    static String encoding = "UTF-8";
     static Resources resource;
    public static JTextArea logArea;
    public static OutputStreamWriter result;

    public static void main(String[] args) {
        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\manage\\export.properties");
   
     doIt(null, "C:\\MYTERM\\nice\\MANAGE_XLS.txt", "CERN","FR", true);


    }

    public static void init(String propertiesFile) {
        getProperties(propertiesFile);
    }

    public static void doIt(JTextArea _logArea, String _outFileName, String _resourceName, String _language, boolean show) {
        try {
            resourceName = _resourceName;
            outFileName = _outFileName;
            logArea = _logArea;
           result = new OutputStreamWriter(new FileOutputStream(outFileName), encoding);
           
    //        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\export\\export.properties");
            msg("File Management XLS \n");
            msg("exported file: " + outFileName + "\n");
            resource = Queries.getResourceID(resourceName, TermEnum.AutoCreate.NO);
            result.append(GenerateXLSHeader.setHeader()+"\n");
            GenerateXLSEntries.getTermsFromDB(_language);
            
            result.close();

            msg("\n end of export");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExportXLSFromDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportXLSFromDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportXLSFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void writeFile(String s){
        try {
            result.append(s);
        } catch (IOException ex) {
            Logger.getLogger(ExportXLSFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void getProperties(String fileName) {
        Properties prop = new Properties();

        try {
            //load a properties file

            prop.load(new FileInputStream(fileName));

            //get the property value and print it out

            outFileName = prop.getProperty("outFileName");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error:" + fileName);
        }

    }
}
