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
@Table(name = "langsets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Langsets.findAll", query = "SELECT l FROM Langsets l"),
    @NamedQuery(name = "Langsets.findByIdLangset", query = "SELECT l FROM Langsets l WHERE l.idLangset = :idLangset"),
    @NamedQuery(name = "Langsets.findByIdLanguage", query = "SELECT l FROM Langsets l WHERE l.idLanguage = :idLanguage"),
    @NamedQuery(name = "Langsets.findByIdConcept", query = "SELECT l FROM Langsets l WHERE l.idConcept = :idConcept"),
    @NamedQuery(name = "Langsets.findBySeq", query = "SELECT l FROM Langsets l WHERE l.seq = :seq"),
    @NamedQuery(name = "Langsets.findByExtra", query = "SELECT l FROM Langsets l WHERE l.extra = :extra")})
public class Langsets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_langset")
    private Long idLangset;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;
    @Column(name = "langset_note")
    private String langsetNote;
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "extra")
    private String extra;

    public Langsets() {
    }

    public Langsets(Long idLangset) {
        this.idLangset = idLangset;
    }

    public Langsets(Long idLangset, String idLanguage, long idConcept) {
        this.idLangset = idLangset;
        this.idLanguage = idLanguage;
        this.idConcept = idConcept;
    }

    public Long getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(Long idLangset) {
        this.idLangset = idLangset;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
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
        hash += (idLangset != null ? idLangset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Langsets)) {
            return false;
        }
        Langsets other = (Langsets) object;
        if ((this.idLangset == null && other.idLangset != null) || (this.idLangset != null && !this.idLangset.equals(other.idLangset))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Langsets[ idLangset=" + idLangset + " ]";
    }

    /**
     * @return the langsetNote
     */
    public String getLangsetNote() {
        return langsetNote;
    }

    /**
     * @param langsetNote the langsetNote to set
     */
    public void setLangsetNote(String langsetNote) {
        this.langsetNote = langsetNote;
    }
    
}
