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

package org.olanto.myterm.manage;


import static org.olanto.myterm.manage.ExportXLSFromDB.*;
import static org.olanto.myterm.manage.JdomUtilities.*;

import org.jdom2.Element;

/**
 * génére la partie fixe de l'entête du fichier.
 *
 * @author jg
 *
 *
 */
public class GenerateXLSHeader {

    /**
     * 
     * @return
     */
    public static String setHeader() {
        
        String rname=resource.getResourceName();
        String rnote="Note: "+resource.getResourceNote();
        String expdate=getCurrentDate();
        return rname+" - "+expdate+"\t"
                + "subject Field\t"
                    + "concept definition\t"
                    + "concept source\t"
                    + "Concept create by user\t"
                    + "Concept create date\t"
                    + "Concept modified by user\t"
                    + "Concept modified date\t"
                    + "Lang\t"
                    + "Term Status\t"
                    + "Term Form\t"
                    + "Term definition\t"
                    + "Term create by user\t"
                    + "Term create date\t"
                    + "Term modified by user\t"
                    + "Term modified date";
    }
    
    
 }
