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
import org.olanto.myterm.coredb.entityclasses.ResourcesDomains;
import org.olanto.myterm.coredb.entityclasses.ResourcesDomainsPK;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class ResourcesDomainsJpaController implements Serializable {

    public ResourcesDomainsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResourcesDomains resourcesDomains) throws PreexistingEntityException, Exception {
        if (resourcesDomains.getResourcesDomainsPK() == null) {
            resourcesDomains.setResourcesDomainsPK(new ResourcesDomainsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(resourcesDomains);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResourcesDomains(resourcesDomains.getResourcesDomainsPK()) != null) {
                throw new PreexistingEntityException("ResourcesDomains " + resourcesDomains + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResourcesDomains resourcesDomains) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            resourcesDomains = em.merge(resourcesDomains);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ResourcesDomainsPK id = resourcesDomains.getResourcesDomainsPK();
                if (findResourcesDomains(id) == null) {
                    throw new NonexistentEntityException("The resourcesDomains with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResourcesDomainsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResourcesDomains resourcesDomains;
            try {
                resourcesDomains = em.getReference(ResourcesDomains.class, id);
                resourcesDomains.getResourcesDomainsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resourcesDomains with id " + id + " no longer exists.", enfe);
            }
            em.remove(resourcesDomains);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResourcesDomains> findResourcesDomainsEntities() {
        return findResourcesDomainsEntities(true, -1, -1);
    }

    public List<ResourcesDomains> findResourcesDomainsEntities(int maxResults, int firstResult) {
        return findResourcesDomainsEntities(false, maxResults, firstResult);
    }

    private List<ResourcesDomains> findResourcesDomainsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResourcesDomains.class));
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

    public ResourcesDomains findResourcesDomains(ResourcesDomainsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResourcesDomains.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourcesDomainsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResourcesDomains> rt = cq.from(ResourcesDomains.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
