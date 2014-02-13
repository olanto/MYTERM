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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.olanto.myterm.coredb.jpacontroller.ConceptsJpaController;
import org.olanto.myterm.coredb.jpacontroller.DomainsJpaController;
import org.olanto.myterm.coredb.jpacontroller.LangsetsJpaController;
import org.olanto.myterm.coredb.jpacontroller.LanguagesJpaController;
import org.olanto.myterm.coredb.jpacontroller.OwnersJpaController;
import org.olanto.myterm.coredb.jpacontroller.ResourcesJpaController;
import org.olanto.myterm.coredb.jpacontroller.TermsJpaController;

/**
 *
 * @author Jacques Guyot
 */
public class TermDB {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CoreDBTermPU");
    static EntityManager em = emf.createEntityManager();
    static DomainsJpaController domainsJC = new DomainsJpaController(TermDB.emf);
    static OwnersJpaController ownersJC = new OwnersJpaController(TermDB.emf);
    static ResourcesJpaController resourcesJC = new ResourcesJpaController(TermDB.emf);
    static ConceptsJpaController conceptsJC = new ConceptsJpaController(TermDB.emf);
    static LangsetsJpaController langsetsJC = new LangsetsJpaController(TermDB.emf);
    static LanguagesJpaController languagesJC = new LanguagesJpaController(TermDB.emf);
    static TermsJpaController termsJC = new TermsJpaController(TermDB.emf);
}
