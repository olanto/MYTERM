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
    @NamedQuery(name = "Owners.findAll", query = "SELECT o FROM Owners o"),
    @NamedQuery(name = "Owners.findByIdOwner", query = "SELECT o FROM Owners o WHERE o.idOwner = :idOwner"),
    @NamedQuery(name = "Owners.findByOwnerFirstName", query = "SELECT o FROM Owners o WHERE o.ownerFirstName = :ownerFirstName"),
    @NamedQuery(name = "Owners.findByOwnerLastName", query = "SELECT o FROM Owners o WHERE o.ownerLastName = :ownerLastName"),
    @NamedQuery(name = "Owners.findByOwnerMailing", query = "SELECT o FROM Owners o WHERE o.ownerMailing = :ownerMailing"),
    @NamedQuery(name = "Owners.findByOwnerHash", query = "SELECT o FROM Owners o WHERE o.ownerHash = :ownerHash"),
    @NamedQuery(name = "Owners.findByOwnerStatus", query = "SELECT o FROM Owners o WHERE o.ownerStatus = :ownerStatus")})
public class Owners implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_owner")
    private Long idOwner;
    @Basic(optional = false)
    @Column(name = "owner_first_name")
    private String ownerFirstName;
    @Basic(optional = false)
    @Column(name = "owner_last_name")
    private String ownerLastName;
    @Basic(optional = false)
    @Column(name = "owner_mailing")
    private String ownerMailing;
    @Column(name = "owner_hash")
    private String ownerHash;
    @Basic(optional = false)
    @Column(name = "owner_status")
    private String ownerStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOwner")
    private Collection<Resources> resourcesCollection;
    @OneToMany(mappedBy = "lastmodifiedBy")
    private Collection<Terms> termsCollection;
    @OneToMany(mappedBy = "createBy")
    private Collection<Terms> termsCollection1;

    public Owners() {
    }

    public Owners(Long idOwner) {
        this.idOwner = idOwner;
    }

    public Owners(Long idOwner, String ownerFirstName, String ownerLastName, String ownerMailing, String ownerStatus) {
        this.idOwner = idOwner;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerMailing = ownerMailing;
        this.ownerStatus = ownerStatus;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerMailing() {
        return ownerMailing;
    }

    public void setOwnerMailing(String ownerMailing) {
        this.ownerMailing = ownerMailing;
    }

    public String getOwnerHash() {
        return ownerHash;
    }

    public void setOwnerHash(String ownerHash) {
        this.ownerHash = ownerHash;
    }

    public String getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(String ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    @XmlTransient
    public Collection<Resources> getResourcesCollection() {
        return resourcesCollection;
    }

    public void setResourcesCollection(Collection<Resources> resourcesCollection) {
        this.resourcesCollection = resourcesCollection;
    }

    @XmlTransient
    public Collection<Terms> getTermsCollection() {
        return termsCollection;
    }

    public void setTermsCollection(Collection<Terms> termsCollection) {
        this.termsCollection = termsCollection;
    }

    @XmlTransient
    public Collection<Terms> getTermsCollection1() {
        return termsCollection1;
    }

    public void setTermsCollection1(Collection<Terms> termsCollection1) {
        this.termsCollection1 = termsCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOwner != null ? idOwner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Owners)) {
            return false;
        }
        Owners other = (Owners) object;
        if ((this.idOwner == null && other.idOwner != null) || (this.idOwner != null && !this.idOwner.equals(other.idOwner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Owners[ idOwner=" + idOwner + " ]";
    }
    
}
