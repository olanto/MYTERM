/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "resources")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resources.findAll", query = "SELECT r FROM Resources r"),
    @NamedQuery(name = "Resources.findByIdResource", query = "SELECT r FROM Resources r WHERE r.idResource = :idResource"),
    @NamedQuery(name = "Resources.findByIdOwner", query = "SELECT r FROM Resources r WHERE r.idOwner = :idOwner"),
    @NamedQuery(name = "Resources.findByResourceName", query = "SELECT r FROM Resources r WHERE r.resourceName = :resourceName"),
    @NamedQuery(name = "Resources.findByResourcePrivacy", query = "SELECT r FROM Resources r WHERE r.resourcePrivacy = :resourcePrivacy"),
    @NamedQuery(name = "Resources.findByExtra", query = "SELECT r FROM Resources r WHERE r.extra = :extra")})
public class Resources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resource")
    private Long idResource;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "resource_privacy")
    private String resourcePrivacy;
    @Column(name = "extra")
    private String extra;

    public Resources() {
    }

    public Resources(Long idResource) {
        this.idResource = idResource;
    }

    public Resources(Long idResource, long idOwner, String resourceName, String resourcePrivacy) {
        this.idResource = idResource;
        this.idOwner = idOwner;
        this.resourceName = resourceName;
        this.resourcePrivacy = resourcePrivacy;
    }
   public Resources(Long idResource, String resourceName, String resourcePrivacy) {
        this.idResource = idResource;
        this.idOwner = idOwner;
        this.resourceName = resourceName;
        this.resourcePrivacy = resourcePrivacy;
    }

    public Long getIdResource() {
        return idResource;
    }

    public void setIdResource(Long idResource) {
        this.idResource = idResource;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePrivacy() {
        return resourcePrivacy;
    }

    public void setResourcePrivacy(String resourcePrivacy) {
        this.resourcePrivacy = resourcePrivacy;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResource != null ? idResource.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resources)) {
            return false;
        }
        Resources other = (Resources) object;
        if ((this.idResource == null && other.idResource != null) || (this.idResource != null && !this.idResource.equals(other.idResource))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Resources[ idResource=" + idResource + " ]";
    }
    
}
