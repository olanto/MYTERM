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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.jpacontroller.exceptions.NonexistentEntityException;

/**
 *
 * @author simple
 */
public class TermsJpaController implements Serializable {

    public TermsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terms terms) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Owners lastmodifiedBy = terms.getLastmodifiedBy();
            if (lastmodifiedBy != null) {
                lastmodifiedBy = em.getReference(lastmodifiedBy.getClass(), lastmodifiedBy.getIdOwner());
                terms.setLastmodifiedBy(lastmodifiedBy);
            }
            Owners createBy = terms.getCreateBy();
            if (createBy != null) {
                createBy = em.getReference(createBy.getClass(), createBy.getIdOwner());
                terms.setCreateBy(createBy);
            }
            Languages idLanguage = terms.getIdLanguage();
            if (idLanguage != null) {
                idLanguage = em.getReference(idLanguage.getClass(), idLanguage.getIdLanguage());
                terms.setIdLanguage(idLanguage);
            }
            Langsets idLangset = terms.getIdLangset();
            if (idLangset != null) {
                idLangset = em.getReference(idLangset.getClass(), idLangset.getIdLangset());
                terms.setIdLangset(idLangset);
            }
            em.persist(terms);
            if (lastmodifiedBy != null) {
                lastmodifiedBy.getTermsCollection().add(terms);
                lastmodifiedBy = em.merge(lastmodifiedBy);
            }
            if (createBy != null) {
                createBy.getTermsCollection().add(terms);
                createBy = em.merge(createBy);
            }
            if (idLanguage != null) {
                idLanguage.getTermsCollection().add(terms);
                idLanguage = em.merge(idLanguage);
            }
            if (idLangset != null) {
                idLangset.getTermsCollection().add(terms);
                idLangset = em.merge(idLangset);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terms terms) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terms persistentTerms = em.find(Terms.class, terms.getIdTerm());
            Owners lastmodifiedByOld = persistentTerms.getLastmodifiedBy();
            Owners lastmodifiedByNew = terms.getLastmodifiedBy();
            Owners createByOld = persistentTerms.getCreateBy();
            Owners createByNew = terms.getCreateBy();
            Languages idLanguageOld = persistentTerms.getIdLanguage();
            Languages idLanguageNew = terms.getIdLanguage();
            Langsets idLangsetOld = persistentTerms.getIdLangset();
            Langsets idLangsetNew = terms.getIdLangset();
            if (lastmodifiedByNew != null) {
                lastmodifiedByNew = em.getReference(lastmodifiedByNew.getClass(), lastmodifiedByNew.getIdOwner());
                terms.setLastmodifiedBy(lastmodifiedByNew);
            }
            if (createByNew != null) {
                createByNew = em.getReference(createByNew.getClass(), createByNew.getIdOwner());
                terms.setCreateBy(createByNew);
            }
            if (idLanguageNew != null) {
                idLanguageNew = em.getReference(idLanguageNew.getClass(), idLanguageNew.getIdLanguage());
                terms.setIdLanguage(idLanguageNew);
            }
            if (idLangsetNew != null) {
                idLangsetNew = em.getReference(idLangsetNew.getClass(), idLangsetNew.getIdLangset());
                terms.setIdLangset(idLangsetNew);
            }
            terms = em.merge(terms);
            if (lastmodifiedByOld != null && !lastmodifiedByOld.equals(lastmodifiedByNew)) {
                lastmodifiedByOld.getTermsCollection().remove(terms);
                lastmodifiedByOld = em.merge(lastmodifiedByOld);
            }
            if (lastmodifiedByNew != null && !lastmodifiedByNew.equals(lastmodifiedByOld)) {
                lastmodifiedByNew.getTermsCollection().add(terms);
                lastmodifiedByNew = em.merge(lastmodifiedByNew);
            }
            if (createByOld != null && !createByOld.equals(createByNew)) {
                createByOld.getTermsCollection().remove(terms);
                createByOld = em.merge(createByOld);
            }
            if (createByNew != null && !createByNew.equals(createByOld)) {
                createByNew.getTermsCollection().add(terms);
                createByNew = em.merge(createByNew);
            }
            if (idLanguageOld != null && !idLanguageOld.equals(idLanguageNew)) {
                idLanguageOld.getTermsCollection().remove(terms);
                idLanguageOld = em.merge(idLanguageOld);
            }
            if (idLanguageNew != null && !idLanguageNew.equals(idLanguageOld)) {
                idLanguageNew.getTermsCollection().add(terms);
                idLanguageNew = em.merge(idLanguageNew);
            }
            if (idLangsetOld != null && !idLangsetOld.equals(idLangsetNew)) {
                idLangsetOld.getTermsCollection().remove(terms);
                idLangsetOld = em.merge(idLangsetOld);
            }
            if (idLangsetNew != null && !idLangsetNew.equals(idLangsetOld)) {
                idLangsetNew.getTermsCollection().add(terms);
                idLangsetNew = em.merge(idLangsetNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = terms.getIdTerm();
                if (findTerms(id) == null) {
                    throw new NonexistentEntityException("The terms with id " + id + " no longer exists.");
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
            Terms terms;
            try {
                terms = em.getReference(Terms.class, id);
                terms.getIdTerm();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terms with id " + id + " no longer exists.", enfe);
            }
            Owners lastmodifiedBy = terms.getLastmodifiedBy();
            if (lastmodifiedBy != null) {
                lastmodifiedBy.getTermsCollection().remove(terms);
                lastmodifiedBy = em.merge(lastmodifiedBy);
            }
            Owners createBy = terms.getCreateBy();
            if (createBy != null) {
                createBy.getTermsCollection().remove(terms);
                createBy = em.merge(createBy);
            }
            Languages idLanguage = terms.getIdLanguage();
            if (idLanguage != null) {
                idLanguage.getTermsCollection().remove(terms);
                idLanguage = em.merge(idLanguage);
            }
            Langsets idLangset = terms.getIdLangset();
            if (idLangset != null) {
                idLangset.getTermsCollection().remove(terms);
                idLangset = em.merge(idLangset);
            }
            em.remove(terms);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terms> findTermsEntities() {
        return findTermsEntities(true, -1, -1);
    }

    public List<Terms> findTermsEntities(int maxResults, int firstResult) {
        return findTermsEntities(false, maxResults, firstResult);
    }

    private List<Terms> findTermsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Terms.class));
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

    public Terms findTerms(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terms.class, id);
        } finally {
            em.close();
        }
    }

    public int getTermsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Terms> rt = cq.from(Terms.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
