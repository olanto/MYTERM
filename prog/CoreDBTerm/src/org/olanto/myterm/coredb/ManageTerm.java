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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ManageTerm {

    public static Terms addTermToLangset(Langsets lan,  String termForm, char status) {
         Terms ter = new Terms(null, termForm, status);
         ter.setIdLangset(lan.getIdLangset());
         ter.setIdLanguage(lan.getIdLanguage());
        TermDB.termsJC.create(ter);
        return ter;
   }
    public static Terms addTermToLangset(Langsets lan, Terms ter) {
         ter.setIdLangset(lan.getIdLangset());
         ter.setIdLanguage(lan.getIdLanguage());
        TermDB.termsJC.create(ter);
        return ter;
   }
       public static void remove(Langsets lan) {
        Query query = TermDB.em.createNamedQuery("Terms.findByIdLangset");
        query.setParameter("idLangset", lan.getIdLangset());
        List<Terms>  listOfTerms= query.getResultList();
      for (Terms ter: listOfTerms ){
               try {
                   TermDB.termsJC.destroy(ter.getIdTerm());
               } catch (NonexistentEntityException ex) {
                   Logger.getLogger(ManageTerm.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }

}
