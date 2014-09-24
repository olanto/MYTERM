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
import org.olanto.myterm.coredb.entityclasses.UsersLanguages;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class UsersLanguagesJpaController implements Serializable {

    public UsersLanguagesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsersLanguages usersLanguages) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usersLanguages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsersLanguages usersLanguages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usersLanguages = em.merge(usersLanguages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usersLanguages.getIdLink();
                if (findUsersLanguages(id) == null) {
                    throw new NonexistentEntityException("The usersLanguages with id " + id + " no longer exists.");
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
            UsersLanguages usersLanguages;
            try {
                usersLanguages = em.getReference(UsersLanguages.class, id);
                usersLanguages.getIdLink();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usersLanguages with id " + id + " no longer exists.", enfe);
            }
            em.remove(usersLanguages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsersLanguages> findUsersLanguagesEntities() {
        return findUsersLanguagesEntities(true, -1, -1);
    }

    public List<UsersLanguages> findUsersLanguagesEntities(int maxResults, int firstResult) {
        return findUsersLanguagesEntities(false, maxResults, firstResult);
    }

    private List<UsersLanguages> findUsersLanguagesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsersLanguages.class));
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

    public UsersLanguages findUsersLanguages(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsersLanguages.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersLanguagesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsersLanguages> rt = cq.from(UsersLanguages.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
