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
@Table(name = "resources_domains")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResourcesDomains.findAll", query = "SELECT r FROM ResourcesDomains r"),
    @NamedQuery(name = "ResourcesDomains.findByIdDomain", query = "SELECT r FROM ResourcesDomains r WHERE r.resourcesDomainsPK.idDomain = :idDomain"),
    @NamedQuery(name = "ResourcesDomains.findByIdResource", query = "SELECT r FROM ResourcesDomains r WHERE r.resourcesDomainsPK.idResource = :idResource")})
public class ResourcesDomains implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResourcesDomainsPK resourcesDomainsPK;

    public ResourcesDomains() {
    }

    public ResourcesDomains(ResourcesDomainsPK resourcesDomainsPK) {
        this.resourcesDomainsPK = resourcesDomainsPK;
    }

    public ResourcesDomains(long idDomain, long idResource) {
        this.resourcesDomainsPK = new ResourcesDomainsPK(idDomain, idResource);
    }

    public ResourcesDomainsPK getResourcesDomainsPK() {
        return resourcesDomainsPK;
    }

    public void setResourcesDomainsPK(ResourcesDomainsPK resourcesDomainsPK) {
        this.resourcesDomainsPK = resourcesDomainsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcesDomainsPK != null ? resourcesDomainsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourcesDomains)) {
            return false;
        }
        ResourcesDomains other = (ResourcesDomains) object;
        if ((this.resourcesDomainsPK == null && other.resourcesDomainsPK != null) || (this.resourcesDomainsPK != null && !this.resourcesDomainsPK.equals(other.resourcesDomainsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.ResourcesDomains[ resourcesDomainsPK=" + resourcesDomainsPK + " ]";
    }
    
}
