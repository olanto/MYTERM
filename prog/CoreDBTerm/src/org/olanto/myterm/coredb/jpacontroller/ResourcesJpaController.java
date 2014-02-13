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
package org.olanto.myterm.coredb.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Domains;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ResourcesJpaController implements Serializable {

    public ResourcesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resources resources) {
        if (resources.getDomainsCollection() == null) {
            resources.setDomainsCollection(new ArrayList<Domains>());
        }
        if (resources.getConceptsCollection() == null) {
            resources.setConceptsCollection(new ArrayList<Concepts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Owners idOwner = resources.getIdOwner();
            if (idOwner != null) {
                idOwner = em.getReference(idOwner.getClass(), idOwner.getIdOwner());
                resources.setIdOwner(idOwner);
            }
            Collection<Domains> attachedDomainsCollection = new ArrayList<Domains>();
            for (Domains domainsCollectionDomainsToAttach : resources.getDomainsCollection()) {
                domainsCollectionDomainsToAttach = em.getReference(domainsCollectionDomainsToAttach.getClass(), domainsCollectionDomainsToAttach.getIdDomain());
                attachedDomainsCollection.add(domainsCollectionDomainsToAttach);
            }
            resources.setDomainsCollection(attachedDomainsCollection);
            Collection<Concepts> attachedConceptsCollection = new ArrayList<Concepts>();
            for (Concepts conceptsCollectionConceptsToAttach : resources.getConceptsCollection()) {
                conceptsCollectionConceptsToAttach = em.getReference(conceptsCollectionConceptsToAttach.getClass(), conceptsCollectionConceptsToAttach.getIdConcept());
                attachedConceptsCollection.add(conceptsCollectionConceptsToAttach);
            }
            resources.setConceptsCollection(attachedConceptsCollection);
            em.persist(resources);
            if (idOwner != null) {
                idOwner.getResourcesCollection().add(resources);
                idOwner = em.merge(idOwner);
            }
            for (Domains domainsCollectionDomains : resources.getDomainsCollection()) {
                domainsCollectionDomains.getResourcesCollection().add(resources);
                domainsCollectionDomains = em.merge(domainsCollectionDomains);
            }
            for (Concepts conceptsCollectionConcepts : resources.getConceptsCollection()) {
                Resources oldIdResourceOfConceptsCollectionConcepts = conceptsCollectionConcepts.getIdResource();
                conceptsCollectionConcepts.setIdResource(resources);
                conceptsCollectionConcepts = em.merge(conceptsCollectionConcepts);
                if (oldIdResourceOfConceptsCollectionConcepts != null) {
                    oldIdResourceOfConceptsCollectionConcepts.getConceptsCollection().remove(conceptsCollectionConcepts);
                    oldIdResourceOfConceptsCollectionConcepts = em.merge(oldIdResourceOfConceptsCollectionConcepts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resources resources) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resources persistentResources = em.find(Resources.class, resources.getIdResource());
            Owners idOwnerOld = persistentResources.getIdOwner();
            Owners idOwnerNew = resources.getIdOwner();
            Collection<Domains> domainsCollectionOld = persistentResources.getDomainsCollection();
            Collection<Domains> domainsCollectionNew = resources.getDomainsCollection();
            Collection<Concepts> conceptsCollectionOld = persistentResources.getConceptsCollection();
            Collection<Concepts> conceptsCollectionNew = resources.getConceptsCollection();
            List<String> illegalOrphanMessages = null;
            for (Concepts conceptsCollectionOldConcepts : conceptsCollectionOld) {
                if (!conceptsCollectionNew.contains(conceptsCollectionOldConcepts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Concepts " + conceptsCollectionOldConcepts + " since its idResource field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idOwnerNew != null) {
                idOwnerNew = em.getReference(idOwnerNew.getClass(), idOwnerNew.getIdOwner());
                resources.setIdOwner(idOwnerNew);
            }
            Collection<Domains> attachedDomainsCollectionNew = new ArrayList<Domains>();
            for (Domains domainsCollectionNewDomainsToAttach : domainsCollectionNew) {
                domainsCollectionNewDomainsToAttach = em.getReference(domainsCollectionNewDomainsToAttach.getClass(), domainsCollectionNewDomainsToAttach.getIdDomain());
                attachedDomainsCollectionNew.add(domainsCollectionNewDomainsToAttach);
            }
            domainsCollectionNew = attachedDomainsCollectionNew;
            resources.setDomainsCollection(domainsCollectionNew);
            Collection<Concepts> attachedConceptsCollectionNew = new ArrayList<Concepts>();
            for (Concepts conceptsCollectionNewConceptsToAttach : conceptsCollectionNew) {
                conceptsCollectionNewConceptsToAttach = em.getReference(conceptsCollectionNewConceptsToAttach.getClass(), conceptsCollectionNewConceptsToAttach.getIdConcept());
                attachedConceptsCollectionNew.add(conceptsCollectionNewConceptsToAttach);
            }
            conceptsCollectionNew = attachedConceptsCollectionNew;
            resources.setConceptsCollection(conceptsCollectionNew);
            resources = em.merge(resources);
            if (idOwnerOld != null && !idOwnerOld.equals(idOwnerNew)) {
                idOwnerOld.getResourcesCollection().remove(resources);
                idOwnerOld = em.merge(idOwnerOld);
            }
            if (idOwnerNew != null && !idOwnerNew.equals(idOwnerOld)) {
                idOwnerNew.getResourcesCollection().add(resources);
                idOwnerNew = em.merge(idOwnerNew);
            }
            for (Domains domainsCollectionOldDomains : domainsCollectionOld) {
                if (!domainsCollectionNew.contains(domainsCollectionOldDomains)) {
                    domainsCollectionOldDomains.getResourcesCollection().remove(resources);
                    domainsCollectionOldDomains = em.merge(domainsCollectionOldDomains);
                }
            }
            for (Domains domainsCollectionNewDomains : domainsCollectionNew) {
                if (!domainsCollectionOld.contains(domainsCollectionNewDomains)) {
                    domainsCollectionNewDomains.getResourcesCollection().add(resources);
                    domainsCollectionNewDomains = em.merge(domainsCollectionNewDomains);
                }
            }
            for (Concepts conceptsCollectionNewConcepts : conceptsCollectionNew) {
                if (!conceptsCollectionOld.contains(conceptsCollectionNewConcepts)) {
                    Resources oldIdResourceOfConceptsCollectionNewConcepts = conceptsCollectionNewConcepts.getIdResource();
                    conceptsCollectionNewConcepts.setIdResource(resources);
                    conceptsCollectionNewConcepts = em.merge(conceptsCollectionNewConcepts);
                    if (oldIdResourceOfConceptsCollectionNewConcepts != null && !oldIdResourceOfConceptsCollectionNewConcepts.equals(resources)) {
                        oldIdResourceOfConceptsCollectionNewConcepts.getConceptsCollection().remove(conceptsCollectionNewConcepts);
                        oldIdResourceOfConceptsCollectionNewConcepts = em.merge(oldIdResourceOfConceptsCollectionNewConcepts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = resources.getIdResource();
                if (findResources(id) == null) {
                    throw new NonexistentEntityException("The resources with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resources resources;
            try {
                resources = em.getReference(Resources.class, id);
                resources.getIdResource();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resources with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Concepts> conceptsCollectionOrphanCheck = resources.getConceptsCollection();
            for (Concepts conceptsCollectionOrphanCheckConcepts : conceptsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Resources (" + resources + ") cannot be destroyed since the Concepts " + conceptsCollectionOrphanCheckConcepts + " in its conceptsCollection field has a non-nullable idResource field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Owners idOwner = resources.getIdOwner();
            if (idOwner != null) {
                idOwner.getResourcesCollection().remove(resources);
                idOwner = em.merge(idOwner);
            }
            Collection<Domains> domainsCollection = resources.getDomainsCollection();
            for (Domains domainsCollectionDomains : domainsCollection) {
                domainsCollectionDomains.getResourcesCollection().remove(resources);
                domainsCollectionDomains = em.merge(domainsCollectionDomains);
            }
            em.remove(resources);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resources> findResourcesEntities() {
        return findResourcesEntities(true, -1, -1);
    }

    public List<Resources> findResourcesEntities(int maxResults, int firstResult) {
        return findResourcesEntities(false, maxResults, firstResult);
    }

    private List<Resources> findResourcesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resources.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Resources findResources(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resources.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourcesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resources> rt = cq.from(Resources.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
