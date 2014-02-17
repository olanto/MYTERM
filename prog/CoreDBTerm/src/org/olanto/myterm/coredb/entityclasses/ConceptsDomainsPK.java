/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author simple
 */
@Embeddable
public class ConceptsDomainsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_domain")
    private long idDomain;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;

    public ConceptsDomainsPK() {
    }

    public ConceptsDomainsPK(long idDomain, long idConcept) {
        this.idDomain = idDomain;
        this.idConcept = idConcept;
    }

    public long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(long idDomain) {
        this.idDomain = idDomain;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDomain;
        hash += (int) idConcept;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptsDomainsPK)) {
            return false;
        }
        ConceptsDomainsPK other = (ConceptsDomainsPK) object;
        if (this.idDomain != other.idDomain) {
            return false;
        }
        if (this.idConcept != other.idConcept) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.ConceptsDomainsPK[ idDomain=" + idDomain + ", idConcept=" + idConcept + " ]";
    }
    
}
