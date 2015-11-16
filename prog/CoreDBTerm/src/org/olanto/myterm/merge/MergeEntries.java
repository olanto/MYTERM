/**
 * ********
 * Copyright © 2013-2015 Olanto Foundation Geneva
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
package org.olanto.myterm.merge;

import java.util.List;
import javax.persistence.Query;
import static org.olanto.myterm.export.tbx.ExportTBXFromDB.*;
import static org.olanto.myterm.export.tbx.JdomUtilities.*;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermDB;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;

/**
 * gÃ©nÃ©re la partie fixe de l'entÃªte du fichier.
 *
 * @author jg
 *
 *
 */
public class MergeEntries {

    /**
     * génère les entrées
     *
     * @return
     */
    public static void changeIdRessourceFromDB(long resIdFrom,long resIdInto) {
         getAllConcepts(resIdFrom,resIdInto);

    }

    public static void getAllConcepts(long resIdFrom,long resIdInto) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdResource");
        query.setParameter("idResource", resIdFrom);
        List<Concepts> listOfConcept = query.getResultList();
        for (Concepts con : listOfConcept) {
            con.setIdResource(resIdInto);
            ManageConcept.edit(con);
        }
    }


}