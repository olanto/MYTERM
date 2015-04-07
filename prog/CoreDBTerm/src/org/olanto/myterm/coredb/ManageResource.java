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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ManageResource {

    public static Resources create(String resourceName, String resourcePrivacy, String ownerLastName, String extra) {
        Resources res = new Resources(null, resourceName, resourcePrivacy);
        Owners own = Queries.getOwnerID(ownerLastName, TermEnum.AutoCreate.YES);
        res.setIdOwner(own.getIdOwner());
        res.setExtra(extra);
        TermDB.resourcesJC.create(res);
        return res;
    }

    public static Resources create(String resourceName, String resourcePrivacy, long ownerID, String extra) {
        Resources res = new Resources(null, resourceName, resourcePrivacy);
        if (ownerID < 0) {
            Owners own = Queries.getOwnerID("admin", TermEnum.AutoCreate.YES);
            res.setIdOwner(own.getIdOwner());
        } else {
            res.setIdOwner(ownerID);
        }
        res.setExtra(extra);
        TermDB.resourcesJC.create(res);
        return res;
    }

    public static Resources edit(Resources rs) {
        try {
            TermDB.resourcesJC.edit(rs);
            return rs;
        } catch (Exception ex) {
            Logger.getLogger(ManageOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void remove(String resourceName) {
        Resources res = Queries.getResourceID(resourceName, TermEnum.AutoCreate.NO);
        if (res == null) {
            System.out.println("This ressource doesnt exist :" + resourceName);
            return;
        }
        ManageConcept.remove(res);

        //  ajouter les removes pour les droits des ressources;
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            TermDB.resourcesJC.destroy(res.getIdResource());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void remove(long resID) {
        Resources res = Queries.getResourceByID(resID);
        if (res == null) {
            System.out.println("This ressource doesnt exist :" + resID);
            return;
        }
        ManageConcept.remove(res);

        //  ajouter les removes pour les droits des ressources;
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            TermDB.resourcesJC.destroy(res.getIdResource());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
