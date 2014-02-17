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
@Table(name = "concepts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concepts.findAll", query = "SELECT c FROM Concepts c"),
    @NamedQuery(name = "Concepts.findByIdConcept", query = "SELECT c FROM Concepts c WHERE c.idConcept = :idConcept"),
    @NamedQuery(name = "Concepts.findByIdResource", query = "SELECT c FROM Concepts c WHERE c.idResource = :idResource"),
    @NamedQuery(name = "Concepts.findBySubjectField", query = "SELECT c FROM Concepts c WHERE c.subjectField = :subjectField"),
    @NamedQuery(name = "Concepts.findByConceptDefinition", query = "SELECT c FROM Concepts c WHERE c.conceptDefinition = :conceptDefinition"),
    @NamedQuery(name = "Concepts.findByConceptSourceDefinition", query = "SELECT c FROM Concepts c WHERE c.conceptSourceDefinition = :conceptSourceDefinition"),
    @NamedQuery(name = "Concepts.findByExtra", query = "SELECT c FROM Concepts c WHERE c.extra = :extra")})
public class Concepts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_concept")
    private Long idConcept;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Column(name = "subject_field")
    private String subjectField;
    @Column(name = "concept_definition")
    private String conceptDefinition;
    @Column(name = "concept_source_definition")
    private String conceptSourceDefinition;
    @Column(name = "extra")
    private String extra;

    public Concepts() {
    }

    public Concepts(Long idConcept) {
        this.idConcept = idConcept;
    }

    public Concepts(Long idConcept, long idResource) {
        this.idConcept = idConcept;
        this.idResource = idResource;
    }

    public Long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(Long idConcept) {
        this.idConcept = idConcept;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public String getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(String subjectField) {
        this.subjectField = subjectField;
    }

    public String getConceptDefinition() {
        return conceptDefinition;
    }

    public void setConceptDefinition(String conceptDefinition) {
        this.conceptDefinition = conceptDefinition;
    }

    public String getConceptSourceDefinition() {
        return conceptSourceDefinition;
    }

    public void setConceptSourceDefinition(String conceptSourceDefinition) {
        this.conceptSourceDefinition = conceptSourceDefinition;
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
    
}
