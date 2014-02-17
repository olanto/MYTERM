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
package org.olanto.myterm.parsetbx;

import java.io.*;
import java.util.List;
import java.util.Iterator;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.entityclasses.Resources;

/**
 *
 * @author jg
 */
public class XDXF_Loader implements Loader {

    static org.jdom2.Document document;
    static long totEntries;
    static String resourceName;
    static Entry courantEntry;
    static Namespace xmlNS = Namespace.XML_NAMESPACE;
    static Namespace noNS = Namespace.NO_NAMESPACE;
    static boolean skipverbose = true;
    static String solang = "";
    static String talang = "";
    static Resources resource;
    static long timer=System.currentTimeMillis();

    /* filename with extension _fr_en */
    public void loadAFileIntoTBXDB(String fileName, String _resourceName) {
        resourceName = _resourceName;
        totEntries = 0;
        processAFile(fileName);
        System.out.println(fileName + "; " + totEntries + "; ");

    }

    static void processAFile(String fileName) {
        //On crï¿½e une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crï¿½e un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminï¿½ ;)
            document = sxb.build(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element racine = document.getRootElement();
        // System.out.println("XML is open, now process it ...");
        totEntries = 0;
        afficheALL(racine);
    }

    static String getAtt(Element e, String att, Namespace nameSpace, boolean localverbose) {
        String val = e.getAttributeValue(att, nameSpace);
        if (localverbose) {
            System.out.println(nameSpace + att + "=" + val);
        }
        return val;
    }

    static String getText(Element e, boolean localverbose) {
        String val = e.getTextNormalize();
        if (localverbose) {
            System.out.println("--> " + val);
        }
        return val;
    }

    static String getTermEntry(Element e) {
        /*
         <ar><k>Abkhasie</k>Abkhazia</ar>
         */
        boolean localverbose = false;
        courantEntry = new Entry(resource, true);
        courantEntry.setExtraConcepts("");
        totEntries++;
        if (totEntries % 100 == 0) {
            timer=System.currentTimeMillis()-timer;
            System.out.println("processed:" + totEntries+", "+timer+" [ms]");
            timer=System.currentTimeMillis();
        }
        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        courantEntry.addConcept();
        courantEntry.addLangSet(talang);
        courantEntry.prepareTerm(getText(e, localverbose));
        courantEntry.addTerm();

        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("k")) {
                courantEntry.addLangSet(talang);
                courantEntry.prepareTerm(getText(info, localverbose));
                courantEntry.addTerm();
            } else {
                String extra = getExtraElement(info);
                //courantEntry.setExtraConcepts(courantEntry.getExtraConcepts() + extra + "\n");
                if (skipverbose) {
                    System.out.println("--skip element:" + info.getName());
                    System.out.println(extra);
                }
            }
        }

        return "";
    }

    static String getExtraElement(Element e) {
        StringBuilder collect = new StringBuilder();
        boolean attributeverbose = false;
        collect.append("<" + e.getName());
        List<Attribute> attlist = e.getAttributes();
        Iterator i = attlist.iterator();
        while (i.hasNext()) {
            Attribute att = (Attribute) i.next();
            String av = att.getName() + "=\"" + getAtt(e, att.getName(), noNS, attributeverbose) + "\"";
            collect.append(" " + av);
            //System.out.println(av);
        }
        collect.append(">");
        List listNode = e.getChildren();
        i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            collect.append("\n");
            collect.append(getExtraElement(info));
        }
        //System.out.println(e.getTextTrim());
        collect.append(e.getTextNormalize());
        collect.append("</" + e.getName() + ">");
        String res = collect.toString();
        res = res.replace("><", ">\n<");
        res = res.replace("  ", " ");
        //System.out.println(res);      
        return res;
    }

    static String getLanguages(Element e) {
        boolean localverbose = false;
        boolean attributeverbose = false;

        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        solang = LanguageCode.getISO2From3(getAtt(e, "lang_from", noNS, attributeverbose));
        talang = LanguageCode.getISO2From3(getAtt(e, "lang_to", noNS, attributeverbose));

        System.out.println("from: " + solang + " to: " + talang);

        return "";
    }

    static void afficheALL(Element element) {
        boolean localverbose = false;
        if (localverbose) {
            System.out.println("--- process from root:" + element.getName());
        }
        getLanguages(element);
        List listNode = element.getChildren();

        // on récupère les indications du header
        String extra = "";
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            if (courant.getName().equals("ar")) {
                ;
            } else {
                extra = extra + getExtraElement(courant) + "\n";
                if (skipverbose) {
                    System.out.println("--skip element from header:" + courant.getName());
                    System.out.println(extra);
                }
            }
        }
        resource = ManageResource.create(resourceName, "PUBLIC", "???", extra);
        i = listNode.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            if (courant.getName().equals("ar")) {
                getTermEntry(courant);
            }
        }

    }
}
