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
import org.olanto.myterm.coredb.entityclasses.Domains;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class ConceptsJpaController implements Serializable {

    public ConceptsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concepts concepts) {
        if (concepts.getDomainsCollection() == null) {
            concepts.setDomainsCollection(new ArrayList<Domains>());
        }
        if (concepts.getLangsetsCollection() == null) {
            concepts.setLangsetsCollection(new ArrayList<Langsets>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resources idResource = concepts.getIdResource();
            if (idResource != null) {
                idResource = em.getReference(idResource.getClass(), idResource.getIdResource());
                concepts.setIdResource(idResource);
            }
            Collection<Domains> attachedDomainsCollection = new ArrayList<Domains>();
            for (Domains domainsCollectionDomainsToAttach : concepts.getDomainsCollection()) {
                domainsCollectionDomainsToAttach = em.getReference(domainsCollectionDomainsToAttach.getClass(), domainsCollectionDomainsToAttach.getIdDomain());
                attachedDomainsCollection.add(domainsCollectionDomainsToAttach);
            }
            concepts.setDomainsCollection(attachedDomainsCollection);
            Collection<Langsets> attachedLangsetsCollection = new ArrayList<Langsets>();
            for (Langsets langsetsCollectionLangsetsToAttach : concepts.getLangsetsCollection()) {
                langsetsCollectionLangsetsToAttach = em.getReference(langsetsCollectionLangsetsToAttach.getClass(), langsetsCollectionLangsetsToAttach.getIdLangset());
                attachedLangsetsCollection.add(langsetsCollectionLangsetsToAttach);
            }
            concepts.setLangsetsCollection(attachedLangsetsCollection);
            em.persist(concepts);
            if (idResource != null) {
                idResource.getConceptsCollection().add(concepts);
                idResource = em.merge(idResource);
            }
            for (Domains domainsCollectionDomains : concepts.getDomainsCollection()) {
                domainsCollectionDomains.getConceptsCollection().add(concepts);
                domainsCollectionDomains = em.merge(domainsCollectionDomains);
            }
            for (Langsets langsetsCollectionLangsets : concepts.getLangsetsCollection()) {
                Concepts oldIdConceptOfLangsetsCollectionLangsets = langsetsCollectionLangsets.getIdConcept();
                langsetsCollectionLangsets.setIdConcept(concepts);
                langsetsCollectionLangsets = em.merge(langsetsCollectionLangsets);
                if (oldIdConceptOfLangsetsCollectionLangsets != null) {
                    oldIdConceptOfLangsetsCollectionLangsets.getLangsetsCollection().remove(langsetsCollectionLangsets);
                    oldIdConceptOfLangsetsCollectionLangsets = em.merge(oldIdConceptOfLangsetsCollectionLangsets);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concepts concepts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concepts persistentConcepts = em.find(Concepts.class, concepts.getIdConcept());
            Resources idResourceOld = persistentConcepts.getIdResource();
            Resources idResourceNew = concepts.getIdResource();
            Collection<Domains> domainsCollectionOld = persistentConcepts.getDomainsCollection();
            Collection<Domains> domainsCollectionNew = concepts.getDomainsCollection();
            Collection<Langsets> langsetsCollectionOld = persistentConcepts.getLangsetsCollection();
            Collection<Langsets> langsetsCollectionNew = concepts.getLangsetsCollection();
            List<String> illegalOrphanMessages = null;
            for (Langsets langsetsCollectionOldLangsets : langsetsCollectionOld) {
                if (!langsetsCollectionNew.contains(langsetsCollectionOldLangsets)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Langsets " + langsetsCollectionOldLangsets + " since its idConcept field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idResourceNew != null) {
                idResourceNew = em.getReference(idResourceNew.getClass(), idResourceNew.getIdResource());
                concepts.setIdResource(idResourceNew);
            }
            Collection<Domains> attachedDomainsCollectionNew = new ArrayList<Domains>();
            for (Domains domainsCollectionNewDomainsToAttach : domainsCollectionNew) {
                domainsCollectionNewDomainsToAttach = em.getReference(domainsCollectionNewDomainsToAttach.getClass(), domainsCollectionNewDomainsToAttach.getIdDomain());
                attachedDomainsCollectionNew.add(domainsCollectionNewDomainsToAttach);
            }
            domainsCollectionNew = attachedDomainsCollectionNew;
            concepts.setDomainsCollection(domainsCollectionNew);
            Collection<Langsets> attachedLangsetsCollectionNew = new ArrayList<Langsets>();
            for (Langsets langsetsCollectionNewLangsetsToAttach : langsetsCollectionNew) {
                langsetsCollectionNewLangsetsToAttach = em.getReference(langsetsCollectionNewLangsetsToAttach.getClass(), langsetsCollectionNewLangsetsToAttach.getIdLangset());
                attachedLangsetsCollectionNew.add(langsetsCollectionNewLangsetsToAttach);
            }
            langsetsCollectionNew = attachedLangsetsCollectionNew;
            concepts.setLangsetsCollection(langsetsCollectionNew);
            concepts = em.merge(concepts);
            if (idResourceOld != null && !idResourceOld.equals(idResourceNew)) {
                idResourceOld.getConceptsCollection().remove(concepts);
                idResourceOld = em.merge(idResourceOld);
            }
            if (idResourceNew != null && !idResourceNew.equals(idResourceOld)) {
                idResourceNew.getConceptsCollection().add(concepts);
                idResourceNew = em.merge(idResourceNew);
            }
            for (Domains domainsCollectionOldDomains : domainsCollectionOld) {
                if (!domainsCollectionNew.contains(domainsCollectionOldDomains)) {
                    domainsCollectionOldDomains.getConceptsCollection().remove(concepts);
                    domainsCollectionOldDomains = em.merge(domainsCollectionOldDomains);
                }
            }
            for (Domains domainsCollectionNewDomains : domainsCollectionNew) {
                if (!domainsCollectionOld.contains(domainsCollectionNewDomains)) {
                    domainsCollectionNewDomains.getConceptsCollection().add(concepts);
                    domainsCollectionNewDomains = em.merge(domainsCollectionNewDomains);
                }
            }
            for (Langsets langsetsCollectionNewLangsets : langsetsCollectionNew) {
                if (!langsetsCollectionOld.contains(langsetsCollectionNewLangsets)) {
                    Concepts oldIdConceptOfLangsetsCollectionNewLangsets = langsetsCollectionNewLangsets.getIdConcept();
                    langsetsCollectionNewLangsets.setIdConcept(concepts);
                    langsetsCollectionNewLangsets = em.merge(langsetsCollectionNewLangsets);
                    if (oldIdConceptOfLangsetsCollectionNewLangsets != null && !oldIdConceptOfLangsetsCollectionNewLangsets.equals(concepts)) {
                        oldIdConceptOfLangsetsCollectionNewLangsets.getLangsetsCollection().remove(langsetsCollectionNewLangsets);
                        oldIdConceptOfLangsetsCollectionNewLangsets = em.merge(oldIdConceptOfLangsetsCollectionNewLangsets);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = concepts.getIdConcept();
                if (findConcepts(id) == null) {
                    throw new NonexistentEntityException("The concepts with id " + id + " no longer exists.");
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
            Concepts concepts;
            try {
                concepts = em.getReference(Concepts.class, id);
                concepts.getIdConcept();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concepts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Langsets> langsetsCollectionOrphanCheck = concepts.getLangsetsCollection();
            for (Langsets langsetsCollectionOrphanCheckLangsets : langsetsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Concepts (" + concepts + ") cannot be destroyed since the Langsets " + langsetsCollectionOrphanCheckLangsets + " in its langsetsCollection field has a non-nullable idConcept field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Resources idResource = concepts.getIdResource();
            if (idResource != null) {
                idResource.getConceptsCollection().remove(concepts);
                idResource = em.merge(idResource);
            }
            Collection<Domains> domainsCollection = concepts.getDomainsCollection();
            for (Domains domainsCollectionDomains : domainsCollection) {
                domainsCollectionDomains.getConceptsCollection().remove(concepts);
                domainsCollectionDomains = em.merge(domainsCollectionDomains);
            }
            em.remove(concepts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concepts> findConceptsEntities() {
        return findConceptsEntities(true, -1, -1);
    }

    public List<Concepts> findConceptsEntities(int maxResults, int firstResult) {
        return findConceptsEntities(false, maxResults, firstResult);
    }

    private List<Concepts> findConceptsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concepts.class));
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

    public Concepts findConcepts(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concepts.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concepts> rt = cq.from(Concepts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
