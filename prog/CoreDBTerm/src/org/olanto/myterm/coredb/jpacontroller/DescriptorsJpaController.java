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
import org.olanto.myterm.coredb.entityclasses.Descriptors;
import org.olanto.myterm.coredb.entityclasses.DescriptorsPK;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class DescriptorsJpaController implements Serializable {

    public DescriptorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Descriptors descriptors) throws PreexistingEntityException, Exception {
        if (descriptors.getDescriptorsPK() == null) {
            descriptors.setDescriptorsPK(new DescriptorsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(descriptors);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDescriptors(descriptors.getDescriptorsPK()) != null) {
                throw new PreexistingEntityException("Descriptors " + descriptors + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Descriptors descriptors) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            descriptors = em.merge(descriptors);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DescriptorsPK id = descriptors.getDescriptorsPK();
                if (findDescriptors(id) == null) {
                    throw new NonexistentEntityException("The descriptors with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DescriptorsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Descriptors descriptors;
            try {
                descriptors = em.getReference(Descriptors.class, id);
                descriptors.getDescriptorsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The descriptors with id " + id + " no longer exists.", enfe);
            }
            em.remove(descriptors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Descriptors> findDescriptorsEntities() {
        return findDescriptorsEntities(true, -1, -1);
    }

    public List<Descriptors> findDescriptorsEntities(int maxResults, int firstResult) {
        return findDescriptorsEntities(false, maxResults, firstResult);
    }

    private List<Descriptors> findDescriptorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Descriptors.class));
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

    public Descriptors findDescriptors(DescriptorsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Descriptors.class, id);
        } finally {
            em.close();
        }
    }

    public int getDescriptorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Descriptors> rt = cq.from(Descriptors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
