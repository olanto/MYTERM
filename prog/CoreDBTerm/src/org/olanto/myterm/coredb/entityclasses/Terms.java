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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terms.findAll", query = "SELECT t FROM Terms t"),
    @NamedQuery(name = "Terms.findByIdTerm", query = "SELECT t FROM Terms t WHERE t.idTerm = :idTerm"),
    @NamedQuery(name = "Terms.findByTermForm", query = "SELECT t FROM Terms t WHERE t.termForm = :termForm"),
    @NamedQuery(name = "Terms.findByTermSource", query = "SELECT t FROM Terms t WHERE t.termSource = :termSource"),
    @NamedQuery(name = "Terms.findByTermDefinition", query = "SELECT t FROM Terms t WHERE t.termDefinition = :termDefinition"),
    @NamedQuery(name = "Terms.findByTermSourceDefinition", query = "SELECT t FROM Terms t WHERE t.termSourceDefinition = :termSourceDefinition"),
    @NamedQuery(name = "Terms.findByTermUsage", query = "SELECT t FROM Terms t WHERE t.termUsage = :termUsage"),
    @NamedQuery(name = "Terms.findByTermContext", query = "SELECT t FROM Terms t WHERE t.termContext = :termContext"),
    @NamedQuery(name = "Terms.findByTermSourceContext", query = "SELECT t FROM Terms t WHERE t.termSourceContext = :termSourceContext"),
    @NamedQuery(name = "Terms.findByTermNote", query = "SELECT t FROM Terms t WHERE t.termNote = :termNote"),
    @NamedQuery(name = "Terms.findByTermType", query = "SELECT t FROM Terms t WHERE t.termType = :termType"),
    @NamedQuery(name = "Terms.findByTermPartofspeech", query = "SELECT t FROM Terms t WHERE t.termPartofspeech = :termPartofspeech"),
    @NamedQuery(name = "Terms.findByTermGender", query = "SELECT t FROM Terms t WHERE t.termGender = :termGender"),
    @NamedQuery(name = "Terms.findByCreation", query = "SELECT t FROM Terms t WHERE t.creation = :creation"),
    @NamedQuery(name = "Terms.findByLastmodified", query = "SELECT t FROM Terms t WHERE t.lastmodified = :lastmodified"),
    @NamedQuery(name = "Terms.findByStatus", query = "SELECT t FROM Terms t WHERE t.status = :status"),
    @NamedQuery(name = "Terms.findBySeq", query = "SELECT t FROM Terms t WHERE t.seq = :seq")})
public class Terms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_term")
    private Long idTerm;
    @Basic(optional = false)
       @Column(name = "extra")
    private String extra;
 @Column(name = "term_form")
    private String termForm;
    @Column(name = "term_source")
    private String termSource;
    @Column(name = "term_definition")
    private String termDefinition;
    @Column(name = "term_source_definition")
    private String termSourceDefinition;
    @Column(name = "term_usage")
    private String termUsage;
    @Column(name = "term_context")
    private String termContext;
    @Column(name = "term_source_context")
    private String termSourceContext;
    @Column(name = "term_note")
    private String termNote;
    @Column(name = "term_type")
    private String termType;
    @Column(name = "term_partofspeech")
    private String termPartofspeech;
    @Column(name = "term_admin_status")
    private String termAdminStatus;
    @Column(name = "term_geo_usage")
    private String termGeographicalUsage;
    @Column(name = "term_gender")
    private String termGender;
    @Temporal(TemporalType.DATE)
    private Date creation;
    @Temporal(TemporalType.DATE)
    private Date lastmodified;
    @Basic(optional = false)
    private char status;
    private Integer seq;
    @JoinColumn(name = "lastmodified_by", referencedColumnName = "id_owner")
    @ManyToOne
    private Owners lastmodifiedBy;
    @JoinColumn(name = "create_by", referencedColumnName = "id_owner")
    @ManyToOne
    private Owners createBy;
    @JoinColumn(name = "id_language", referencedColumnName = "id_language")
    @ManyToOne(optional = false)
    private Languages idLanguage;
    @JoinColumn(name = "id_langset", referencedColumnName = "id_langset")
    @ManyToOne(optional = false)
    private Langsets idLangset;

    public Terms() {
    }

    public Terms(Long idTerm) {
        this.idTerm = idTerm;
    }

    public Terms(Long idTerm, String termForm, char status) {
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

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
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

    public Owners getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(Owners lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }

    public Owners getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Owners createBy) {
        this.createBy = createBy;
    }

    public Languages getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Languages idLanguage) {
        this.idLanguage = idLanguage;
    }

    public Langsets getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(Langsets idLangset) {
        this.idLangset = idLangset;
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
        if (!(object instanceof Terms)) {
            return false;
        }
        Terms other = (Terms) object;
        if ((this.idTerm == null && other.idTerm != null) || (this.idTerm != null && !this.idTerm.equals(other.idTerm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Terms[ idTerm=" + idTerm + " ]";
    }

    /**
     * @return the termAdminStatus
     */
    public String getTermAdminStatus() {
        return termAdminStatus;
    }

    /**
     * @param termAdminStatus the termAdminStatus to set
     */
    public void setTermAdminStatus(String termAdminStatus) {
        this.termAdminStatus = termAdminStatus;
    }

    /**
     * @return the termGeographicalUsage
     */
    public String getTermGeographicalUsage() {
        return termGeographicalUsage;
    }

    /**
     * @param termGeographicalUsage the termGeographicalUsage to set
     */
    public void setTermGeographicalUsage(String termGeographicalUsage) {
        this.termGeographicalUsage = termGeographicalUsage;
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
