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
package org.olanto.myterm.extractor.model;

import java.io.*;
import java.util.List;
import java.util.Iterator;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author jg
 */
public class LoadModelPrefix {

    static org.jdom2.Document document;
    //   static long totEntries;
    static Namespace xmlNS = Namespace.XML_NAMESPACE;
    static Namespace noNS = Namespace.NO_NAMESPACE;
    static boolean skipverbose = true;
    static ModelPrefix model;
    static long timer = System.currentTimeMillis();

    public static ModelPrefix loadAFileIntoModel(String fileName) {
        model = new ModelPrefix();
        processAFile(fileName);
        System.out.println("-------------- " + fileName);
        model.dump();
        return model;
    }

    static void processAFile(String fileName) {
        //On créée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On créée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            document = sxb.build(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element racine = document.getRootElement();
        // System.out.println("XML is open, now process it ...");
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

    static String getLanguage(Element e) {
        boolean localverbose = false;
        if (localverbose) {
            System.out.println("--- process from getLanguage:" + e.getName());
        }
        String lang="??";
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("code")) {
                lang=getText(info, localverbose);
            } else if (info.getName().equals("prefix")) {
                model.addPrefix(getText(info, localverbose), lang);
            } 
            else {
                String extra = getExtraElement(info);
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

    static void afficheALL(Element element) {
        boolean localverbose = false;
        if (localverbose) {
            System.out.println("--- process from root:" + element.getName());
        }
        List listNode = element.getChildren();

        String extra = "";
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
             if (courant.getName().equals("separator")) {
                model.setSeparator(getText(courant, localverbose));
            } else if (courant.getName().equals("specialLine")) {
                model.setSpecialLine(getText(courant, localverbose));
            } else if (courant.getName().equals("language")) {
                getLanguage(courant);
            } else {
                extra = extra + getExtraElement(courant) + "\n";
                if (skipverbose) {
                    System.out.println("--skip element from header:" + courant.getName());
                    System.out.println(extra);
                }
            }
        }

    }
}
