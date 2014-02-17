/**********
    Copyright © 2013-2014 Olanto Foundation Geneva

   This file is part of myTERM.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/
package org.olanto.myterm.coredb;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ManageConcept {

    public static Concepts addConceptToResource(String resourceName) {
         Concepts con = new Concepts();
         Resources res=Queries.getResourceID(resourceName, TermEnum.AutoCreate.YES);
         con.setIdResource(res);
        TermDB.conceptsJC.create(con);
        return con;
   }
   public static Concepts addConceptToResource(String resourceName, Concepts con) {
         Resources res=Queries.getResourceID(resourceName, TermEnum.AutoCreate.YES);
         con.setIdResource(res);
        TermDB.conceptsJC.create(con);
        return con;
   }
   public static Concepts addConceptToResource(Resources res, Concepts con) {
         con.setIdResource(res);
        TermDB.conceptsJC.create(con);
        return con;
   }
      public static void remove(Collection<Concepts> listOfConcept) {
          for (Concepts con: listOfConcept ){
              try {
                  ManageLangsets.remove(con.getLangsetsCollection());
                  TermDB.conceptsJC.destroy(con.getIdConcept());
              } catch (IllegalOrphanException ex) {
                  Logger.getLogger(ManageConcept.class.getName()).log(Level.SEVERE, null, ex);
              } catch (NonexistentEntityException ex) {
                  Logger.getLogger(ManageConcept.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      }

 }
