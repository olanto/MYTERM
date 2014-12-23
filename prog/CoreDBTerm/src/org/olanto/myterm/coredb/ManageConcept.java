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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ManageConcept {

    public static Concepts addNewConcept() {
        Concepts con = new Concepts();
        TermDB.conceptsJC.create(con);
        return con;
    }

    public static Concepts addConceptToResource(String resourceName) {
        Concepts con = new Concepts();
        Resources res = Queries.getResourceID(resourceName, TermEnum.AutoCreate.YES);
        con.setIdResource(res.getIdResource());
        TermDB.conceptsJC.create(con);
        return con;
    }

    public static Concepts addConceptToResource(String resourceName, Concepts con) {
        Resources res = Queries.getResourceID(resourceName, TermEnum.AutoCreate.YES);
        con.setIdResource(res.getIdResource());
        TermDB.conceptsJC.create(con);
        return con;
    }

    public static Concepts addConceptToResource(Resources res, Concepts con) {
        con.setIdResource(res.getIdResource());
        TermDB.conceptsJC.create(con);
        return con;
    }

    public static Concepts addConcept(Concepts con) {
        TermDB.conceptsJC.create(con);
        return con;
    }

    public static void remove(Resources res) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdResource");
        query.setParameter("idResource", res.getIdResource());
        List<Concepts> listOfConcept = query.getResultList();
        for (Concepts con : listOfConcept) {
            ManageLangsets.remove(con);
            try {
                TermDB.conceptsJC.destroy(con.getIdConcept());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ManageConcept.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Concepts edit(Concepts con) {
        try {
            TermDB.conceptsJC.edit(con);
            return con;
        } catch (Exception ex) {
            Logger.getLogger(ManageConcept.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean remove(long con) {
        ManageLangsets.remove(con);
        try {
            TermDB.conceptsJC.destroy(con);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ManageConcept.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
