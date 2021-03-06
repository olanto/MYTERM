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
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class LanguagesJpaController implements Serializable {

    public LanguagesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Languages languages) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(languages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLanguages(languages.getIdLanguage()) != null) {
                throw new PreexistingEntityException("Languages " + languages + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Languages languages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            languages = em.merge(languages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = languages.getIdLanguage();
                if (findLanguages(id) == null) {
                    throw new NonexistentEntityException("The languages with id " + id + " no longer exists.");
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
            Languages languages;
            try {
                languages = em.getReference(Languages.class, id);
                languages.getIdLanguage();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The languages with id " + id + " no longer exists.", enfe);
            }
            em.remove(languages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Languages> findLanguagesEntities() {
        return findLanguagesEntities(true, -1, -1);
    }

    public List<Languages> findLanguagesEntities(int maxResults, int firstResult) {
        return findLanguagesEntities(false, maxResults, firstResult);
    }

    private List<Languages> findLanguagesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Languages.class));
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

    public Languages findLanguages(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Languages.class, id);
        } finally {
            em.close();
        }
    }

    public int getLanguagesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Languages> rt = cq.from(Languages.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
