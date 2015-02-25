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

/**
 *
 * @author simple
 */
public class ManageOwner {

    public static Owners create(String ownerFirstName, String ownerLastName, String ownerMailing, String ownerStatus, String owherHash) {
        Owners oa = new Owners(null, ownerFirstName, ownerLastName, ownerMailing, ownerStatus);
        oa.setOwnerHash(owherHash);
        TermDB.ownersJC.create(oa);
        return oa;
    }
    
     public static Owners create(String ownerFirstName, String ownerLastName, String ownerMailing, String ownerStatus, String owherHash, String ownerRole) {
        Owners oa = new Owners(null, ownerFirstName, ownerLastName, ownerMailing, ownerStatus);
        oa.setOwnerHash(owherHash);
        oa.setOwnerRoles(ownerRole);
        TermDB.ownersJC.create(oa);
        return oa;
    }

    public static Owners edit(Owners ow) {
        try {
            TermDB.ownersJC.edit(ow);
            return ow;
        } catch (Exception ex) {
            Logger.getLogger(ManageOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    public static void remove(long ownerID) {
        try {
            TermDB.ownersJC.destroy(ownerID);
        } catch (Exception ex) {
            Logger.getLogger(ManageOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
