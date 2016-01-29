/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qageneration;

import java.util.Vector;
import static qageneration.QAGeneration.glossary;

/**
 *
 * @author simple
 */
public class Glossary {

    public Vector<Entry> glossary = new Vector<>();

    public Glossary() {
    }

    public void add(Entry e) {
        glossary.add(e);
    }
    
   public Entry get(int i) {
        return glossary.get(i);
    }

    public int size() {
        return glossary.size();
    }

    public void dump(String title) {
        System.out.println(title);
        for (int i = 0; i < glossary.size(); i++) {
            Entry e = get(i);
            System.out.println("\t" + e.key + "\t" + e.def);
        }

    }

    public void generate(int i) {
        Entry e = glossary.get(i);
        e.dump();
        // candidate with one keyTerm
        for (int j = 0; j < e.keyTerms.size(); j++) {
            Glossary res = getSimilarOnKey(e, e.keyTerms.get(j));
            res.dump("Key 1:" + e.keyTerms.get(j));
        }
        // candidate with two keyTerm
        if (e.keyTerms.size() > 1) {
            for (int j = 0; j < e.keyTerms.size(); j++) {
                Glossary res1 = getSimilarOnKey(e, e.keyTerms.get(j));
                for (int k = j+1; k < e.keyTerms.size(); k++) {
                    Glossary res2 = getSimilarOnKey(e, e.keyTerms.get(k));
                    Glossary res3 = intersect(res1, res2);
                    res3.dump("Key 2:" + e.keyTerms.get(j) +" "+ e.keyTerms.get(k));
                }
            }
        }
    }

    public Glossary intersect(Glossary r1,Glossary r2){
        Glossary result = new Glossary();
        for (int i = 0; i < r1.size(); i++) {
            if (r2.exists(r1.get(i)))
                result.add(r1.get(i));
         }
        return result;
    }
  
        public boolean exists(Entry e) {
            for (int i = 0; i < size(); i++) {
             if (e.key.equals(glossary.get(i).key)) return true; 
            }
            return false;
        }

    
    
    public Glossary getSimilarOnKey(Entry e, String kt) {
        Glossary result = new Glossary();
        for (int i = 0; i < glossary.size(); i++) {
            if (testkt(e, kt, glossary.get(i))) {
                result.add(glossary.get(i));
            };
        }
        return result;
    }

    public boolean testkt(Entry e, String kt, Entry candidate) {
        if (e.key.equals(candidate.key)) {
            return false;
        }
        for (int i = 0; i < candidate.keyTerms.size(); i++) {
            if (kt.equals(candidate.keyTerms.get(i))) {
                return true;
            }
        }
        return false;
    }
}
