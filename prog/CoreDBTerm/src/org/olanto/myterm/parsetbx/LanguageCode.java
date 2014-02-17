/**********
    Copyright © 2013-2014 Olanto Foundation Geneva

   This file is part of myTERM.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/
package org.olanto.myterm.parsetbx;


/**
 *
 * @author simple
 */
public class LanguageCode {

   
 
   public static String getISO2From3(String iso3){
       iso3=iso3.toUpperCase();
       if (iso3.length()!=3) {
               System.out.println("Fatal Error code ISO 3 not on 3 chars: "+iso3);
                      System.exit(0);}
              switch (iso3) {
            case "ENG":
                return "en";
           case "FRE":
                return "fr";
            default:
                System.out.println("ISO2 Conversion not implemented for: "+iso3);
                      System.exit(0);
       return null;
   }
   }
    
}
