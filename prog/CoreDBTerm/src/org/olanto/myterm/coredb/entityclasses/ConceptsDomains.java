/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "concepts_domains")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptsDomains.findAll", query = "SELECT c FROM ConceptsDomains c"),
    @NamedQuery(name = "ConceptsDomains.findByIdDomain", query = "SELECT c FROM ConceptsDomains c WHERE c.conceptsDomainsPK.idDomain = :idDomain"),
    @NamedQuery(name = "ConceptsDomains.findByIdConcept", query = "SELECT c FROM ConceptsDomains c WHERE c.conceptsDomainsPK.idConcept = :idConcept")})
public class ConceptsDomains implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptsDomainsPK conceptsDomainsPK;

    public ConceptsDomains() {
    }

    public ConceptsDomains(ConceptsDomainsPK conceptsDomainsPK) {
        this.conceptsDomainsPK = conceptsDomainsPK;
    }

    public ConceptsDomains(long idDomain, long idConcept) {
        this.conceptsDomainsPK = new ConceptsDomainsPK(idDomain, idConcept);
    }

    public ConceptsDomainsPK getConceptsDomainsPK() {
        return conceptsDomainsPK;
    }

    public void setConceptsDomainsPK(ConceptsDomainsPK conceptsDomainsPK) {
        this.conceptsDomainsPK = conceptsDomainsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptsDomainsPK != null ? conceptsDomainsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptsDomains)) {
            return false;
        }
        ConceptsDomains other = (ConceptsDomains) object;
        if ((this.conceptsDomainsPK == null && other.conceptsDomainsPK != null) || (this.conceptsDomainsPK != null && !this.conceptsDomainsPK.equals(other.conceptsDomainsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.ConceptsDomains[ conceptsDomainsPK=" + conceptsDomainsPK + " ]";
    }
    
}
