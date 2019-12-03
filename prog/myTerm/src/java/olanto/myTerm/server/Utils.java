/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author simple
 *
 * utilitaire pour le service
 */
public class Utils {

    public static String extractCrossRef(String crossReference) {
        String ref = "<br/>";
        Map<String, String> concepts = getConceptIDs(crossReference);
        for (Map.Entry<String, String> entry : concepts.entrySet()) {
            ref += "<br/><a href=#TS" + entry.getValue() + ">" + entry.getValue()+ " - " + entry.getKey() + "</a>";
        }
        return ref;
    }

    private static Map<String, String> getConceptIDs(String crossReference) {
        crossReference = crossReference.replaceAll("\n", ";");
        crossReference = crossReference.replaceAll("\\s+", "");
        crossReference = crossReference.replaceAll("\\;+", ";");
        String[] references = crossReference.split(";");
        Map<String, String> conceptIDs = new HashMap<>();
        int idx = 0, idx1 = 0;
        String key = "", value = "";
        System.out.println(crossReference);
        for (String ref : references) {
            idx = ref.indexOf("\">");
            idx1 = ref.indexOf("</");
            key = ref.substring(idx + 2, idx1);
            idx = ref.indexOf("\"");
            idx1 = ref.lastIndexOf("\"");
            value = ref.substring(idx + 1, idx1);
            conceptIDs.put(key, value);
        }
        return conceptIDs;
    }
}
