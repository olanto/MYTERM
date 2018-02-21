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


import org.olanto.myterm.export.tbx.ExportTBXFromDB;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Utilitaires autour de JDom
 *
 * @author jg
 */
public class JdomUtilities {
    
    public static void main(String[] args) {
        System.out.println(getCurrentDate()); 
                //getFormattedDate(Date d)
        
    }
    

    /**
     * sÃ©paration dans les fichiers en sortie de la BD
     */
    public static final String SEPARATOR = "[|]";
    private static Element racine;
    private static Pattern p = Pattern.compile(SEPARATOR);

    /**
     * initialise la racine du XML
     *
     * @param element de la racine
     * @param nameSpace de la racine
     * @param url du nameSpace
     */
    public static void initRoot(String element) {

        Element elementAjoute = new Element(element);
        racine = elementAjoute;

        // dtd dependent

        racine.setAttribute("type", "TBX-Basic");
        racine.setAttribute("lang", "en-US", Namespace.XML_NAMESPACE);
    }

    /**
     * crée un élément
     *
     * @param name nom de l'élément
     * @return élément
     */
    public static Element makeElem(String name) {
        return new Element(name);
    }

    /**
     * crée un élément préfixé par le name space
     *
     * @param name nom de l'élément
     * @return élément
     */
    public static Element makeElem(String name, Namespace nameSpace) {
        return new Element(name, nameSpace);
    }

    /**
     * crée un élément préfixé par le name space avec un texte
     *
     * @param name nom de l'élément
     * @param text texte de l'élément
     * @return élément
     */
    public static Element makeElem(String name, String text, Namespace nameSpace) {
        return new Element(name, nameSpace).setText(text);
    }

    /**
     * crée un élément avec un texte
     *
     * @param name nom de l'élément
     * @param text texte de l'élément
     * @return élément
     */
    public static Element makeElem(String name, String text) {
        return new Element(name).setText(text);
    }

    /**
     * @return the racine
     */
    public static Element getRacine() {
        return racine;
    }

    /**
     * affiche le texte du xml dans la console
     *
     * @param document xml
     */
    static void showXML(Document document) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
        }
    }

    /**
     * affiche le texte du xml dans la console
     *
     * @param document xml
     */
    static String getXML(Document document) {
        //On utilise ici un affichage classique avec getPrettyFormat()
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        String result=sortie.outputString(document);
        result = hackIMG(result);
        return result;
    }

     static String hackIMG(String s){
             s=s.replace("&lt;MEDIA ","<");
             s=s.replace("&gt;MEDIA",">");        
             s=s.replace("&gt;MEDIA",">");
            s=s.replace("&lt;h1&gt;","<h1>"); s=s.replace("&lt;/h1&gt;","</h1>"); 
            s=s.replace("&lt;h2&gt;","<h2>"); s=s.replace("&lt;/h2&gt;","</h2>"); 
            s=s.replace("&lt;p&gt;","<p>"); s=s.replace("&lt;/p&gt;","</p>");s=s.replace("&lt;p/&gt;","<p/>"); 
            s=s.replace("&lt;br&gt;","<br>"); s=s.replace("&lt;/br&gt;","</br>");s=s.replace("&lt;br/&gt;","<br/>"); 
           s=s.replace("&lt;ul&gt;","<ul>"); s=s.replace("&lt;/ul&gt;","</ul>");s=s.replace("&lt;ul/&gt;","<ul/>"); 
           s=s.replace("&lt;li&gt;","<li>"); s=s.replace("&lt;/li&gt;","</li>");s=s.replace("&lt;li/&gt;","<li/>"); 
            s=s.replace("&lt;h3&gt;","<h3>"); s=s.replace("&lt;/h3&gt;","</h3>"); 
           return s;
     }
    
    /**
     * copie le texte du xml dans un fichier
     *
     * @param document xml
     * @param namefile nom du fichier
     */
    static void saveXML(Document document, String namefile) {
        try {
            msg("export file in: " + namefile);
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(namefile));
        } catch (java.io.IOException e) {
        }
    }

    /**
     * coupe une ligne avec le sÃ©parateur
     *
     * @param line Ã  couper
     * @return les champs de la ligne
     */
    public static String[] cutLine(String line) {
        return p.split(line);
    }

    /**
     * affiche le message dans la console
     *
     * @param line Ã  afficher
     */
    public static void msg(String line) {

        if (ExportTBXFromDB.logArea != null) {
            ExportTBXFromDB.logArea.append(line + "\n");
        } else {
            System.out.println(line);
        }
    }

    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
    }

    public static String getFormattedDate(Date d) {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        return df.format(d).replace(" ", "T");
    }
}
