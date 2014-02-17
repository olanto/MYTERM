/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.jpacontroller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.olanto.myterm.coredb.entityclasses.ConceptsDomains;
import org.olanto.myterm.coredb.entityclasses.ConceptsDomainsPK;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class ConceptsDomainsJpaController implements Serializable {

    public ConceptsDomainsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConceptsDomains conceptsDomains) throws PreexistingEntityException, Exception {
        if (conceptsDomains.getConceptsDomainsPK() == null) {
            conceptsDomains.setConceptsDomainsPK(new ConceptsDomainsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(conceptsDomains);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConceptsDomains(conceptsDomains.getConceptsDomainsPK()) != null) {
                throw new PreexistingEntityException("ConceptsDomains " + conceptsDomains + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConceptsDomains conceptsDomains) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            conceptsDomains = em.merge(conceptsDomains);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ConceptsDomainsPK id = conceptsDomains.getConceptsDomainsPK();
                if (findConceptsDomains(id) == null) {
                    throw new NonexistentEntityException("The conceptsDomains with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConceptsDomainsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConceptsDomains conceptsDomains;
            try {
                conceptsDomains = em.getReference(ConceptsDomains.class, id);
                conceptsDomains.getConceptsDomainsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conceptsDomains with id " + id + " no longer exists.", enfe);
            }
            em.remove(conceptsDomains);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConceptsDomains> findConceptsDomainsEntities() {
        return findConceptsDomainsEntities(true, -1, -1);
    }

    public List<ConceptsDomains> findConceptsDomainsEntities(int maxResults, int firstResult) {
        return findConceptsDomainsEntities(false, maxResults, firstResult);
    }

    private List<ConceptsDomains> findConceptsDomainsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConceptsDomains.class));
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

    public ConceptsDomains findConceptsDomains(ConceptsDomainsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConceptsDomains.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptsDomainsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConceptsDomains> rt = cq.from(ConceptsDomains.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
