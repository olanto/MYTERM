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
@Table(name = "terms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terms.findAll", query = "SELECT t FROM Terms t"),
    @NamedQuery(name = "Terms.findByIdTerm", query = "SELECT t FROM Terms t WHERE t.idTerm = :idTerm"),
    @NamedQuery(name = "Terms.findByIdLanguage", query = "SELECT t FROM Terms t WHERE t.idLanguage = :idLanguage"),
    @NamedQuery(name = "Terms.findByIdLangset", query = "SELECT t FROM Terms t WHERE t.idLangset = :idLangset"),
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
    @NamedQuery(name = "Terms.findByTermAdminStatus", query = "SELECT t FROM Terms t WHERE t.termAdminStatus = :termAdminStatus"),
    @NamedQuery(name = "Terms.findByTermGeoUsage", query = "SELECT t FROM Terms t WHERE t.termGeoUsage = :termGeoUsage"),
    @NamedQuery(name = "Terms.findByCreation", query = "SELECT t FROM Terms t WHERE t.creation = :creation"),
    @NamedQuery(name = "Terms.findByCreateBy", query = "SELECT t FROM Terms t WHERE t.createBy = :createBy"),
    @NamedQuery(name = "Terms.findByLastmodified", query = "SELECT t FROM Terms t WHERE t.lastmodified = :lastmodified"),
    @NamedQuery(name = "Terms.findByLastmodifiedBy", query = "SELECT t FROM Terms t WHERE t.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "Terms.findByStatus", query = "SELECT t FROM Terms t WHERE t.status = :status"),
    @NamedQuery(name = "Terms.findBySeq", query = "SELECT t FROM Terms t WHERE t.seq = :seq"),
    @NamedQuery(name = "Terms.findByExtra", query = "SELECT t FROM Terms t WHERE t.extra = :extra")})
public class Terms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_term")
    private Long idTerm;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "id_langset")
    private long idLangset;
    @Basic(optional = false)
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
    @Column(name = "term_gender")
    private String termGender;
    @Column(name = "term_admin_status")
    private String termAdminStatus;
    @Column(name = "term_geo_usage")
    private String termGeoUsage;
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
    @Basic(optional = false)
    @Column(name = "status")
    private char status;
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "extra")
    private String extra;
    @Column(name = "extcrossref")
    private String extcrossref;
    @Column(name = "crossref")
    private String crossref;
    @Column(name = "image")
    private String image;
    @Column(name = "importedref")
    private String importedref;
    @Column(name = "sup0")
    private String sup0;
    @Column(name = "sup1")
    private String sup1;
    @Column(name = "sup2")
    private String sup2;
    @Column(name = "sup3")
    private String sup3;
    @Column(name = "sup4")
    private String sup4;

    public Terms() {
    }

    public Terms(Long idTerm) {
        this.idTerm = idTerm;
    }

    public Terms(Long idTerm, String idLanguage, long idLangset, String termForm, char status) {
        this.idTerm = idTerm;
        this.idLanguage = idLanguage;
        this.idLangset = idLangset;
        this.termForm = termForm;
        this.status = status;
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
     * @return the importedref
     */
    public String getImportedref() {
        return importedref;
    }

    /**
     * @param importedref the importedref to set
     */
    public void setImportedref(String importedref) {
        this.importedref = importedref;
    }

    /**
     * @return the sup0
     */
    public String getSup0() {
        return sup0;
    }

    /**
     * @param sup0 the sup0 to set
     */
    public void setSup0(String sup0) {
        this.sup0 = sup0;
    }

    /**
     * @return the sup1
     */
    public String getSup1() {
        return sup1;
    }

    /**
     * @param sup1 the sup1 to set
     */
    public void setSup1(String sup1) {
        this.sup1 = sup1;
    }

    /**
     * @return the sup2
     */
    public String getSup2() {
        return sup2;
    }

    /**
     * @param sup2 the sup2 to set
     */
    public void setSup2(String sup2) {
        this.sup2 = sup2;
    }

    /**
     * @return the sup3
     */
    public String getSup3() {
        return sup3;
    }

    /**
     * @param sup3 the sup3 to set
     */
    public void setSup3(String sup3) {
        this.sup3 = sup3;
    }

    /**
     * @return the sup4
     */
    public String getSup4() {
        return sup4;
    }

    /**
     * @param sup4 the sup4 to set
     */
    public void setSup4(String sup4) {
        this.sup4 = sup4;
    }
}
