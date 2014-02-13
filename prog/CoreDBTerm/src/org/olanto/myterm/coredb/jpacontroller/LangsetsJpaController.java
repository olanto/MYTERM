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
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Terms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class LangsetsJpaController implements Serializable {

    public LangsetsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Langsets langsets) {
        if (langsets.getTermsCollection() == null) {
            langsets.setTermsCollection(new ArrayList<Terms>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Languages idLanguage = langsets.getIdLanguage();
            if (idLanguage != null) {
                idLanguage = em.getReference(idLanguage.getClass(), idLanguage.getIdLanguage());
                langsets.setIdLanguage(idLanguage);
            }
            Concepts idConcept = langsets.getIdConcept();
            if (idConcept != null) {
                idConcept = em.getReference(idConcept.getClass(), idConcept.getIdConcept());
                langsets.setIdConcept(idConcept);
            }
            Collection<Terms> attachedTermsCollection = new ArrayList<Terms>();
            for (Terms termsCollectionTermsToAttach : langsets.getTermsCollection()) {
                termsCollectionTermsToAttach = em.getReference(termsCollectionTermsToAttach.getClass(), termsCollectionTermsToAttach.getIdTerm());
                attachedTermsCollection.add(termsCollectionTermsToAttach);
            }
            langsets.setTermsCollection(attachedTermsCollection);
            em.persist(langsets);
            if (idLanguage != null) {
                idLanguage.getLangsetsCollection().add(langsets);
                idLanguage = em.merge(idLanguage);
            }
            if (idConcept != null) {
                idConcept.getLangsetsCollection().add(langsets);
                idConcept = em.merge(idConcept);
            }
            for (Terms termsCollectionTerms : langsets.getTermsCollection()) {
                Langsets oldIdLangsetOfTermsCollectionTerms = termsCollectionTerms.getIdLangset();
                termsCollectionTerms.setIdLangset(langsets);
                termsCollectionTerms = em.merge(termsCollectionTerms);
                if (oldIdLangsetOfTermsCollectionTerms != null) {
                    oldIdLangsetOfTermsCollectionTerms.getTermsCollection().remove(termsCollectionTerms);
                    oldIdLangsetOfTermsCollectionTerms = em.merge(oldIdLangsetOfTermsCollectionTerms);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Langsets langsets) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Langsets persistentLangsets = em.find(Langsets.class, langsets.getIdLangset());
            Languages idLanguageOld = persistentLangsets.getIdLanguage();
            Languages idLanguageNew = langsets.getIdLanguage();
            Concepts idConceptOld = persistentLangsets.getIdConcept();
            Concepts idConceptNew = langsets.getIdConcept();
            Collection<Terms> termsCollectionOld = persistentLangsets.getTermsCollection();
            Collection<Terms> termsCollectionNew = langsets.getTermsCollection();
            List<String> illegalOrphanMessages = null;
            for (Terms termsCollectionOldTerms : termsCollectionOld) {
                if (!termsCollectionNew.contains(termsCollectionOldTerms)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Terms " + termsCollectionOldTerms + " since its idLangset field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLanguageNew != null) {
                idLanguageNew = em.getReference(idLanguageNew.getClass(), idLanguageNew.getIdLanguage());
                langsets.setIdLanguage(idLanguageNew);
            }
            if (idConceptNew != null) {
                idConceptNew = em.getReference(idConceptNew.getClass(), idConceptNew.getIdConcept());
                langsets.setIdConcept(idConceptNew);
            }
            Collection<Terms> attachedTermsCollectionNew = new ArrayList<Terms>();
            for (Terms termsCollectionNewTermsToAttach : termsCollectionNew) {
                termsCollectionNewTermsToAttach = em.getReference(termsCollectionNewTermsToAttach.getClass(), termsCollectionNewTermsToAttach.getIdTerm());
                attachedTermsCollectionNew.add(termsCollectionNewTermsToAttach);
            }
            termsCollectionNew = attachedTermsCollectionNew;
            langsets.setTermsCollection(termsCollectionNew);
            langsets = em.merge(langsets);
            if (idLanguageOld != null && !idLanguageOld.equals(idLanguageNew)) {
                idLanguageOld.getLangsetsCollection().remove(langsets);
                idLanguageOld = em.merge(idLanguageOld);
            }
            if (idLanguageNew != null && !idLanguageNew.equals(idLanguageOld)) {
                idLanguageNew.getLangsetsCollection().add(langsets);
                idLanguageNew = em.merge(idLanguageNew);
            }
            if (idConceptOld != null && !idConceptOld.equals(idConceptNew)) {
                idConceptOld.getLangsetsCollection().remove(langsets);
                idConceptOld = em.merge(idConceptOld);
            }
            if (idConceptNew != null && !idConceptNew.equals(idConceptOld)) {
                idConceptNew.getLangsetsCollection().add(langsets);
                idConceptNew = em.merge(idConceptNew);
            }
            for (Terms termsCollectionNewTerms : termsCollectionNew) {
                if (!termsCollectionOld.contains(termsCollectionNewTerms)) {
                    Langsets oldIdLangsetOfTermsCollectionNewTerms = termsCollectionNewTerms.getIdLangset();
                    termsCollectionNewTerms.setIdLangset(langsets);
                    termsCollectionNewTerms = em.merge(termsCollectionNewTerms);
                    if (oldIdLangsetOfTermsCollectionNewTerms != null && !oldIdLangsetOfTermsCollectionNewTerms.equals(langsets)) {
                        oldIdLangsetOfTermsCollectionNewTerms.getTermsCollection().remove(termsCollectionNewTerms);
                        oldIdLangsetOfTermsCollectionNewTerms = em.merge(oldIdLangsetOfTermsCollectionNewTerms);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = langsets.getIdLangset();
                if (findLangsets(id) == null) {
                    throw new NonexistentEntityException("The langsets with id " + id + " no longer exists.");
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
            Langsets langsets;
            try {
                langsets = em.getReference(Langsets.class, id);
                langsets.getIdLangset();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The langsets with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Terms> termsCollectionOrphanCheck = langsets.getTermsCollection();
            for (Terms termsCollectionOrphanCheckTerms : termsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Langsets (" + langsets + ") cannot be destroyed since the Terms " + termsCollectionOrphanCheckTerms + " in its termsCollection field has a non-nullable idLangset field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Languages idLanguage = langsets.getIdLanguage();
            if (idLanguage != null) {
                idLanguage.getLangsetsCollection().remove(langsets);
                idLanguage = em.merge(idLanguage);
            }
            Concepts idConcept = langsets.getIdConcept();
            if (idConcept != null) {
                idConcept.getLangsetsCollection().remove(langsets);
                idConcept = em.merge(idConcept);
            }
            em.remove(langsets);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Langsets> findLangsetsEntities() {
        return findLangsetsEntities(true, -1, -1);
    }

    public List<Langsets> findLangsetsEntities(int maxResults, int firstResult) {
        return findLangsetsEntities(false, maxResults, firstResult);
    }

    private List<Langsets> findLangsetsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Langsets.class));
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

    public Langsets findLangsets(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Langsets.class, id);
        } finally {
            em.close();
        }
    }

    public int getLangsetsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Langsets> rt = cq.from(Langsets.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
