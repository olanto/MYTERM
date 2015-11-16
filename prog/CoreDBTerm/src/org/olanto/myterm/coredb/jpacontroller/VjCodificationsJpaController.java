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
import org.olanto.myterm.coredb.entityclasses.VjCodifications;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class VjCodificationsJpaController implements Serializable {

    public VjCodificationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VjCodifications vjCodifications) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vjCodifications);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVjCodifications(vjCodifications.getUuid()) != null) {
                throw new PreexistingEntityException("VjCodifications " + vjCodifications + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VjCodifications vjCodifications) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vjCodifications = em.merge(vjCodifications);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vjCodifications.getUuid();
                if (findVjCodifications(id) == null) {
                    throw new NonexistentEntityException("The vjCodifications with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VjCodifications vjCodifications;
            try {
                vjCodifications = em.getReference(VjCodifications.class, id);
                vjCodifications.getUuid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vjCodifications with id " + id + " no longer exists.", enfe);
            }
            em.remove(vjCodifications);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VjCodifications> findVjCodificationsEntities() {
        return findVjCodificationsEntities(true, -1, -1);
    }

    public List<VjCodifications> findVjCodificationsEntities(int maxResults, int firstResult) {
        return findVjCodificationsEntities(false, maxResults, firstResult);
    }

    private List<VjCodifications> findVjCodificationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VjCodifications.class));
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

    public VjCodifications findVjCodifications(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VjCodifications.class, id);
        } finally {
            em.close();
        }
    }

    public int getVjCodificationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VjCodifications> rt = cq.from(VjCodifications.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
