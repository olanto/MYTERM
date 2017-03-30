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
package org.olanto.myterm.print.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.ProcessingInstruction;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermEnum;
import org.olanto.myterm.coredb.entityclasses.Resources;
import static org.olanto.myterm.print.xml.JdomUtilities.*;

/**
 *
 * @author jg
 */
public class PrintXMLFromDB {

    static Element racine;
    static org.jdom2.Document document;

    public static String resourceName;
    static String encoding = "UTF-8";
    static String cssName = "nice.css";
    static String rootName = "nicetbx";
    static Resources resource;


    public static void main(String[] args) {
        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\print\\xml\\print.properties");
    //   doIt(null, "C:\\MYTERM\\nice\\DEMO resource NICE.xml", "TESTTBX","FR", true);

   //  doIt(null, "C:\\MYTERM\\nice\\DEMO resource NICE.xml", "CERN","EN", false);
  
    System.out.println( doIt( "TESTCERN",6765,"FR"));
    }

    public static void init(String propertiesFile) {
        getProperties(propertiesFile);
    }

    public static String  doIt( String _resourceName, long conceptid, String _language) {
        resourceName = _resourceName;
   
//        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\export\\export.properties");
        msg("File Generation XML\n");
        msg("CSS version: " + cssName);
        msg("rootName: " + rootName);
        msg("language: " + _language);

        initRoot(rootName);
        racine = getRacine();
        document = new Document(racine);
        Map<String, String> m = new HashMap<String, String>();
        m.put("href", cssName);
        m.put("type", "text/css");
        document.addContent(0, new ProcessingInstruction("xml-stylesheet", m));
        resource = Queries.getResourceID(resourceName, TermEnum.AutoCreate.NO);
        JdomUtilities.msg("start exporting :");
        racine.addContent(GenerateXMLHeader.setHeader(cssName, rootName));
        racine.addContent(GenerateXMLEntries.getTermsFromDB(_language, conceptid)); 
        return getXML(document);
    }

    public static void getProperties(String fileName) {
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream(fileName));
            //get the property value and print it out
            cssName = prop.getProperty("cssName");
            rootName = prop.getProperty("rootName");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error:" + fileName);
        }
    }
}
