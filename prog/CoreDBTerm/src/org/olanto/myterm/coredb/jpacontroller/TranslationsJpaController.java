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
import org.olanto.myterm.coredb.entityclasses.Translations;
import org.olanto.myterm.coredb.entityclasses.TranslationsPK;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class TranslationsJpaController implements Serializable {

    public TranslationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Translations translations) throws PreexistingEntityException, Exception {
        if (translations.getTranslationsPK() == null) {
            translations.setTranslationsPK(new TranslationsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(translations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTranslations(translations.getTranslationsPK()) != null) {
                throw new PreexistingEntityException("Translations " + translations + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Translations translations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            translations = em.merge(translations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TranslationsPK id = translations.getTranslationsPK();
                if (findTranslations(id) == null) {
                    throw new NonexistentEntityException("The translations with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TranslationsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Translations translations;
            try {
                translations = em.getReference(Translations.class, id);
                translations.getTranslationsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The translations with id " + id + " no longer exists.", enfe);
            }
            em.remove(translations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Translations> findTranslationsEntities() {
        return findTranslationsEntities(true, -1, -1);
    }

    public List<Translations> findTranslationsEntities(int maxResults, int firstResult) {
        return findTranslationsEntities(false, maxResults, firstResult);
    }

    private List<Translations> findTranslationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Translations.class));
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

    public Translations findTranslations(TranslationsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Translations.class, id);
        } finally {
            em.close();
        }
    }

    public int getTranslationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Translations> rt = cq.from(Translations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
