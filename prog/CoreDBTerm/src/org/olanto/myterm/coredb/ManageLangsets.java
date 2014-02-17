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
package org.olanto.myterm.coredb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ManageLangsets {

    public static Langsets addLangsetToConcept(Concepts con, String idLanguage) {
        Langsets lan = new Langsets();
        lan.setIdLanguage(idLanguage);
        lan.setIdConcept(con.getIdConcept());
        TermDB.langsetsJC.create(lan);
        return lan;
    }
  
    public static void remove(Concepts con) {
               Query query = TermDB.em.createNamedQuery("Langsets.findByIdConcept");
        query.setParameter("idConcept", con.getIdConcept());
        List<Langsets>  listOfLangsets= query.getResultList();

          for (Langsets lan: listOfLangsets ){
                  ManageTerm.remove(lan);
                   try {
                       TermDB.langsetsJC.destroy(lan.getIdLangset());
                   } catch (NonexistentEntityException ex) {
                       Logger.getLogger(ManageLangsets.class.getName()).log(Level.SEVERE, null, ex);
                   }
       }
    }

    public static Langsets updateExtra(Long idLangset, String extra) {
        Langsets lan = Queries.getIdLangset(idLangset);
        if (lan == null) {
            return null;
        }
        lan.setExtra(extra);
        try {
            TermDB.langsetsJC.edit(lan);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ManageLangsets.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageLangsets.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(ManageLangsets.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return lan;
    }
    // public long AddOw("")
}
