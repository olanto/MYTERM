/**
 * ********
 * Copyright  2013-2014 Olanto Foundation Geneva
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
package org.olanto.myterm.extractor;

import org.olanto.myterm.coredb.ManageResource;
import static org.olanto.myterm.extractor.ModelEnum.*;
import static org.olanto.myterm.extractor.ModelEnum.Model.POSFIX;


/**
 *
 * @author jg
 */
public class ConvertAndLoadIntoDB {
    
    public static void main(String[] args) {

   //    ConvertAFile("C:/MYTERM/private/glossaries/Names of fish [EFSL] FAO 1997_EN_FR_ES.doc.txt","FISH_NAME", Model.PREFIX,"C:/MYTERM/models/prefix_model.xml");
  //      ConvertAFile("C:/MYTERM/private/glossaries/Database 4 [EFG] ESA 1999_EN_FR.doc.txt","ESA", "PREFIX","C:/MYTERM/models/prefix_model2.xml");
        ConvertAFile("C:/MYTERM/private/DESAUSSURE/Area A.txt.cvs","DESAUSSURE", Model.POSFIX,"C:/MYTERM/models/posfix_desaussure.xml");
   //    ConvertAFile("C:/MYTERM/tests/UTX/legutx1.01/legutx1.01.utx","LEG-UTX", Model.POSFIX,"C:/MYTERM/models/posfix_model_utx2.xml");
  //     ConvertAFile("C:/MYTERM/private/WIPO/WIPONewCompleteExport04022014-UTF8.txt","WIPO-TEST", Model.POSFIXREP,"C:/MYTERM/models/posfix_model_repeat.xml");
     }
    
    public static void ConvertAFile(String fileName,String resourceName, Model format, String modelfile) {
        Converter converter;
            switch (format) {
            case PREFIX:
                converter = new ConverterPrefix();
                break;
           case POSFIX:
                converter = new ConverterPosfix();
                break;
           case POSFIXREP:
                converter = new ConverterPosfixRepeat();
                break;
            default:
                System.out.println("Format:" + format + " not implemented ...");
                return;
        }
   System.out.println("-------- try to remove Resource if exist : " + resourceName);
        ManageResource.remove(resourceName);
       System.out.println("-------- start converting file : " + fileName + ", format:" + format);        
        converter.convertAFileIntoTBX(fileName, "UTF-8", resourceName, modelfile);
        System.out.println("-------- end loading file : " + fileName);
        
    }
}
