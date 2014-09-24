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
import org.olanto.myterm.coredb.entityclasses.UsersResources;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class UsersResourcesJpaController implements Serializable {

    public UsersResourcesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsersResources usersResources) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usersResources);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsersResources usersResources) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usersResources = em.merge(usersResources);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usersResources.getIdLink();
                if (findUsersResources(id) == null) {
                    throw new NonexistentEntityException("The usersResources with id " + id + " no longer exists.");
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
            UsersResources usersResources;
            try {
                usersResources = em.getReference(UsersResources.class, id);
                usersResources.getIdLink();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usersResources with id " + id + " no longer exists.", enfe);
            }
            em.remove(usersResources);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsersResources> findUsersResourcesEntities() {
        return findUsersResourcesEntities(true, -1, -1);
    }

    public List<UsersResources> findUsersResourcesEntities(int maxResults, int firstResult) {
        return findUsersResourcesEntities(false, maxResults, firstResult);
    }

    private List<UsersResources> findUsersResourcesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsersResources.class));
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

    public UsersResources findUsersResources(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsersResources.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersResourcesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsersResources> rt = cq.from(UsersResources.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
