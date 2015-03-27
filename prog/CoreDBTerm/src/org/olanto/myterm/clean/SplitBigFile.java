/**
 * ********
 * Copyright © 2010-2012 Olanto Foundation Geneva
 *
 * This file is part of myMT.
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
package org.olanto.myterm.clean;

import java.io.*;
import java.lang.reflect.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * remplacement d'une chaine dans tous les fichiers d'un folder
 *
 */
public class SplitBigFile {

    static String DOCEXT, root;
    static int countLine = 0;
    static char x03 = 0x03;
    static char x04 = 0x04;
    static final String oldStr = "" + x04;
    static final String newStr = " ";

    public static void main(String[] args) {
        DOCEXT = ".tbx";
        root = "C:/MYTERM/tests/IATE/";
        System.out.println("start replacement for " + oldStr);
          processAFile(root, "IATE_export_27012015");
      //  processAFile(root, "export_EN_FR");

    }

    public static void processAFile(String root, String tbxname) {
        String corpusSO = root + tbxname + ".tbx";
        int entryCount = 0;
        int splitcount = 1;
        String corpusSOCOPY = root + tbxname + "-" + splitcount + ".tbx";
        try {
            countLine = 0;
            System.out.println("open :" + corpusSO);
            BufferedReader inSO = new BufferedReader(new InputStreamReader(new FileInputStream(corpusSO), "UTF-8"));
            OutputStreamWriter outSO = new OutputStreamWriter(new FileOutputStream(corpusSOCOPY), "UTF-8");
            outSO.append(header());
            String wSO = inSO.readLine();  // tous le temps ensemble pour garder la synchro
            boolean copy = false;
            while (wSO != null) {
                countLine++;
                if (wSO.contains("<termEntry")) {
                    entryCount++;
                    copy = true;
                }
                if (wSO.contains("<descrip type=\"reliabilityCode\">")) {
                   wSO= wSO.replace("<descrip type=\"reliabilityCode\">","<note>");
                   wSO= wSO.replace("</descrip>","</note>");
                   wSO= wSO.replace(">1<",">1 - Reliability not verified<");
                   wSO= wSO.replace(">2<",">2 - Minimum reliability<");
                   wSO= wSO.replace(">3<",">3 - Reliable<");
                   wSO= wSO.replace(">4<",">4 - Very reliable <");
                }
                
              if (wSO.contains("<descrip type=\"subjectField\">")) {
                   String s=wSO;
                   s= s.replace("subjectField","iateCode");
                   outSO.append(s + "\n");
                }
                 if (copy) {
                    outSO.append(wSO + "\n");
                }
                if (wSO.contains("</termEntry>")) {
                   // System.out.println(entryCount);
                    if (entryCount %100000 == 0) {
                        System.out.println("new file:"+entryCount);
                       outSO.append(footer());
                        outSO.close();
                        splitcount++;
                        corpusSOCOPY = root + tbxname + "-" + splitcount + ".tbx";
                        outSO = new OutputStreamWriter(new FileOutputStream(corpusSOCOPY), "UTF-8");
                        outSO.append(header());
                    }
                }
                wSO = inSO.readLine();
            }
            inSO.close();
            outSO.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("tot:" + countLine);
        System.out.println("entryCount:" + entryCount);
    }

    static String header() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"
                + "<martif type=\"TBX-Default\" xml:lang=\"en\">" + "\n"
                + "<martifHeader>" + "\n"
                + "<fileDesc>" + "\n"
                + "<sourceDesc>" + "\n"
                + "<p>This is a TBX file downloaded from the IATE website. Address any enquiries to iate@cdt.europa.eu.</p>" + "\n"
                + "</sourceDesc>" + "\n"
                + "</fileDesc>" + "\n"
                + "<encodingDesc>" + "\n"
                + "<p type=\"XCSURI\">TBXXCS.xcs</p>" + "\n"
                + "</encodingDesc>" + "\n"
                + "</martifHeader>" + "\n"
                + "<text>" + "\n"
                + " <body>" + "\n";
    }

    static String footer() {
        return "</body>\n"
                + "</text>\n"
                + "</martif>\n";
    }
}
