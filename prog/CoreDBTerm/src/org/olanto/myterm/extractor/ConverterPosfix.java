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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.extractor.entry.ConceptEntry;
import org.olanto.myterm.extractor.model.posfix.Langdef;
import org.olanto.myterm.extractor.model.posfix.LoadModelPosfix;
import org.olanto.myterm.extractor.model.posfix.ModelPosfix;
import org.olanto.myterm.extractor.model.posfix.Posdef;
import org.olanto.myterm.extractor.separator.CharSeparatorChooser;

public class ConverterPosfix implements Converter {

    private static String separator;
    private ModelPosfix model;
    ConceptEntry concept;
    Resources resource;
    static long timer = System.currentTimeMillis();

    public void convertAFileIntoTBX(String fname, String encoding, String resourceName, String modelfile) {
        model = LoadModelPosfix.loadAFileIntoModel(modelfile);
        separator = CharSeparatorChooser.select(model);
        resource = ManageResource.create(resourceName, "PUBLIC", "???", "info");
        StringBuffer b = new StringBuffer("");

        int countline = 0;
        int countconcept = 0;
        try {


            InputStreamReader isr = new InputStreamReader(new FileInputStream(fname), encoding);
            BufferedReader in = new BufferedReader(isr);
            String w = in.readLine();
            for (int i = 0; i < model.getSkipline(); i++) {
                countline++;
                System.out.println("skip this line:" + w);
                w = in.readLine();
            }
            System.out.println("Begin with this line:" + w);
            while (w != null) {
                countline++;
                String[] parts = w.split(separator);
                if (parts.length < model.getNbcolumns()) {
                    System.out.println("FATAL ERROR: not enough columns  at line:" + countline);
                    for (int i = 0; i < parts.length; i++) {
                        System.out.println("part:" + i + "-->" + parts[i] + "<--");
                    }
                    System.exit(0);
                }
                countconcept++;
                if (countconcept % 100 == 0) {
                    timer = System.currentTimeMillis() - timer;
                    System.out.println("processed:" + countconcept + ", " + timer + " [ms]");
                    timer = System.currentTimeMillis();
                }

                // open part
                concept = new ConceptEntry(resource, true);
                process(parts);
                concept.flush();
                concept = null;
                w = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total lines:" + countline);
        System.out.println("total concepts:" + countconcept);

    }

    private void process(String[] parts) {
        for (int i = 0; i < model.nbLanguage(); i++) {
            Langdef langdef = model.getLanguage(i);
            String lang = langdef.lang;
            if (!lang.equals(ModelPosfix.ERROR)) {
                concept.addTerm(lang, "???????");
                Terms currentTerm = concept.getTerm(lang, 0);
                for (int j = 0; j < langdef.posdef.size(); j++) {
                    Posdef def = langdef.posdef.get(j);
                    addDefinition(currentTerm, def.position, def.definition, parts);
                }
            } else {
                //  System.out.println("skip :" + w);
            }
        }
    }

    private void addDefinition(Terms term, int position, String definition, String[] parts) {
        if (position >= parts.length) {
            System.out.println("position:" + position + " too large ...");
            return;
        }
        switch (definition) {
            case "termForm":
                term.setTermForm(parts[position]);
                break;
            case "termPartofspeech":
                term.setTermPartofspeech(parts[position]);
                break;
            case "termDefinition":
                term.setTermDefinition(parts[position]);
                break;
            case "termNote":
                term.setTermNote(parts[position]);
                break;
            case "termUsage":
                term.setTermUsage(parts[position]);
                break;
            case "termSource":
                term.setTermSource(parts[position]);
                break;
            default:
                System.out.println("Definition:" + definition + " not implemented ...");
        }

    }
}
