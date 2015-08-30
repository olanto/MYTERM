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
 * @author simple
 */
public class TermDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idTerm;
    private String idLanguage;
    private String langName;
    private long idLangset;
    private String termForm;
    private String termSource;
    private String termDefinition;
    private String termSourceDefinition;
    private String termUsage;
    private String termContext;
    private String termSourceContext;
    private String termNote;
    private String technicalNote;
    private String referenceNote;
    private String linguisticNote;
    private String termType;
    private String termPartofspeech;
    private String termGender;
    private String termAdminStatus;
    private String termGeoUsage;
    private Date creation;
    private BigInteger createBy;
    private Date lastmodified;
    private BigInteger lastmodifiedBy;
    private char status;
    private Integer seq;
    private String extra;
    private String extcrossref;
    private String crossref;

    public TermDTO() {
    }

    public TermDTO(Long idTerm) {
        this.idTerm = idTerm;
    }

    public TermDTO(Long idTerm, String idLanguage, long idLangset, String termForm, char status) {
        this.idTerm = idTerm;
        this.idLanguage = idLanguage;
        this.idLangset = idLangset;
        this.termForm = termForm;
        this.status = status;
    }

    public TermDTO(Long idTerm, String idLanguage, String termForm, char status) {
        this.idTerm = idTerm;
        this.idLanguage = idLanguage;
        this.termForm = termForm;
        this.status = status;
    }

    public TermDTO(Long idTerm, String termForm, char status) {
        this.idTerm = idTerm;
        this.termForm = termForm;
        this.status = status;
    }

    public Long getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(Long idTerm) {
        this.idTerm = idTerm;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public long getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(long idLangset) {
        this.idLangset = idLangset;
    }

    public String getTermForm() {
        return termForm;
    }

    public void setTermForm(String termForm) {
        this.termForm = termForm;
    }

    public String getTermSource() {
        return termSource;
    }

    public void setTermSource(String termSource) {
        this.termSource = termSource;
    }

    public String getTermDefinition() {
        return termDefinition;
    }

    public void setTermDefinition(String termDefinition) {
        this.termDefinition = termDefinition;
    }

    public String getTermSourceDefinition() {
        return termSourceDefinition;
    }

    public void setTermSourceDefinition(String termSourceDefinition) {
        this.termSourceDefinition = termSourceDefinition;
    }

    public String getTermUsage() {
        return termUsage;
    }

    public String getLangName() {
        return langName;
    }

    public void setTermUsage(String termUsage) {
        this.termUsage = termUsage;
    }

    public String getTermContext() {
        return termContext;
    }

    public void setTermContext(String termContext) {
        this.termContext = termContext;
    }

    public String getTermSourceContext() {
        return termSourceContext;
    }

    public void setTermSourceContext(String termSourceContext) {
        this.termSourceContext = termSourceContext;
    }

    public String getTermNote() {
        return termNote;
    }

    public void setTermNote(String termNote) {
        this.termNote = termNote;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermPartofspeech() {
        return termPartofspeech;
    }

    public void setTermPartofspeech(String termPartofspeech) {
        this.termPartofspeech = termPartofspeech;
    }

    public String getTermGender() {
        return termGender;
    }

    public void setTermGender(String termGender) {
        this.termGender = termGender;
    }

    public String getTermAdminStatus() {
        return termAdminStatus;
    }

    public void setTermAdminStatus(String termAdminStatus) {
        this.termAdminStatus = termAdminStatus;
    }

    public String getTechnicalNote() {
        return technicalNote;
    }

    public String getLinguisticNote() {
        return linguisticNote;
    }

    public String getReferenceNote() {
        return referenceNote;
    }

    public void setTechnicalNote(String technicalNote) {
        this.technicalNote = technicalNote;
    }

    public void setLinguisticNote(String linguisticNote) {
        this.linguisticNote = linguisticNote;
    }

    public void setReferenceNote(String referenceNote) {
        this.referenceNote = referenceNote;
    }

    public String getTermGeoUsage() {
        return termGeoUsage;
    }

    public void setTermGeoUsage(String termGeoUsage) {
        this.termGeoUsage = termGeoUsage;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public BigInteger getCreateBy() {
        return createBy;
    }

    public void setCreateBy(BigInteger createBy) {
        this.createBy = createBy;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public BigInteger getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(BigInteger lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTerm != null ? idTerm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TermDTO)) {
            return false;
        }
        TermDTO other = (TermDTO) object;
        if ((this.idTerm == null && other.getIdTerm() != null) || (this.idTerm != null && !this.idTerm.equals(other.idTerm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Terms[ idTerm=" + idTerm + " ]";
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