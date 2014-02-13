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
import org.olanto.myterm.coredb.entityclasses.Concepts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Domains;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class DomainsJpaController implements Serializable {

    public DomainsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Domains domains) {
        if (domains.getConceptsCollection() == null) {
            domains.setConceptsCollection(new ArrayList<Concepts>());
        }
        if (domains.getResourcesCollection() == null) {
            domains.setResourcesCollection(new ArrayList<Resources>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Concepts> attachedConceptsCollection = new ArrayList<Concepts>();
            for (Concepts conceptsCollectionConceptsToAttach : domains.getConceptsCollection()) {
                conceptsCollectionConceptsToAttach = em.getReference(conceptsCollectionConceptsToAttach.getClass(), conceptsCollectionConceptsToAttach.getIdConcept());
                attachedConceptsCollection.add(conceptsCollectionConceptsToAttach);
            }
            domains.setConceptsCollection(attachedConceptsCollection);
            Collection<Resources> attachedResourcesCollection = new ArrayList<Resources>();
            for (Resources resourcesCollectionResourcesToAttach : domains.getResourcesCollection()) {
                resourcesCollectionResourcesToAttach = em.getReference(resourcesCollectionResourcesToAttach.getClass(), resourcesCollectionResourcesToAttach.getIdResource());
                attachedResourcesCollection.add(resourcesCollectionResourcesToAttach);
            }
            domains.setResourcesCollection(attachedResourcesCollection);
            em.persist(domains);
            for (Concepts conceptsCollectionConcepts : domains.getConceptsCollection()) {
                conceptsCollectionConcepts.getDomainsCollection().add(domains);
                conceptsCollectionConcepts = em.merge(conceptsCollectionConcepts);
            }
            for (Resources resourcesCollectionResources : domains.getResourcesCollection()) {
                resourcesCollectionResources.getDomainsCollection().add(domains);
                resourcesCollectionResources = em.merge(resourcesCollectionResources);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Domains domains) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domains persistentDomains = em.find(Domains.class, domains.getIdDomain());
            Collection<Concepts> conceptsCollectionOld = persistentDomains.getConceptsCollection();
            Collection<Concepts> conceptsCollectionNew = domains.getConceptsCollection();
            Collection<Resources> resourcesCollectionOld = persistentDomains.getResourcesCollection();
            Collection<Resources> resourcesCollectionNew = domains.getResourcesCollection();
            Collection<Concepts> attachedConceptsCollectionNew = new ArrayList<Concepts>();
            for (Concepts conceptsCollectionNewConceptsToAttach : conceptsCollectionNew) {
                conceptsCollectionNewConceptsToAttach = em.getReference(conceptsCollectionNewConceptsToAttach.getClass(), conceptsCollectionNewConceptsToAttach.getIdConcept());
                attachedConceptsCollectionNew.add(conceptsCollectionNewConceptsToAttach);
            }
            conceptsCollectionNew = attachedConceptsCollectionNew;
            domains.setConceptsCollection(conceptsCollectionNew);
            Collection<Resources> attachedResourcesCollectionNew = new ArrayList<Resources>();
            for (Resources resourcesCollectionNewResourcesToAttach : resourcesCollectionNew) {
                resourcesCollectionNewResourcesToAttach = em.getReference(resourcesCollectionNewResourcesToAttach.getClass(), resourcesCollectionNewResourcesToAttach.getIdResource());
                attachedResourcesCollectionNew.add(resourcesCollectionNewResourcesToAttach);
            }
            resourcesCollectionNew = attachedResourcesCollectionNew;
            domains.setResourcesCollection(resourcesCollectionNew);
            domains = em.merge(domains);
            for (Concepts conceptsCollectionOldConcepts : conceptsCollectionOld) {
                if (!conceptsCollectionNew.contains(conceptsCollectionOldConcepts)) {
                    conceptsCollectionOldConcepts.getDomainsCollection().remove(domains);
                    conceptsCollectionOldConcepts = em.merge(conceptsCollectionOldConcepts);
                }
            }
            for (Concepts conceptsCollectionNewConcepts : conceptsCollectionNew) {
                if (!conceptsCollectionOld.contains(conceptsCollectionNewConcepts)) {
                    conceptsCollectionNewConcepts.getDomainsCollection().add(domains);
                    conceptsCollectionNewConcepts = em.merge(conceptsCollectionNewConcepts);
                }
            }
            for (Resources resourcesCollectionOldResources : resourcesCollectionOld) {
                if (!resourcesCollectionNew.contains(resourcesCollectionOldResources)) {
                    resourcesCollectionOldResources.getDomainsCollection().remove(domains);
                    resourcesCollectionOldResources = em.merge(resourcesCollectionOldResources);
                }
            }
            for (Resources resourcesCollectionNewResources : resourcesCollectionNew) {
                if (!resourcesCollectionOld.contains(resourcesCollectionNewResources)) {
                    resourcesCollectionNewResources.getDomainsCollection().add(domains);
                    resourcesCollectionNewResources = em.merge(resourcesCollectionNewResources);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = domains.getIdDomain();
                if (findDomains(id) == null) {
                    throw new NonexistentEntityException("The domains with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domains domains;
            try {
                domains = em.getReference(Domains.class, id);
                domains.getIdDomain();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domains with id " + id + " no longer exists.", enfe);
            }
            Collection<Concepts> conceptsCollection = domains.getConceptsCollection();
            for (Concepts conceptsCollectionConcepts : conceptsCollection) {
                conceptsCollectionConcepts.getDomainsCollection().remove(domains);
                conceptsCollectionConcepts = em.merge(conceptsCollectionConcepts);
            }
            Collection<Resources> resourcesCollection = domains.getResourcesCollection();
            for (Resources resourcesCollectionResources : resourcesCollection) {
                resourcesCollectionResources.getDomainsCollection().remove(domains);
                resourcesCollectionResources = em.merge(resourcesCollectionResources);
            }
            em.remove(domains);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Domains> findDomainsEntities() {
        return findDomainsEntities(true, -1, -1);
    }

    public List<Domains> findDomainsEntities(int maxResults, int firstResult) {
        return findDomainsEntities(false, maxResults, firstResult);
    }

    private List<Domains> findDomainsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Domains.class));
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

    public Domains findDomains(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Domains.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomainsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Domains> rt = cq.from(Domains.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
