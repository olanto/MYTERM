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
    @NamedQuery(name = "Concepts.findAll", query = "SELECT c FROM Concepts c"),
    @NamedQuery(name = "Concepts.findByIdConcept", query = "SELECT c FROM Concepts c WHERE c.idConcept = :idConcept")})
public class Concepts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_concept")
    private Long idConcept;
    @Column(name = "subject_field")
    private String subjectfield;
    @Column(name = "extra")
    private String extra;
    @Column(name = "concept_definition")
    private String conceptDefinition;
    @Column(name = "concept_source_definition")
    private String conceptSourceDefinition;
    @JoinTable(name = "concepts_domains", joinColumns = {
        @JoinColumn(name = "id_concept", referencedColumnName = "id_concept")}, inverseJoinColumns = {
        @JoinColumn(name = "id_domain", referencedColumnName = "id_domain")})
    @ManyToMany
    private Collection<Domains> domainsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConcept")
    private Collection<Langsets> langsetsCollection;
    @JoinColumn(name = "id_resource", referencedColumnName = "id_resource")
    @ManyToOne(optional = false)
    private Resources idResource;

    public Concepts() {
    }

    public Concepts(Long idConcept) {
        this.idConcept = idConcept;
    }

    public Long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(Long idConcept) {
        this.idConcept = idConcept;
    }

    @XmlTransient
    public Collection<Domains> getDomainsCollection() {
        return domainsCollection;
    }

    public void setDomainsCollection(Collection<Domains> domainsCollection) {
        this.domainsCollection = domainsCollection;
    }

    @XmlTransient
    public Collection<Langsets> getLangsetsCollection() {
        return langsetsCollection;
    }

    public void setLangsetsCollection(Collection<Langsets> langsetsCollection) {
        this.langsetsCollection = langsetsCollection;
    }

    public Resources getIdResource() {
        return idResource;
    }

    public void setIdResource(Resources idResource) {
        this.idResource = idResource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConcept != null ? idConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concepts)) {
            return false;
        }
        Concepts other = (Concepts) object;
        if ((this.idConcept == null && other.idConcept != null) || (this.idConcept != null && !this.idConcept.equals(other.idConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Concepts[ idConcept=" + idConcept + " ]";
    }

    /**
     * @return the conceptDefinition
     */
    public String getConceptDefinition() {
        return conceptDefinition;
    }

    /**
     * @param conceptDefinition the conceptDefinition to set
     */
    public void setConceptDefinition(String conceptDefinition) {
        this.conceptDefinition = conceptDefinition;
    }

    /**
     * @return the conceptSourceDefinition
     */
    public String getConceptSourceDefinition() {
        return conceptSourceDefinition;
    }

    /**
     * @param conceptSourceDefinition the conceptSourceDefinition to set
     */
    public void setConceptSourceDefinition(String conceptSourceDefinition) {
        this.conceptSourceDefinition = conceptSourceDefinition;
    }

    /**
     * @return the subjectfield
     */
    public String getSubjectfield() {
        return subjectfield;
    }

    /**
     * @param subjectfield the subjectfield to set
     */
    public void setSubjectfield(String subjectfield) {
        this.subjectfield = subjectfield;
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
