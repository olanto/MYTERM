/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qageneration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 *
 * @author simple
 */
public class QAGeneration {

    /**
     * Experimental package to generate Question and Answer
     */
    static String separator = "\t";
    static Glossary glossary = new Glossary();

    public static void main(String[] args) {
        ProcessAFile("C:/MYTERM/private/IIBA/IIBA_gloss.cvs", "UTF-8");
        for (int i = 0; i < glossary.size(); i++) {
            glossary.generate(i);
        }
    }

   
    
    
    public static void ProcessAFile(String fname, String encoding) {
        int countline = 0;
        int countconcept = 0;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fname), encoding);
            BufferedReader in = new BufferedReader(isr);
            String w = in.readLine();
            while (w != null) {
                countline++;
                String[] parts = w.split(separator);
                if (parts.length < 2) {
                    System.out.println("FATAL ERROR: not enough columns  at line:" + countline);
                    System.exit(0);
                }
                countconcept++;
                Entry e = new Entry(parts[0], parts[1]);

                glossary.add(e);
                // open part
                w = in.readLine();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total lines:" + countline);
        System.out.println("total concepts:" + countconcept);

    }
}
