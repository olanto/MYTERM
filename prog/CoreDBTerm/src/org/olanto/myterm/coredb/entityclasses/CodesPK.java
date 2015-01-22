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
public class CodesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "code_type")
    private String codeType;
    @Basic(optional = false)
    @Column(name = "code_value")
    private String codeValue;

    public CodesPK() {
    }

    public CodesPK(String codeType, String codeValue) {
        this.codeType = codeType;
        this.codeValue = codeValue;
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
        hash += (codeType != null ? codeType.hashCode() : 0);
        hash += (codeValue != null ? codeValue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodesPK)) {
            return false;
        }
        CodesPK other = (CodesPK) object;
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
        return "org.olanto.myterm.coredb.entityclasses.CodesPK[ codeType=" + codeType + ", codeValue=" + codeValue + " ]";
    }
    
}
