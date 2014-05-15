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
package org.olanto.myterm.extractor.model.prefix;

import java.util.Vector;

/**
 *
 * @author jg
 */
public class ModelPrefix {

    public static String ERROR="??";
    private String separator;
    private String specialLine;
    private Vector<Prefix> pref;

    public ModelPrefix() {
        pref = new Vector<>();
    }
    
    public String getLanguage(String w){
      for (int i = 0; i < pref.size(); i++) {
          if (w.startsWith(pref.get(i).prefix)){
           return pref.get(i).lang; 
          }
         }
      return ERROR;
    }
    
    public String getTerm(String w){
      for (int i = 0; i < pref.size(); i++) {
          if (w.startsWith(pref.get(i).prefix)){
           return w.substring(pref.get(i).prefix.length()).trim(); 
          }
         }
      return ERROR;
    }
    
    public void addPrefix(String prefix, String lang) {
        pref.add(new Prefix(prefix, lang));
    }

    public void dump() {
        System.out.println("Model:");
        System.out.println("  Separaror:" + separator);
        System.out.println("  Prefix:");
        for (int i = 0; i < pref.size(); i++) {
            System.out.println("     prefix:" + pref.get(i).prefix + "-->" + pref.get(i).lang);
        }
    }

    /**
     * @return the separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * @return the specialLine
     */
    public String getSpecialLine() {
        return specialLine;
    }

    /**
     * @param specialLine the specialLine to set
     */
    public void setSpecialLine(String specialLine) {
        this.specialLine = specialLine;
    }
}
