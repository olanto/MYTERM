/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package olanto.myTerm.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author nizar ghoula - simple
 */
public class ConceptDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idConcept;
    private long idResource;
    private String subjectField;
    private String conceptDefinition;
    private String conceptSourceDefinition;
    private String conceptNote;
    private Date creation;
    private BigInteger createBy;
    private Date lastmodified;
    private BigInteger lastmodifiedBy;
    private String extra;
    private String image;
    private String extcrossref;
    private String crossref;

    public ConceptDTO() {
    }

    public ConceptDTO(Long idConcept) {
        this.idConcept = idConcept;
    }

    public ConceptDTO(Long idConcept, long idResource) {
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
        if (!(object instanceof ConceptDTO)) {
            return false;
        }
        ConceptDTO other = (ConceptDTO) object;
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
