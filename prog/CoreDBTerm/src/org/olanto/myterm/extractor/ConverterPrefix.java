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
package org.olanto.myterm.extractor;

import org.olanto.myterm.extractor.separator.LineSeparatorChooser;
import org.olanto.myterm.extractor.separator.Separator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.model.prefix.LoadModelPrefix;
import org.olanto.myterm.extractor.model.prefix.ModelPrefix;

public class ConverterPrefix implements Converter {

    private static Separator separator;
    private ModelPrefix model;
    ConceptEntry concept;
    Resources resource;

    public void convertAFileIntoTBX(String fname, String encoding, String resourceName, String modelfile) {
        model = LoadModelPrefix.loadAFileIntoModel(modelfile);
        separator = LineSeparatorChooser.select(model);
        resource = ManageResource.create(resourceName, "PUBLIC", "???", "info");
        StringBuffer b = new StringBuffer("");
        boolean InsideConcept = false;
        int countline = 0;
        int countconcept = 0;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fname), encoding);
            BufferedReader in = new BufferedReader(isr);
            String w = in.readLine();
            // find start
            while (w != null) {
                countline++;
                if (w.equals("<BEGIN>")) {
                    System.out.println("skip header OK");
                    w = in.readLine();
                    break;
                }
                w = in.readLine();

            }
            System.out.println("<BEGIN> at line:" + countline);

            InsideConcept = false;
            while (w != null) {
                countline++;
                //System.out.println("parse:"+w);
                if (!InsideConcept) { // search a new concept                   
                    if (separator.checkStart(w)) { // start a new concept                       
                        InsideConcept = true;
                        countconcept++;
                        // open part
                        concept = new ConceptEntry(resource, true);
                        process(w);
                    } else { // continue to search
                        ;
                    }
                } else {  // parse a concept
                    if (separator.checkEnd(w)) { // end of this concept
                        InsideConcept = false;
                        // save part
                        concept.flush();
                        concept = null;
                    } else { // continue to process the courant concept
                        process(w);
                    }
                }
                w = in.readLine();
            }
            if (concept != null) {
                concept.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total lines:" + countline);
        System.out.println("total concepts:" + countconcept);
    }

    private void process(String w) {
        String lang = model.getLanguage(w);
        if (!lang.equals(ModelPrefix.ERROR)) {
            String term = model.getTerm(w);
            System.out.println(w + "| " + lang + ", " + term);
            if (!term.equals("")) {
                concept.addTerm(lang, term);
            }
        } else {
          //  System.out.println("skip :" + w);
        }

    }
}
