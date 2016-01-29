/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qageneration;

import java.util.Vector;
import static qageneration.QAGeneration.separator;

/**
 *
 * @author simple
 */
public class Entry {

    public String key;
    public String def;
    public Vector<String> keyTerms = new Vector<>();

    public Entry(String key, String def) {
        this.key = key;
        this.def = def;
        extractKeyTerms();
    }

    public void extractKeyTerms() {
        String[] parts = key.replace("("," ").replace(")"," ").split("\\s");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 2) {
                keyTerms.add(parts[i]);
            }
        }
    }

    public void dump() {
        System.out.println("____________________________________________________________________");
        System.out.println(key + " definition:" + def);
        System.out.print("Key Terms:");
        for (int i = 0; i < keyTerms.size(); i++) {
            System.out.print(" " + keyTerms.get(i));
        }
        System.out.println();
    }
}
