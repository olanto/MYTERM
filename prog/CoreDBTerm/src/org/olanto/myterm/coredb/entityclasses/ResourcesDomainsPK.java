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
public class ResourcesDomainsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_domain")
    private long idDomain;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;

    public ResourcesDomainsPK() {
    }

    public ResourcesDomainsPK(long idDomain, long idResource) {
        this.idDomain = idDomain;
        this.idResource = idResource;
    }

    public long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(long idDomain) {
        this.idDomain = idDomain;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDomain;
        hash += (int) idResource;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourcesDomainsPK)) {
            return false;
        }
        ResourcesDomainsPK other = (ResourcesDomainsPK) object;
        if (this.idDomain != other.idDomain) {
            return false;
        }
        if (this.idResource != other.idResource) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.ResourcesDomainsPK[ idDomain=" + idDomain + ", idResource=" + idResource + " ]";
    }
    
}
