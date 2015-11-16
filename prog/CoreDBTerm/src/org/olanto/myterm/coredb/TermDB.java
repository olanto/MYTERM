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
import org.olanto.myterm.coredb.jpacontroller.UsersLanguagesJpaController;
import org.olanto.myterm.coredb.jpacontroller.UsersResourcesJpaController;
import org.olanto.myterm.coredb.jpacontroller.VjCodificationsJpaController;

/**
 *
 * @author Jacques Guyot
 */
public class TermDB {

    static public String PU = "CoreDBTermPU";
    static public EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
    static public EntityManager em = emf.createEntityManager();
    static public DomainsJpaController domainsJC = new DomainsJpaController(TermDB.emf);
    static public OwnersJpaController ownersJC = new OwnersJpaController(TermDB.emf);
    static public ResourcesJpaController resourcesJC = new ResourcesJpaController(TermDB.emf);
    static public ConceptsJpaController conceptsJC = new ConceptsJpaController(TermDB.emf);
    static public LangsetsJpaController langsetsJC = new LangsetsJpaController(TermDB.emf);
    static public LanguagesJpaController languagesJC = new LanguagesJpaController(TermDB.emf);
    static public TermsJpaController termsJC = new TermsJpaController(TermDB.emf);
    static public UsersLanguagesJpaController usersLanguagesJC = new UsersLanguagesJpaController(TermDB.emf);
    static public UsersResourcesJpaController usersResourcesJC = new UsersResourcesJpaController(TermDB.emf);
   static public VjCodificationsJpaController vjCodificationsJC = new VjCodificationsJpaController(TermDB.emf);

    public static void restart() {
        emf = null;
        emf = Persistence.createEntityManagerFactory(PU);
        em = emf.createEntityManager();
        domainsJC = new DomainsJpaController(TermDB.emf);
        ownersJC = new OwnersJpaController(TermDB.emf);
        resourcesJC = new ResourcesJpaController(TermDB.emf);
        conceptsJC = new ConceptsJpaController(TermDB.emf);
        langsetsJC = new LangsetsJpaController(TermDB.emf);
        languagesJC = new LanguagesJpaController(TermDB.emf);
        termsJC = new TermsJpaController(TermDB.emf);
        usersLanguagesJC = new UsersLanguagesJpaController(TermDB.emf);
        usersResourcesJC = new UsersResourcesJpaController(TermDB.emf);
        vjCodificationsJC = new VjCodificationsJpaController(TermDB.emf);
    }
}
