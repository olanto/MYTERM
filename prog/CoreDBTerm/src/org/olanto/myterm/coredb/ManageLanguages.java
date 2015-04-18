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
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class ManageLanguages {

    public static void remove(String langID) {
        try {
            TermDB.languagesJC.destroy(langID);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageLanguages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Languages create(Languages lan) {
        try {
            TermDB.languagesJC.create(lan);
            return lan;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(ManageLanguages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManageLanguages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Languages update(Languages lan) {
        try {
            TermDB.languagesJC.edit(lan);
            return lan;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageLanguages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManageLanguages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
