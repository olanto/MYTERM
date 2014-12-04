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

import org.olanto.myterm.coredb.ManageResource;

/**
 *
 * @author jg
 * 
 * 
 * 
 */
public class LoadInToDB {
    
    public static void main(String[] args) {
  //      loadAFile("C:/MYTERM/tests/TBX-basic-samples.tbx", "TESTTBX", "TBX-BASIC");
   //     loadAFile("C:/MYTERM/tests/exportTBX.xml", "TESTTBXEXPORTED", "TBX-BASIC");
     //     loadAFile("C:/MYTERM/tests/XDXF/dict_fr_en.xdxf", "XDXF_fr_en2", "XDXF");
   //     loadAFile("C:/MYTERM/private/dict_en_ru.xdxf","XDXF_en_ru2", "XDXF");
       //  loadAFile("C:/MYTERM/private/WTODisputeSettlementTerminology.xml","TESTOMC","TBX-BASIC");
        loadAFile("C:/MYTERM/private/WTOTerm public entries_EN_FR_ES__2014_12_01.xml","PUBLIC-OMC","MTF");
       // loadAFile("C:/MYTERM/private/CERN_from_MELBY.tbx","TESTCERN","TBX-BASIC");
    }
    
    public static void loadAFile(String fileName, String resourceName, String format) {
        Loader loader;
            switch (format) {
            case "TBX-BASIC":
                loader = new TBX_Loader();
                break;
           case "XDXF":
                loader = new XDXF_Loader();
                break;
          case "MTF":
                loader = new MTF_Loader();
                break;
            default:
                System.out.println("Format:" + format + " not implemented ...");
                return;
        }
  System.out.println("-------- try to remove Resource if exist : " + resourceName);
        ManageResource.remove(resourceName);
        System.out.println("-------- start loading file : " + fileName + ", format:" + format);        
        loader.loadAFileIntoTBXDB(fileName, resourceName);
        System.out.println("-------- end loading file : " + fileName);
        
    }
}
