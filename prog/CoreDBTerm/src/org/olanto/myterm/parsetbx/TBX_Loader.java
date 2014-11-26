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
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermEnum;
import org.olanto.myterm.coredb.entityclasses.Resources;

/**
 *
 * @author jg
 *
 * compatible with TBXBasiccoreStructV02.dtd compatible with
 * TBXcoreStructV02.dtd
 */
public class TBX_Loader implements Loader {

    static org.jdom2.Document document;
    static long totEntries;
    static String resourceName;
    static Entry courantEntry;
    static Pattern p = Pattern.compile("[\\s]");  // les blancs
    static Namespace xmlNS = Namespace.XML_NAMESPACE;
    static Namespace noNS = Namespace.NO_NAMESPACE;
    static boolean skipverbose = true;
    static Resources resource;
    static SimpleDateFormat convertDate = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");

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

    static String getTig(Element e) {
        boolean localverbose = true;
        boolean termFormExist = false;
        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        courantEntry.setExtraTerms("");
        courantEntry.setTermNote("");
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("term")) {
                courantEntry.prepareTerm(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("termNote")
                    && info.getAttributeValue("type").equals("partOfSpeech")) {
                courantEntry.getTerm().setTermPartofspeech(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("termNote")
                    && info.getAttributeValue("type").equals("administrativeStatus")) {
                courantEntry.getTerm().setTermAdminStatus(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("termNote")
                    && info.getAttributeValue("type").equals("grammaticalGender")) {
                courantEntry.getTerm().setTermGender(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("termNote")
                    && info.getAttributeValue("type").equals("termType")) {
                courantEntry.getTerm().setTermType(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("termNote")
                    && info.getAttributeValue("type").equals("geographicalUsage")) {
                courantEntry.getTerm().setTermGeoUsage(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("descrip")
                    && info.getAttributeValue("type").equals("context")) {
                courantEntry.getTerm().setTermContext(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("descrip")
                    && info.getAttributeValue("type").equals("definition")) {
                courantEntry.getTerm().setTermDefinition(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("admin")
                    && info.getAttributeValue("type").equals("source")) {
                courantEntry.getTerm().setTermSource(getText(info, localverbose));
                termFormExist = true;
            } else if (info.getName().equals("note")) {
                courantEntry.setTermNote(courantEntry.getTermNote() + getText(info, localverbose) + "\n");
                termFormExist = true;
            } else {
                String extra = getExtraElement(info);
                courantEntry.setExtraTerms(courantEntry.getExtraTerms() + extra + "\n");
                if (skipverbose) {
                    System.out.println("--skip element:" + info.getName());
                    System.out.println(extra);
                }
            }
        }
        if (termFormExist) {
            courantEntry.addTerm();
        } else {
            System.out.println("ERROR element in <tig> element no formTerm");
        }
        return "";
    }

    static String getLangSet(Element e) {
        boolean localverbose = true;
        boolean attributeverbose = false;

        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        courantEntry.setExtraLangsets("");
        courantEntry.setLangsetNote("");
        courantEntry.addLangSet(getAtt(e, "lang", xmlNS, attributeverbose));
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("tig")) {
                getTig(info);
            } else if (info.getName().equals("descrip")) {
                courantEntry.setLangsetNote(courantEntry.getLangsetNote() + getText(info, localverbose) + "\n");
                courantEntry.addNoteLangSet();
            } else {
                String extra = getExtraElement(info);
                courantEntry.setExtraLangsets(courantEntry.getExtraLangsets() + extra + "\n");
                if (skipverbose) {
                    System.out.println("--skip element:" + info.getName());
                    System.out.println(extra);
                }
            }
        }
        courantEntry.addExtraLangSet();
        return "";
    }

    static String getTermEntry(Element e) {
        boolean localverbose = true;
        courantEntry = new Entry(resource, true);
        courantEntry.setExtraConcepts("");
        courantEntry.setConceptNote("");
        totEntries++;
        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("descrip")
                    && info.getAttributeValue("type").equals("subjectField")) {
                courantEntry.getConcept().setSubjectField(getText(info, localverbose));
            } else if (info.getName().equals("descripGrp")) {
                getDescripGrpConcept(info);
            } else if (info.getName().equals("langSet")) {
                ; // process in next loop
            } else if (info.getName().equals("note")) {
                courantEntry.getConcept().setConceptNote(courantEntry.getConceptNote() + getText(info, localverbose) + "\n");
            } else if (info.getName().equals("transacGrp")) {
                getConceptTransacGrp(info);
            } else {
                String extra = getExtraElement(info);
                courantEntry.setExtraConcepts(courantEntry.getExtraConcepts() + extra + "\n");
                if (skipverbose) {
                    System.out.println("--skip element:" + info.getName());
                    System.out.println(extra);
                }
            }
        }
        courantEntry.addConcept();
        i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("langSet")) {
                getLangSet(info);
            }
        }
        return "";
    }

    static String getConceptTransacGrp(Element e) {
        boolean localverbose = true;
        boolean attributeverbose = false;
        String transactionType = "";

        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("transac")
                    && info.getAttributeValue("type").equals("transactionType")) {
                transactionType = getText(info, localverbose);
            } else if (info.getName().equals("transacNote")
                    && info.getAttributeValue("type").equals("responsibility")) {
                String user = getText(info, localverbose);
                //System.out.println("user:" + user);
                switch (transactionType) {
                    case "origination":
                        //System.out.println("created by user:" + user);                      
                        courantEntry.getConcept().setCreateBy(new BigInteger(Queries.getOwnerID(user, TermEnum.AutoCreate.YES).getIdOwner().toString()));
                        break;
                    case "modification":
                        //System.out.println("modified by user:" + user);
                        courantEntry.getConcept().setLastmodifiedBy(new BigInteger(Queries.getOwnerID(user, TermEnum.AutoCreate.YES).getIdOwner().toString()));
                        break;
                    default:
                        System.out.println("ERROR transactionType unknown:" + transactionType);
                }
           //     courantEntry.getConcept().setCreateBy(new BigInteger(Queries.getOwnerID(getText(info, localverbose), TermEnum.AutoCreate.YES).getIdOwner().toString()));
            } else if (info.getName().equals("date")) {
                String sdate = getText(info, localverbose).replace('T', ' ');
                Date date = null;
                try {
                    date = convertDate.parse(sdate);
                } catch (ParseException ex) {
                    System.out.println("ERROR date format unknown:" + sdate);
                    //Logger.getLogger(TBX_Loader.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("date:" + sdate);
                switch (transactionType) {
                    case "origination":
                        courantEntry.getConcept().setCreation(date);
                        break;
                    case "modification":
                        courantEntry.getConcept().setLastmodified(date);
                        break;
                    default:
                        System.out.println("ERROR transactionType unknown:" + transactionType);
                }
            } else {
                String extra = getExtraElement(info);
                courantEntry.setExtraLangsets(courantEntry.getExtraLangsets() + extra + "\n");
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

    static String getDescripGrpConcept(Element e) {
        boolean localverbose = true;
        if (localverbose) {
            System.out.println("--- process:" + e.getName());
        }
        List listNode = e.getChildren();
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element info = (Element) i.next();
            if (info.getName().equals("descrip")
                    && info.getAttributeValue("type").equals("definition")) {
                courantEntry.getConcept().setConceptDefinition(getText(info, localverbose));
            } else if (info.getName().equals("admin")
                    && info.getAttributeValue("type").equals("source")) {
                courantEntry.getConcept().setConceptSourceDefinition(getText(info, localverbose));
            } else {
                String extra = getExtraElement(info);
                courantEntry.setExtraConcepts(courantEntry.getExtraConcepts() + extra + "\n");
                if (skipverbose) {
                    System.out.println("--skip element:" + info.getName());
                    System.out.println(extra);
                }
            }
        }
        return "";
    }

    static void afficheALL(Element element) {
        boolean localverbose = false;
        if (localverbose) {
            System.out.println("--- process from root:" + element.getName());
        }
        List listNode = element.getChildren();

        //On crée un Iterator sur notre liste
        if (resource == null) { // only the first time
            resource = ManageResource.create(resourceName, "PUBLIC", "???", "");
        }
        Iterator i = listNode.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            if (courant.getName().equals("termEntry")) {
                getTermEntry(courant);
            } else {
                if (skipverbose && localverbose) {
                    System.out.println("skip element:" + courant.getName());
                }
                afficheALL(courant);
            }
        }
    }
}
