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
public class LoadInToDBnoREMOVE {
    
    public static void main(String[] args) {
//         loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-1.tbx", "IATE", "TBX-BASIC"); //ok
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-2.tbx", "IATE", "TBX-BASIC"); //ok
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-3.tbx", "IATE", "TBX-BASIC"); //ok

//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-4.tbx", "IATE", "TBX-BASIC"); //ok
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-5.tbx", "IATE", "TBX-BASIC"); //ok
                
//         loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-6.tbx", "IATE", "TBX-BASIC"); //ok

//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-7.tbx", "IATE", "TBX-BASIC"); //ok
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-8.tbx", "IATE", "TBX-BASIC");
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-9.tbx", "IATE", "TBX-BASIC");
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-10.tbx", "IATE", "TBX-BASIC");
        
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-11.tbx", "IATE", "TBX-BASIC");

 //       loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-12.tbx", "IATE", "TBX-BASIC"); // ok
//

        loadAFile(args[0], "IATE", "TBX-BASIC");
        
 //       loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-13.tbx", "IATE", "TBX-BASIC");
//        loadAFile("C:/MYTERM/tests/IATE/IATE_export_27012015-14.tbx", "IATE", "TBX-BASIC");
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
 // System.out.println("-------- NO REMOVE  Resource if exist : " + resourceName);
 //       ManageResource.remove(resourceName);
        System.out.println("-------- start loading file : " + fileName + ", format:" + format);        
        loader.loadAFileIntoTBXDB(fileName, resourceName);
        System.out.println("-------- end loading file : " + fileName);
        
    }
}
