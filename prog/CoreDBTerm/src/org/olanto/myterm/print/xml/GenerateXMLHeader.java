/**
 * ********
 * Copyright � 2013-2014 Olanto Foundation Geneva
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

import static org.olanto.myterm.print.xml.PrintXMLFromDB.*;
import static org.olanto.myterm.print.xml.JdomUtilities.*;

import org.jdom2.Element;

/**
 * génére la partie fixe de l'entête du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateXMLHeader {

    /**
     * génére la partie fixe de l'entête du fichier
     *
     * @param debut_imposition
     * @param fin_imposition
     * @return
     */
    public static Element setHeader(String debut_imposition, String fin_imposition) {
        Element res = makeElem("header");
       res.addContent(makeElem("titleRes" ).addContent(makeElem("p", resource.getResourceName())));
        res.addContent(makeElem("noteRes" ).addContent(makeElem("p", resource.getResourceNote())));
        Element sd  =makeElem("sourceRes");
        res.addContent(sd);
        sd.addContent(makeElem("p", "exported from myTerm (olanto.org) date: "+getCurrentDate()));
        return res;
    }

 }
