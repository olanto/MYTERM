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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author simple
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domains.findAll", query = "SELECT d FROM Domains d"),
    @NamedQuery(name = "Domains.findByIdDomain", query = "SELECT d FROM Domains d WHERE d.idDomain = :idDomain"),
    @NamedQuery(name = "Domains.findByDomainDefaultName", query = "SELECT d FROM Domains d WHERE d.domainDefaultName = :domainDefaultName")})
public class Domains implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_domain")
    private Long idDomain;
    @Basic(optional = false)
    @Column(name = "domain_default_name")
    private String domainDefaultName;
    @ManyToMany(mappedBy = "domainsCollection")
    private Collection<Concepts> conceptsCollection;
    @ManyToMany(mappedBy = "domainsCollection")
    private Collection<Resources> resourcesCollection;

    public Domains() {
    }

    public Domains(Long idDomain) {
        this.idDomain = idDomain;
    }

    public Domains(Long idDomain, String domainDefaultName) {
        this.idDomain = idDomain;
        this.domainDefaultName = domainDefaultName;
    }

    public Long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(Long idDomain) {
        this.idDomain = idDomain;
    }

    public String getDomainDefaultName() {
        return domainDefaultName;
    }

    public void setDomainDefaultName(String domainDefaultName) {
        this.domainDefaultName = domainDefaultName;
    }

    @XmlTransient
    public Collection<Concepts> getConceptsCollection() {
        return conceptsCollection;
    }

    public void setConceptsCollection(Collection<Concepts> conceptsCollection) {
        this.conceptsCollection = conceptsCollection;
    }

    @XmlTransient
    public Collection<Resources> getResourcesCollection() {
        return resourcesCollection;
    }

    public void setResourcesCollection(Collection<Resources> resourcesCollection) {
        this.resourcesCollection = resourcesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDomain != null ? idDomain.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domains)) {
            return false;
        }
        Domains other = (Domains) object;
        if ((this.idDomain == null && other.idDomain != null) || (this.idDomain != null && !this.idDomain.equals(other.idDomain))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Domains[ idDomain=" + idDomain + " ]";
    }
    
}
