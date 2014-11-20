/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "vj_conceptdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjConceptdetail.findAll", query = "SELECT v FROM VjConceptdetail v"),
    @NamedQuery(name = "VjConceptdetail.findByUuid", query = "SELECT v FROM VjConceptdetail v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjConceptdetail.findByIdTerm", query = "SELECT v FROM VjConceptdetail v WHERE v.idTerm = :idTerm"),
    @NamedQuery(name = "VjConceptdetail.findByIdLanguage", query = "SELECT v FROM VjConceptdetail v WHERE v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjConceptdetail.findByTermForm", query = "SELECT v FROM VjConceptdetail v WHERE v.termForm = :termForm"),
    @NamedQuery(name = "VjConceptdetail.findByIdConcept", query = "SELECT v FROM VjConceptdetail v WHERE v.idConcept = :idConcept"),
    @NamedQuery(name = "VjConceptdetail.findByIdConceptAndLangSet", query = "SELECT v FROM VjConceptdetail v WHERE v.idConcept = :idConcept AND v.idLangset = :idLangset"),
    @NamedQuery(name = "VjConceptdetail.findByIdConceptAndLanguage", query = "SELECT v FROM VjConceptdetail v WHERE v.idConcept = :idConcept AND v.idLanguage = :idLanguage")})

public class VjConceptdetail implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_langset")
    private long idLangset;
    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_term")
    private long idTerm;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "term_form")
    private String termForm;
    @Lob
    @Column(name = "term_source")
    private String termSource;
    @Lob
    @Column(name = "term_definition")
    private String termDefinition;
    @Lob
    @Column(name = "term_note")
    private String termNote;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;

    public VjConceptdetail() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(long idTerm) {
        this.idTerm = idTerm;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
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

    public String getTermNote() {
        return termNote;
    }

    public void setTermNote(String termNote) {
        this.termNote = termNote;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    public long getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(long idLangset) {
        this.idLangset = idLangset;
    }
    
}
