package org.olanto.myterm.parsetbx;

import org.olanto.myterm.coredb.ManageResource;

/**
 *
 * @author jg
 */
public class RemoveRessources {


    public static void main(String[] args) {
        String resourceName = "INSERTED WITH SQL";       
        ManageResource.remove(resourceName);
        
        System.out.println("removed Ressource: "+resourceName);
    }

  
}
