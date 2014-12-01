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
package org.olanto.myterm.export;

import javax.swing.JTextArea;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermEnum;
import org.olanto.myterm.coredb.entityclasses.Resources;
import static org.olanto.myterm.export.JdomUtilities.*;

/**
 *
 * @author jg
 */
public class ExportTBXFromDB {

    static Element racine;
    static org.jdom2.Document document;
    public static String outFileName;
    public static String resourceName;
    static String encoding = "UTF-8";
    static String dtdName="TBXBasiccoreStructV02.dtd";
    static String rootName="martif";
    static Resources resource;
    public static JTextArea logArea;

    public static void main(String[] args) {
        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\export\\export.properties");
        doIt(null, "C:\\MYTERM\\tests\\exportTBX.xml", "TESTTBX",true);
    }

    public static void init(String propertiesFile) {
        getProperties(propertiesFile);
    }

    public static void doIt(JTextArea _logArea, String _outFileName, String _resourceName, boolean show) {
        resourceName = _resourceName;
        outFileName = _outFileName;
        logArea = _logArea;
//        init("C:\\MYTERM\\prog\\CoreDBTerm\\src\\org\\olanto\\myterm\\export\\export.properties");
        msg("File Generation TBX\n");
        msg("TBX version: " + dtdName);
        msg("rootName: " + rootName);
        msg("exported file: " + outFileName + "\n");
         initRoot(rootName);
        racine = getRacine();
        DocType doctype = new DocType("TBXBasiccoreStructV02.dtd");
        document = new Document(racine, doctype);
        resource = Queries.getResourceID(resourceName, TermEnum.AutoCreate.NO);
        JdomUtilities.msg("start exporting :");
        racine.addContent(GenerateTBXHeader.setHeader(dtdName, rootName));
        racine.addContent(GenerateEntries.getTermsFromDB());


        if (show)showXML(document);
        saveXML(document, outFileName);
        msg("\n end of export");

    }

    public static void getProperties(String fileName) {
        Properties prop = new Properties();

        try {
            //load a properties file

            prop.load(new FileInputStream(fileName));

            //get the property value and print it out
            dtdName = prop.getProperty("dtdName");
            rootName = prop.getProperty("rootName");
            outFileName = prop.getProperty("outFileName");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error:" + fileName);
        }

    }
}
