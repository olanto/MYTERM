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

package org.olanto.myterm.export.tbx;

import static org.olanto.myterm.export.tbx.ExportTBXFromDB.*;
import static org.olanto.myterm.export.tbx.JdomUtilities.*;

import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * génére la partie fixe de l'entête du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateTBXHeader {

    /**
     * génére la partie fixe de l'entête du fichier
     *
     * @param debut_imposition
     * @param fin_imposition
     * @return
     */
    public static Element setHeader(String debut_imposition, String fin_imposition) {
        Element res = makeElem("martifHeader");
        Element fd = makeElem("fileDesc");
        res.addContent(fd);
        Element ts  =makeElem("titleStmt");
        fd.addContent(ts);
        ts.addContent(makeElem("title", resource.getResourceName()));
        ts.addContent(makeElem("note", resource.getResourceNote()));
        Element sd  =makeElem("sourceDesc");
        fd.addContent(sd);
        sd.addContent(makeElem("p", "exported from myTerm (olanto.org) date: "+getCurrentDate()));
        Element ed = makeElem("encodingDesc","http://www.lisa.org/fileadmin/standards/tbx_basic/TBXBasicXCSV02.xcs");
        ed.setAttribute("type", "XCSURI");
        res.addContent(ed);
        return res;
    }

 }
