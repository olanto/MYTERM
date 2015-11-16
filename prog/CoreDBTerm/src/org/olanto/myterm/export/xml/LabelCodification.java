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
package org.olanto.myterm.export.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.entityclasses.VjCodifications;

/**
 * Utilitaires autour de JDom
 *
 * @author jg
 */
public class LabelCodification {

    private static Map<String, String> lblcodif;
   public static void main(String[] args) {
       init("FR");
       System.out.println(getMsg("lbl.c.subject_field"));
        System.out.println(getMsg("lbl.c.subject_fieldx"));
      
   }
 
    public static void init(String idLanguage) {
        lblcodif= new HashMap<>();
        List<VjCodifications> testcode = Queries.getMsgCodification("msg", idLanguage);
        for (VjCodifications code : testcode) {
            //System.out.println(code.getCodeValue() + ", " + code.getCodeExtraLang());
            lblcodif.put(code.getCodeValue(),code.getCodeExtraLang());
        }
    }
    
    public static String  getMsg(String msg){
        String msglang=lblcodif.get(msg);
        if (msglang==null){
            return "*** warning no code for: "+msg;
        }
        return msglang;
        
    }
            
}
