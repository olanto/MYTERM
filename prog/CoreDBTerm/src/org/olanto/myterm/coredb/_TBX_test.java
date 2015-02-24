/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class _TBX_test {

    public static void main(String[] args) {
        try {
            // TODO code application logic here

            // Open a database connection
            // (create a new database if it doesn't exist yet):


           // System.out.println("owner id just after create:" + ManageOwner.create("JOE", "SMITH", "xxx@yyy.com", "DORMANT", "AAA000").getIdOwner());
            // 1026
            
            Owners o1= Queries.getOwnerID("SMITH", TermEnum.AutoCreate.NO);
            System.out.println(o1.getOwnerFirstName()+" "+o1.getOwnerLastName());
            
            o1.setOwnerFirstName("JACK");
            
            TermDB.ownersJC.edit(o1);
            
            System.out.println(o1.getOwnerFirstName()+" "+o1.getOwnerLastName());
          
    //        
    //        System.out.println("resource id just after create:" + ManageResource.create("R2", "PUBLIC", "ADMIN_IMPORT").getIdResource());
    //    Concepts con=ManageConcept.addConceptToResource("R3");
    //        System.out.println("resource id just after create:" + con.getIdConcept());
    //Langsets lanEN=ManageLangsets.addLangsetToConcept(con,"EN");
    //       System.out.println("Langsets id just after create:" + lanEN.getIdLangset());
    //Terms t1=ManageTerm.addTermToLangset(lanEN, "yes" ,'p');
    //       System.out.println("Terms id just after create:" + t1.getIdTerm());
            //        System.out.println("Domain ID for Painting:" + getDomainID("Painting", AutoCreate.NO));
    //        System.out.println("Domain ID for Mathematics:" + getDomainID("Mathematics", AutoCreate.YES));
    //        System.out.println("Domain ID for Physics:" + getDomainID("Physics", AutoCreate.YES));
    //        System.out.println("Language ID for EN:" + Queries.getLanguageID("EN", AutoCreate.YES));
    //        System.out.println("owner id for TOTO:" + getOwnerID("TATA", AutoCreate.YES).getIdOwner());

            
           
            // Close the database connection:
            TermDB.emf.close();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(_TBX_test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(_TBX_test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
