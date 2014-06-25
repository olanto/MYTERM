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
package org.olanto.myterm.extractor.model.posfix;

import java.util.Vector;

/**
 *
 * @author jg
 */
public class ModelPosfix {

    public static String ERROR = "??";
    private String separator;
    private int skipline;
    private int skipcol;
    private int nbcolumns;
    private Vector<Langdef> langdef;

    public ModelPosfix() {
        langdef = new Vector<>();
    }

    public Langdef getLanguage(int i) {
        return langdef.get(i);
    }
    public int nbLanguage() {
        return langdef.size();
    }


    public Langdef addPosfix(String lang) {
        langdef.add(new Langdef(lang));
        return langdef.lastElement();
    }

    
    public void dump() {
        System.out.println("Model:");
        System.out.println("  Separaror:" + separator);
        System.out.println("  Nb. columns:" + nbcolumns);
        System.out.println("  Skip line:" + skipline);
        System.out.println("  Skip column:" + skipcol);
        System.out.println("  Langdef:");
        for (int i = 0; i < langdef.size(); i++) {
            System.out.println("     lang:" + langdef.get(i).lang);
            for (int j = 0; j < langdef.get(i).posdef.size(); j++) {
                System.out.println("        position:" + langdef.get(i).posdef.get(j).position
                        + " --> "
                        + langdef.get(i).posdef.get(j).definition);
            }
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
     * @return the skipline
     */
    public int getSkipline() {
        return skipline;
    }

    /**
     * @param skipline the skipline to set
     */
    public void setSkipline(int skipline) {
        this.skipline = skipline;
    }

    /**
     * @return the nbcolon
     */
    public int getNbcolumns() {
        return nbcolumns;
    }

    /**
     * @param nbcolon the nbcolon to set
     */
    public void setNbcolumns(int nbcolumns) {
        this.nbcolumns = nbcolumns;
    }

    /**
     * @return the skipcol
     */
    public int getSkipcol() {
        return skipcol;
    }

    /**
     * @param skipcol the skipcol to set
     */
    public void setSkipcol(int skipcol) {
        this.skipcol = skipcol;
    }
}
