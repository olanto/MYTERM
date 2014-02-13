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
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author simple
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resources.findAll", query = "SELECT r FROM Resources r"),
    @NamedQuery(name = "Resources.findByIdResource", query = "SELECT r FROM Resources r WHERE r.idResource = :idResource"),
    @NamedQuery(name = "Resources.findByResourceName", query = "SELECT r FROM Resources r WHERE r.resourceName = :resourceName"),
    @NamedQuery(name = "Resources.findByResourcePrivacy", query = "SELECT r FROM Resources r WHERE r.resourcePrivacy = :resourcePrivacy")})
public class Resources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resource")
    private Long idResource;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Column(name = "extra")
    private String extra;
    @Basic(optional = false)
    @Column(name = "resource_privacy")
    private String resourcePrivacy;
    @JoinTable(name = "resources_domains", joinColumns = {
        @JoinColumn(name = "id_resource", referencedColumnName = "id_resource")}, inverseJoinColumns = {
        @JoinColumn(name = "id_domain", referencedColumnName = "id_domain")})
    @ManyToMany
    private Collection<Domains> domainsCollection;
    @JoinColumn(name = "id_owner", referencedColumnName = "id_owner")
    @ManyToOne(optional = false)
    private Owners idOwner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResource")
    private Collection<Concepts> conceptsCollection;

    public Resources() {
    }

    public Resources(Long idResource) {
        this.idResource = idResource;
    }

    public Resources(Long idResource, String resourceName, String resourcePrivacy) {
        this.idResource = idResource;
        this.resourceName = resourceName;
        this.resourcePrivacy = resourcePrivacy;
    }

    public Long getIdResource() {
        return idResource;
    }

    public void setIdResource(Long idResource) {
        this.idResource = idResource;
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

    @XmlTransient
    public Collection<Domains> getDomainsCollection() {
        return domainsCollection;
    }

    public void setDomainsCollection(Collection<Domains> domainsCollection) {
        this.domainsCollection = domainsCollection;
    }

    public Owners getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Owners idOwner) {
        this.idOwner = idOwner;
    }

    @XmlTransient
    public Collection<Concepts> getConceptsCollection() {
        return conceptsCollection;
    }

    public void setConceptsCollection(Collection<Concepts> conceptsCollection) {
        this.conceptsCollection = conceptsCollection;
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

    /**
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }
    
}
