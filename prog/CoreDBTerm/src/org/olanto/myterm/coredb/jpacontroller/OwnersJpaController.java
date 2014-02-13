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
import org.olanto.myterm.coredb.entityclasses.Resources;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class OwnersJpaController implements Serializable {

    public OwnersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Owners owners) {
        if (owners.getResourcesCollection() == null) {
            owners.setResourcesCollection(new ArrayList<Resources>());
        }
        if (owners.getTermsCollection() == null) {
            owners.setTermsCollection(new ArrayList<Terms>());
        }
        if (owners.getTermsCollection1() == null) {
            owners.setTermsCollection1(new ArrayList<Terms>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Resources> attachedResourcesCollection = new ArrayList<Resources>();
            for (Resources resourcesCollectionResourcesToAttach : owners.getResourcesCollection()) {
                resourcesCollectionResourcesToAttach = em.getReference(resourcesCollectionResourcesToAttach.getClass(), resourcesCollectionResourcesToAttach.getIdResource());
                attachedResourcesCollection.add(resourcesCollectionResourcesToAttach);
            }
            owners.setResourcesCollection(attachedResourcesCollection);
            Collection<Terms> attachedTermsCollection = new ArrayList<Terms>();
            for (Terms termsCollectionTermsToAttach : owners.getTermsCollection()) {
                termsCollectionTermsToAttach = em.getReference(termsCollectionTermsToAttach.getClass(), termsCollectionTermsToAttach.getIdTerm());
                attachedTermsCollection.add(termsCollectionTermsToAttach);
            }
            owners.setTermsCollection(attachedTermsCollection);
            Collection<Terms> attachedTermsCollection1 = new ArrayList<Terms>();
            for (Terms termsCollection1TermsToAttach : owners.getTermsCollection1()) {
                termsCollection1TermsToAttach = em.getReference(termsCollection1TermsToAttach.getClass(), termsCollection1TermsToAttach.getIdTerm());
                attachedTermsCollection1.add(termsCollection1TermsToAttach);
            }
            owners.setTermsCollection1(attachedTermsCollection1);
            em.persist(owners);
            for (Resources resourcesCollectionResources : owners.getResourcesCollection()) {
                Owners oldIdOwnerOfResourcesCollectionResources = resourcesCollectionResources.getIdOwner();
                resourcesCollectionResources.setIdOwner(owners);
                resourcesCollectionResources = em.merge(resourcesCollectionResources);
                if (oldIdOwnerOfResourcesCollectionResources != null) {
                    oldIdOwnerOfResourcesCollectionResources.getResourcesCollection().remove(resourcesCollectionResources);
                    oldIdOwnerOfResourcesCollectionResources = em.merge(oldIdOwnerOfResourcesCollectionResources);
                }
            }
            for (Terms termsCollectionTerms : owners.getTermsCollection()) {
                Owners oldLastmodifiedByOfTermsCollectionTerms = termsCollectionTerms.getLastmodifiedBy();
                termsCollectionTerms.setLastmodifiedBy(owners);
                termsCollectionTerms = em.merge(termsCollectionTerms);
                if (oldLastmodifiedByOfTermsCollectionTerms != null) {
                    oldLastmodifiedByOfTermsCollectionTerms.getTermsCollection().remove(termsCollectionTerms);
                    oldLastmodifiedByOfTermsCollectionTerms = em.merge(oldLastmodifiedByOfTermsCollectionTerms);
                }
            }
            for (Terms termsCollection1Terms : owners.getTermsCollection1()) {
                Owners oldCreateByOfTermsCollection1Terms = termsCollection1Terms.getCreateBy();
                termsCollection1Terms.setCreateBy(owners);
                termsCollection1Terms = em.merge(termsCollection1Terms);
                if (oldCreateByOfTermsCollection1Terms != null) {
                    oldCreateByOfTermsCollection1Terms.getTermsCollection1().remove(termsCollection1Terms);
                    oldCreateByOfTermsCollection1Terms = em.merge(oldCreateByOfTermsCollection1Terms);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Owners owners) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Owners persistentOwners = em.find(Owners.class, owners.getIdOwner());
            Collection<Resources> resourcesCollectionOld = persistentOwners.getResourcesCollection();
            Collection<Resources> resourcesCollectionNew = owners.getResourcesCollection();
            Collection<Terms> termsCollectionOld = persistentOwners.getTermsCollection();
            Collection<Terms> termsCollectionNew = owners.getTermsCollection();
            Collection<Terms> termsCollection1Old = persistentOwners.getTermsCollection1();
            Collection<Terms> termsCollection1New = owners.getTermsCollection1();
            List<String> illegalOrphanMessages = null;
            for (Resources resourcesCollectionOldResources : resourcesCollectionOld) {
                if (!resourcesCollectionNew.contains(resourcesCollectionOldResources)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Resources " + resourcesCollectionOldResources + " since its idOwner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Resources> attachedResourcesCollectionNew = new ArrayList<Resources>();
            for (Resources resourcesCollectionNewResourcesToAttach : resourcesCollectionNew) {
                resourcesCollectionNewResourcesToAttach = em.getReference(resourcesCollectionNewResourcesToAttach.getClass(), resourcesCollectionNewResourcesToAttach.getIdResource());
                attachedResourcesCollectionNew.add(resourcesCollectionNewResourcesToAttach);
            }
            resourcesCollectionNew = attachedResourcesCollectionNew;
            owners.setResourcesCollection(resourcesCollectionNew);
            Collection<Terms> attachedTermsCollectionNew = new ArrayList<Terms>();
            for (Terms termsCollectionNewTermsToAttach : termsCollectionNew) {
                termsCollectionNewTermsToAttach = em.getReference(termsCollectionNewTermsToAttach.getClass(), termsCollectionNewTermsToAttach.getIdTerm());
                attachedTermsCollectionNew.add(termsCollectionNewTermsToAttach);
            }
            termsCollectionNew = attachedTermsCollectionNew;
            owners.setTermsCollection(termsCollectionNew);
            Collection<Terms> attachedTermsCollection1New = new ArrayList<Terms>();
            for (Terms termsCollection1NewTermsToAttach : termsCollection1New) {
                termsCollection1NewTermsToAttach = em.getReference(termsCollection1NewTermsToAttach.getClass(), termsCollection1NewTermsToAttach.getIdTerm());
                attachedTermsCollection1New.add(termsCollection1NewTermsToAttach);
            }
            termsCollection1New = attachedTermsCollection1New;
            owners.setTermsCollection1(termsCollection1New);
            owners = em.merge(owners);
            for (Resources resourcesCollectionNewResources : resourcesCollectionNew) {
                if (!resourcesCollectionOld.contains(resourcesCollectionNewResources)) {
                    Owners oldIdOwnerOfResourcesCollectionNewResources = resourcesCollectionNewResources.getIdOwner();
                    resourcesCollectionNewResources.setIdOwner(owners);
                    resourcesCollectionNewResources = em.merge(resourcesCollectionNewResources);
                    if (oldIdOwnerOfResourcesCollectionNewResources != null && !oldIdOwnerOfResourcesCollectionNewResources.equals(owners)) {
                        oldIdOwnerOfResourcesCollectionNewResources.getResourcesCollection().remove(resourcesCollectionNewResources);
                        oldIdOwnerOfResourcesCollectionNewResources = em.merge(oldIdOwnerOfResourcesCollectionNewResources);
                    }
                }
            }
            for (Terms termsCollectionOldTerms : termsCollectionOld) {
                if (!termsCollectionNew.contains(termsCollectionOldTerms)) {
                    termsCollectionOldTerms.setLastmodifiedBy(null);
                    termsCollectionOldTerms = em.merge(termsCollectionOldTerms);
                }
            }
            for (Terms termsCollectionNewTerms : termsCollectionNew) {
                if (!termsCollectionOld.contains(termsCollectionNewTerms)) {
                    Owners oldLastmodifiedByOfTermsCollectionNewTerms = termsCollectionNewTerms.getLastmodifiedBy();
                    termsCollectionNewTerms.setLastmodifiedBy(owners);
                    termsCollectionNewTerms = em.merge(termsCollectionNewTerms);
                    if (oldLastmodifiedByOfTermsCollectionNewTerms != null && !oldLastmodifiedByOfTermsCollectionNewTerms.equals(owners)) {
                        oldLastmodifiedByOfTermsCollectionNewTerms.getTermsCollection().remove(termsCollectionNewTerms);
                        oldLastmodifiedByOfTermsCollectionNewTerms = em.merge(oldLastmodifiedByOfTermsCollectionNewTerms);
                    }
                }
            }
            for (Terms termsCollection1OldTerms : termsCollection1Old) {
                if (!termsCollection1New.contains(termsCollection1OldTerms)) {
                    termsCollection1OldTerms.setCreateBy(null);
                    termsCollection1OldTerms = em.merge(termsCollection1OldTerms);
                }
            }
            for (Terms termsCollection1NewTerms : termsCollection1New) {
                if (!termsCollection1Old.contains(termsCollection1NewTerms)) {
                    Owners oldCreateByOfTermsCollection1NewTerms = termsCollection1NewTerms.getCreateBy();
                    termsCollection1NewTerms.setCreateBy(owners);
                    termsCollection1NewTerms = em.merge(termsCollection1NewTerms);
                    if (oldCreateByOfTermsCollection1NewTerms != null && !oldCreateByOfTermsCollection1NewTerms.equals(owners)) {
                        oldCreateByOfTermsCollection1NewTerms.getTermsCollection1().remove(termsCollection1NewTerms);
                        oldCreateByOfTermsCollection1NewTerms = em.merge(oldCreateByOfTermsCollection1NewTerms);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = owners.getIdOwner();
                if (findOwners(id) == null) {
                    throw new NonexistentEntityException("The owners with id " + id + " no longer exists.");
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
            Owners owners;
            try {
                owners = em.getReference(Owners.class, id);
                owners.getIdOwner();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The owners with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Resources> resourcesCollectionOrphanCheck = owners.getResourcesCollection();
            for (Resources resourcesCollectionOrphanCheckResources : resourcesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Owners (" + owners + ") cannot be destroyed since the Resources " + resourcesCollectionOrphanCheckResources + " in its resourcesCollection field has a non-nullable idOwner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Terms> termsCollection = owners.getTermsCollection();
            for (Terms termsCollectionTerms : termsCollection) {
                termsCollectionTerms.setLastmodifiedBy(null);
                termsCollectionTerms = em.merge(termsCollectionTerms);
            }
            Collection<Terms> termsCollection1 = owners.getTermsCollection1();
            for (Terms termsCollection1Terms : termsCollection1) {
                termsCollection1Terms.setCreateBy(null);
                termsCollection1Terms = em.merge(termsCollection1Terms);
            }
            em.remove(owners);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Owners> findOwnersEntities() {
        return findOwnersEntities(true, -1, -1);
    }

    public List<Owners> findOwnersEntities(int maxResults, int firstResult) {
        return findOwnersEntities(false, maxResults, firstResult);
    }

    private List<Owners> findOwnersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Owners.class));
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

    public Owners findOwners(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Owners.class, id);
        } finally {
            em.close();
        }
    }

    public int getOwnersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Owners> rt = cq.from(Owners.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
