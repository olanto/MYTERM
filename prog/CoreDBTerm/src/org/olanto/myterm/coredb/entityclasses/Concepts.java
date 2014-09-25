/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Column(name = "concept_note")
    private String conceptNote;
    @Column(name = "creation")
    @Temporal(TemporalType.DATE)
    private Date creation;
    @Column(name = "create_by")
    private BigInteger createBy;
    @Column(name = "lastmodified")
    @Temporal(TemporalType.DATE)
    private Date lastmodified;
    @Column(name = "lastmodified_by")
    private BigInteger lastmodifiedBy;
    @Column(name = "extra")
    private String extra;
    @Column(name = "image")
    private String image;
    @Column(name = "extcrossref")
    private String extcrossref;
    @Column(name = "crossref")
    private String crossref;

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

    /**
     * @return the conceptNote
     */
    public String getConceptNote() {
        return conceptNote;
    }

    /**
     * @param conceptNote the conceptNote to set
     */
    public void setConceptNote(String conceptNote) {
        this.conceptNote = conceptNote;
    }

    /**
     * @return the creation
     */
    public Date getCreation() {
        return creation;
    }

    /**
     * @param creation the creation to set
     */
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    /**
     * @return the createBy
     */
    public BigInteger getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(BigInteger createBy) {
        this.createBy = createBy;
    }

    /**
     * @return the lastmodified
     */
    public Date getLastmodified() {
        return lastmodified;
    }

    /**
     * @param lastmodified the lastmodified to set
     */
    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    /**
     * @return the lastmodifiedBy
     */
    public BigInteger getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    /**
     * @param lastmodifiedBy the lastmodifiedBy to set
     */
    public void setLastmodifiedBy(BigInteger lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the extcrossref
     */
    public String getExtcrossref() {
        return extcrossref;
    }

    /**
     * @param extcrossref the extcrossref to set
     */
    public void setExtcrossref(String extcrossref) {
        this.extcrossref = extcrossref;
    }

    /**
     * @return the crossref
     */
    public String getCrossref() {
        return crossref;
    }

    /**
     * @param crossref the crossref to set
     */
    public void setCrossref(String crossref) {
        this.crossref = crossref;
    }
}
