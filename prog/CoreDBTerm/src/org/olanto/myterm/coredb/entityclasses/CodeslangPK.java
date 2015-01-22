/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author simple
 */
@Embeddable
public class CodeslangPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "code_type")
    private String codeType;
    @Basic(optional = false)
    @Column(name = "code_value")
    private String codeValue;

    public CodeslangPK() {
    }

    public CodeslangPK(String idLanguage, String codeType, String codeValue) {
        this.idLanguage = idLanguage;
        this.codeType = codeType;
        this.codeValue = codeValue;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLanguage != null ? idLanguage.hashCode() : 0);
        hash += (codeType != null ? codeType.hashCode() : 0);
        hash += (codeValue != null ? codeValue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodeslangPK)) {
            return false;
        }
        CodeslangPK other = (CodeslangPK) object;
        if ((this.idLanguage == null && other.idLanguage != null) || (this.idLanguage != null && !this.idLanguage.equals(other.idLanguage))) {
            return false;
        }
        if ((this.codeType == null && other.codeType != null) || (this.codeType != null && !this.codeType.equals(other.codeType))) {
            return false;
        }
        if ((this.codeValue == null && other.codeValue != null) || (this.codeValue != null && !this.codeValue.equals(other.codeValue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.CodeslangPK[ idLanguage=" + idLanguage + ", codeType=" + codeType + ", codeValue=" + codeValue + " ]";
    }
    
}
