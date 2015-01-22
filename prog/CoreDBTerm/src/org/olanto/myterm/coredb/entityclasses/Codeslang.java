/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "codeslang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codeslang.findAll", query = "SELECT c FROM Codeslang c"),
    @NamedQuery(name = "Codeslang.findByIdLanguage", query = "SELECT c FROM Codeslang c WHERE c.codeslangPK.idLanguage = :idLanguage"),
    @NamedQuery(name = "Codeslang.findByCodeType", query = "SELECT c FROM Codeslang c WHERE c.codeslangPK.codeType = :codeType"),
    @NamedQuery(name = "Codeslang.findByCodeValue", query = "SELECT c FROM Codeslang c WHERE c.codeslangPK.codeValue = :codeValue"),
    @NamedQuery(name = "Codeslang.findByCodeValueLang", query = "SELECT c FROM Codeslang c WHERE c.codeValueLang = :codeValueLang"),
    @NamedQuery(name = "Codeslang.findByCodeExtraLang", query = "SELECT c FROM Codeslang c WHERE c.codeExtraLang = :codeExtraLang")})
public class Codeslang implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodeslangPK codeslangPK;
    @Basic(optional = false)
    @Column(name = "code_value_lang")
    private String codeValueLang;
    @Column(name = "code_extra_lang")
    private String codeExtraLang;

    public Codeslang() {
    }

    public Codeslang(CodeslangPK codeslangPK) {
        this.codeslangPK = codeslangPK;
    }

    public Codeslang(CodeslangPK codeslangPK, String codeValueLang) {
        this.codeslangPK = codeslangPK;
        this.codeValueLang = codeValueLang;
    }

    public Codeslang(String idLanguage, String codeType, String codeValue) {
        this.codeslangPK = new CodeslangPK(idLanguage, codeType, codeValue);
    }

    public CodeslangPK getCodeslangPK() {
        return codeslangPK;
    }

    public void setCodeslangPK(CodeslangPK codeslangPK) {
        this.codeslangPK = codeslangPK;
    }

    public String getCodeValueLang() {
        return codeValueLang;
    }

    public void setCodeValueLang(String codeValueLang) {
        this.codeValueLang = codeValueLang;
    }

    public String getCodeExtraLang() {
        return codeExtraLang;
    }

    public void setCodeExtraLang(String codeExtraLang) {
        this.codeExtraLang = codeExtraLang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeslangPK != null ? codeslangPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codeslang)) {
            return false;
        }
        Codeslang other = (Codeslang) object;
        if ((this.codeslangPK == null && other.codeslangPK != null) || (this.codeslangPK != null && !this.codeslangPK.equals(other.codeslangPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Codeslang[ codeslangPK=" + codeslangPK + " ]";
    }
    
}
