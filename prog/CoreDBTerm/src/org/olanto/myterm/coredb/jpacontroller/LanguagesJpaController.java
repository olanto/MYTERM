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
import org.olanto.myterm.coredb.entityclasses.Terms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.olanto.myterm.coredb.entityclasses.Translations;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.jpacontroller.exceptions.IllegalOrphanException;
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
        if (languages.getTermsCollection() == null) {
            languages.setTermsCollection(new ArrayList<Terms>());
        }
        if (languages.getTranslationsCollection() == null) {
            languages.setTranslationsCollection(new ArrayList<Translations>());
        }
        if (languages.getLangsetsCollection() == null) {
            languages.setLangsetsCollection(new ArrayList<Langsets>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Terms> attachedTermsCollection = new ArrayList<Terms>();
            for (Terms termsCollectionTermsToAttach : languages.getTermsCollection()) {
                termsCollectionTermsToAttach = em.getReference(termsCollectionTermsToAttach.getClass(), termsCollectionTermsToAttach.getIdTerm());
                attachedTermsCollection.add(termsCollectionTermsToAttach);
            }
            languages.setTermsCollection(attachedTermsCollection);
            Collection<Translations> attachedTranslationsCollection = new ArrayList<Translations>();
            for (Translations translationsCollectionTranslationsToAttach : languages.getTranslationsCollection()) {
                translationsCollectionTranslationsToAttach = em.getReference(translationsCollectionTranslationsToAttach.getClass(), translationsCollectionTranslationsToAttach.getTranslationsPK());
                attachedTranslationsCollection.add(translationsCollectionTranslationsToAttach);
            }
            languages.setTranslationsCollection(attachedTranslationsCollection);
            Collection<Langsets> attachedLangsetsCollection = new ArrayList<Langsets>();
            for (Langsets langsetsCollectionLangsetsToAttach : languages.getLangsetsCollection()) {
                langsetsCollectionLangsetsToAttach = em.getReference(langsetsCollectionLangsetsToAttach.getClass(), langsetsCollectionLangsetsToAttach.getIdLangset());
                attachedLangsetsCollection.add(langsetsCollectionLangsetsToAttach);
            }
            languages.setLangsetsCollection(attachedLangsetsCollection);
            em.persist(languages);
            for (Terms termsCollectionTerms : languages.getTermsCollection()) {
                Languages oldIdLanguageOfTermsCollectionTerms = termsCollectionTerms.getIdLanguage();
                termsCollectionTerms.setIdLanguage(languages);
                termsCollectionTerms = em.merge(termsCollectionTerms);
                if (oldIdLanguageOfTermsCollectionTerms != null) {
                    oldIdLanguageOfTermsCollectionTerms.getTermsCollection().remove(termsCollectionTerms);
                    oldIdLanguageOfTermsCollectionTerms = em.merge(oldIdLanguageOfTermsCollectionTerms);
                }
            }
            for (Translations translationsCollectionTranslations : languages.getTranslationsCollection()) {
                Languages oldLanguagesOfTranslationsCollectionTranslations = translationsCollectionTranslations.getLanguages();
                translationsCollectionTranslations.setLanguages(languages);
                translationsCollectionTranslations = em.merge(translationsCollectionTranslations);
                if (oldLanguagesOfTranslationsCollectionTranslations != null) {
                    oldLanguagesOfTranslationsCollectionTranslations.getTranslationsCollection().remove(translationsCollectionTranslations);
                    oldLanguagesOfTranslationsCollectionTranslations = em.merge(oldLanguagesOfTranslationsCollectionTranslations);
                }
            }
            for (Langsets langsetsCollectionLangsets : languages.getLangsetsCollection()) {
                Languages oldIdLanguageOfLangsetsCollectionLangsets = langsetsCollectionLangsets.getIdLanguage();
                langsetsCollectionLangsets.setIdLanguage(languages);
                langsetsCollectionLangsets = em.merge(langsetsCollectionLangsets);
                if (oldIdLanguageOfLangsetsCollectionLangsets != null) {
                    oldIdLanguageOfLangsetsCollectionLangsets.getLangsetsCollection().remove(langsetsCollectionLangsets);
                    oldIdLanguageOfLangsetsCollectionLangsets = em.merge(oldIdLanguageOfLangsetsCollectionLangsets);
                }
            }
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

    public void edit(Languages languages) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Languages persistentLanguages = em.find(Languages.class, languages.getIdLanguage());
            Collection<Terms> termsCollectionOld = persistentLanguages.getTermsCollection();
            Collection<Terms> termsCollectionNew = languages.getTermsCollection();
            Collection<Translations> translationsCollectionOld = persistentLanguages.getTranslationsCollection();
            Collection<Translations> translationsCollectionNew = languages.getTranslationsCollection();
            Collection<Langsets> langsetsCollectionOld = persistentLanguages.getLangsetsCollection();
            Collection<Langsets> langsetsCollectionNew = languages.getLangsetsCollection();
            List<String> illegalOrphanMessages = null;
            for (Terms termsCollectionOldTerms : termsCollectionOld) {
                if (!termsCollectionNew.contains(termsCollectionOldTerms)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Terms " + termsCollectionOldTerms + " since its idLanguage field is not nullable.");
                }
            }
            for (Translations translationsCollectionOldTranslations : translationsCollectionOld) {
                if (!translationsCollectionNew.contains(translationsCollectionOldTranslations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Translations " + translationsCollectionOldTranslations + " since its languages field is not nullable.");
                }
            }
            for (Langsets langsetsCollectionOldLangsets : langsetsCollectionOld) {
                if (!langsetsCollectionNew.contains(langsetsCollectionOldLangsets)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Langsets " + langsetsCollectionOldLangsets + " since its idLanguage field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Terms> attachedTermsCollectionNew = new ArrayList<Terms>();
            for (Terms termsCollectionNewTermsToAttach : termsCollectionNew) {
                termsCollectionNewTermsToAttach = em.getReference(termsCollectionNewTermsToAttach.getClass(), termsCollectionNewTermsToAttach.getIdTerm());
                attachedTermsCollectionNew.add(termsCollectionNewTermsToAttach);
            }
            termsCollectionNew = attachedTermsCollectionNew;
            languages.setTermsCollection(termsCollectionNew);
            Collection<Translations> attachedTranslationsCollectionNew = new ArrayList<Translations>();
            for (Translations translationsCollectionNewTranslationsToAttach : translationsCollectionNew) {
                translationsCollectionNewTranslationsToAttach = em.getReference(translationsCollectionNewTranslationsToAttach.getClass(), translationsCollectionNewTranslationsToAttach.getTranslationsPK());
                attachedTranslationsCollectionNew.add(translationsCollectionNewTranslationsToAttach);
            }
            translationsCollectionNew = attachedTranslationsCollectionNew;
            languages.setTranslationsCollection(translationsCollectionNew);
            Collection<Langsets> attachedLangsetsCollectionNew = new ArrayList<Langsets>();
            for (Langsets langsetsCollectionNewLangsetsToAttach : langsetsCollectionNew) {
                langsetsCollectionNewLangsetsToAttach = em.getReference(langsetsCollectionNewLangsetsToAttach.getClass(), langsetsCollectionNewLangsetsToAttach.getIdLangset());
                attachedLangsetsCollectionNew.add(langsetsCollectionNewLangsetsToAttach);
            }
            langsetsCollectionNew = attachedLangsetsCollectionNew;
            languages.setLangsetsCollection(langsetsCollectionNew);
            languages = em.merge(languages);
            for (Terms termsCollectionNewTerms : termsCollectionNew) {
                if (!termsCollectionOld.contains(termsCollectionNewTerms)) {
                    Languages oldIdLanguageOfTermsCollectionNewTerms = termsCollectionNewTerms.getIdLanguage();
                    termsCollectionNewTerms.setIdLanguage(languages);
                    termsCollectionNewTerms = em.merge(termsCollectionNewTerms);
                    if (oldIdLanguageOfTermsCollectionNewTerms != null && !oldIdLanguageOfTermsCollectionNewTerms.equals(languages)) {
                        oldIdLanguageOfTermsCollectionNewTerms.getTermsCollection().remove(termsCollectionNewTerms);
                        oldIdLanguageOfTermsCollectionNewTerms = em.merge(oldIdLanguageOfTermsCollectionNewTerms);
                    }
                }
            }
            for (Translations translationsCollectionNewTranslations : translationsCollectionNew) {
                if (!translationsCollectionOld.contains(translationsCollectionNewTranslations)) {
                    Languages oldLanguagesOfTranslationsCollectionNewTranslations = translationsCollectionNewTranslations.getLanguages();
                    translationsCollectionNewTranslations.setLanguages(languages);
                    translationsCollectionNewTranslations = em.merge(translationsCollectionNewTranslations);
                    if (oldLanguagesOfTranslationsCollectionNewTranslations != null && !oldLanguagesOfTranslationsCollectionNewTranslations.equals(languages)) {
                        oldLanguagesOfTranslationsCollectionNewTranslations.getTranslationsCollection().remove(translationsCollectionNewTranslations);
                        oldLanguagesOfTranslationsCollectionNewTranslations = em.merge(oldLanguagesOfTranslationsCollectionNewTranslations);
                    }
                }
            }
            for (Langsets langsetsCollectionNewLangsets : langsetsCollectionNew) {
                if (!langsetsCollectionOld.contains(langsetsCollectionNewLangsets)) {
                    Languages oldIdLanguageOfLangsetsCollectionNewLangsets = langsetsCollectionNewLangsets.getIdLanguage();
                    langsetsCollectionNewLangsets.setIdLanguage(languages);
                    langsetsCollectionNewLangsets = em.merge(langsetsCollectionNewLangsets);
                    if (oldIdLanguageOfLangsetsCollectionNewLangsets != null && !oldIdLanguageOfLangsetsCollectionNewLangsets.equals(languages)) {
                        oldIdLanguageOfLangsetsCollectionNewLangsets.getLangsetsCollection().remove(langsetsCollectionNewLangsets);
                        oldIdLanguageOfLangsetsCollectionNewLangsets = em.merge(oldIdLanguageOfLangsetsCollectionNewLangsets);
                    }
                }
            }
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Terms> termsCollectionOrphanCheck = languages.getTermsCollection();
            for (Terms termsCollectionOrphanCheckTerms : termsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Languages (" + languages + ") cannot be destroyed since the Terms " + termsCollectionOrphanCheckTerms + " in its termsCollection field has a non-nullable idLanguage field.");
            }
            Collection<Translations> translationsCollectionOrphanCheck = languages.getTranslationsCollection();
            for (Translations translationsCollectionOrphanCheckTranslations : translationsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Languages (" + languages + ") cannot be destroyed since the Translations " + translationsCollectionOrphanCheckTranslations + " in its translationsCollection field has a non-nullable languages field.");
            }
            Collection<Langsets> langsetsCollectionOrphanCheck = languages.getLangsetsCollection();
            for (Langsets langsetsCollectionOrphanCheckLangsets : langsetsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Languages (" + languages + ") cannot be destroyed since the Langsets " + langsetsCollectionOrphanCheckLangsets + " in its langsetsCollection field has a non-nullable idLanguage field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
