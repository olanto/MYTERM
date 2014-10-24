/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package org.olanto.myterm.coredb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.olanto.myterm.coredb.TermEnum.AutoCreate;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Domains;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Languages;
import org.olanto.myterm.coredb.entityclasses.Owners;
import org.olanto.myterm.coredb.entityclasses.Resources;
import org.olanto.myterm.coredb.entityclasses.Terms;
import org.olanto.myterm.coredb.jpacontroller.exceptions.PreexistingEntityException;

/**
 *
 * @author simple
 */
public class Queries {

    public static Domains getDomainID(String domain_default_name, AutoCreate auto) {
        Query query = TermDB.em.createNamedQuery("Domains.findByDomainDefaultName");
        query.setParameter("domainDefaultName", domain_default_name);
        List<Domains> result = query.getResultList();
        //System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + domain_default_name);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + domain_default_name);
            if (auto == AutoCreate.YES) {
                System.out.println("CREATE NEW VALUE :" + result.size() + ", for :" + domain_default_name);
                Domains obj = new Domains(null, domain_default_name);
                TermDB.domainsJC.create(obj);
                return obj;
            } else {
                return null;
            }

        }
        return result.get(0);
    }

    // create sur le nom !!! pas très bon mais suffisant pour les premiers import
    public static Owners getOwnerID(String ownerLastName, AutoCreate auto) {
        Query query = TermDB.em.createNamedQuery("Owners.findByOwnerLastName");
        query.setParameter("ownerLastName", ownerLastName);
        List<Owners> result = query.getResultList();
//        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + ownerLastName);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + ownerLastName);
            if (auto == AutoCreate.YES) {
                System.out.println("CREATE NEW VALUE :" + result.size() + ", for :" + ownerLastName);
                return ManageOwner.create("???", ownerLastName, "???", "DORMANT", "???");
            } else {
                return null;
            }

        }
        return result.get(0);
    }

    public static Owners getOwner(String ownermail, String hash) {
        Query query = TermDB.em.createNamedQuery("Owners.findByOwnerMailingAndHash");
        query.setParameter("ownerMailing", ownermail);
        query.setParameter("ownerHash", hash);
        List<Owners> result = query.getResultList();
        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + ownermail);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + ownermail);
            return null;
        }
        return result.get(0);
    }
    
    public static Owners getOwnerbyID(long IDowner) {
        Query query = TermDB.em.createNamedQuery("Owners.findByIdOwner");
        query.setParameter("idOwner", IDowner);
        List<Owners> result = query.getResultList();
        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + IDowner);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + IDowner);
            return null;
        }
        return result.get(0);
    }

     public static String getOwnerFullNamebyID(long IDowner) {
        Query query = TermDB.em.createNamedQuery("Owners.findByIdOwner");
        query.setParameter("idOwner", IDowner);
        List<Owners> result = query.getResultList();
        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + IDowner);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + IDowner);
            return null;
        }
        return (result.get(0).getOwnerFirstName() +" "+ result.get(0).getOwnerLastName());
    }

     
    public static Terms getTermByID(long idTerm) {
        Query query = TermDB.em.createNamedQuery("Terms.findByIdTerm");
        query.setParameter("idTerm", idTerm);
        List<Terms> result = query.getResultList();
        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + idTerm);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + idTerm);
            return null;
        }
        return result.get(0);
    }

    public static Resources getResourceID(String resourceName, AutoCreate auto) {
        Query query = TermDB.em.createNamedQuery("Resources.findByResourceName");
        query.setParameter("resourceName", resourceName);
        List<Resources> result = query.getResultList();
//        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + resourceName);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + resourceName);
            if (auto == AutoCreate.YES) {
                System.out.println("CREATE NEW VALUE :" + result.size() + ", for :" + resourceName);
                return ManageResource.create(resourceName, "PUBLIC", "???", "");
            } else {
                return null;
            }

        }
        return result.get(0);
    }

    public static List<Languages> getLanguages() {
        Query query = TermDB.em.createNamedQuery("Languages.findAll");
        List<Languages> result = query.getResultList();
        return result;
    }

    public static Languages getLanguageByID(String langID) {
        Query query = TermDB.em.createNamedQuery("Languages.findByIdLanguage");
        query.setParameter("idLanguage", langID);
        List<Languages> result = query.getResultList();
//        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + langID);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + langID);
            return null;
        }
        return result.get(0);
    }
    
    public static Concepts getConceptByID(long ConceptID) {
        Query query = TermDB.em.createNamedQuery("Concepts.findByIdConcept");
        query.setParameter("idConcept", ConceptID);
        List<Concepts> result = query.getResultList();
//        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + ConceptID);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + ConceptID);
            return null;
        }
        return result.get(0);
    }

    public static List<Resources> getResources() {
        Query query = TermDB.em.createNamedQuery("Resources.findAll");
        List<Resources> result = query.getResultList();
        return result;
    }

    public static Languages getLanguageID(String idLanguage, AutoCreate auto) {
        Query query = TermDB.em.createNamedQuery("Languages.findByIdLanguage");
        query.setParameter("idLanguage", idLanguage);
        List<Languages> result = query.getResultList();
//        System.out.println("result size:" + result.size());
        if (result.size() > 1) {
            System.out.println("TO MANY RETURNED VALUES :" + result.size() + ", for :" + idLanguage);
            return null;
        }
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + idLanguage);
            if (auto == AutoCreate.YES) {
                System.out.println("CREATE NEW VALUE :" + result.size() + ", for :" + idLanguage);
                Languages obj = new Languages(idLanguage, "??? " + idLanguage);
                try {
                    TermDB.languagesJC.create(obj);
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
                }
                return obj;
            } else {
                return null;
            }
        }
        return result.get(0);
    }

    public static Langsets getIdLangset(Long idLangset) {
        Query query = TermDB.em.createNamedQuery("Langsets.findByIdLangset");
        query.setParameter("idLangset", idLangset);
        List<Langsets> result = query.getResultList();
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + idLangset);
            return null;
        }
        return result.get(0);
    }

    public static Resources getIdResources(Long idResource) {
        Query query = TermDB.em.createNamedQuery("Resources.findByIdResource");
        query.setParameter("idResource", idResource);
        List<Resources> result = query.getResultList();
        if (result.isEmpty()) {
            System.out.println("NO RETURNED VALUES for :" + idResource);
            return null;
        }
        return result.get(0);
    }
}
