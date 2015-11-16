/**
 * ********
 * Copyright 2013-2015 Olanto Foundation Geneva
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

import org.olanto.myterm.coredb.ManageResource;
import org.olanto.myterm.coredb.Queries;
import org.olanto.myterm.coredb.TermEnum;
import org.olanto.myterm.coredb.entityclasses.Resources;
import static org.olanto.myterm.export.tbx.ExportTBXFromDB.resourceName;

/**
 *
 * @author jg
 */
public class MergeIntoDB {
private static Resources resourceFrom;
private static Resources resourceInto;
    public static void main(String[] args) {
        mergeARessource("TESTTBX", "DEMO Resource");
    }

    public static void mergeARessource(String resourceNameFrom, String resourceNameInto) {
        System.out.println("-------- try to merge this : " + resourceNameFrom + " into :" + resourceNameInto);
         System.out.println("-------- open : " + resourceNameFrom );
        resourceFrom = Queries.getResourceID(resourceNameFrom, TermEnum.AutoCreate.NO);
         System.out.println("-------- open : " + resourceNameInto );
        resourceInto = Queries.getResourceID(resourceNameInto, TermEnum.AutoCreate.NO);
        System.out.println("-------- merging ... : ");
        MergeEntries.changeIdRessourceFromDB(resourceFrom.getIdResource(),resourceInto.getIdResource());
        System.out.println("-------- end of merging ");
        System.out.println("-------- try to remove : " + resourceNameFrom);
        ManageResource.remove(resourceNameFrom);
    }
}
