/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
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
@Table(name = "codes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codes.findAll", query = "SELECT c FROM Codes c"),
    @NamedQuery(name = "Codes.findByCodeType", query = "SELECT c FROM Codes c WHERE c.codesPK.codeType = :codeType"),
    @NamedQuery(name = "Codes.findByCodeValue", query = "SELECT c FROM Codes c WHERE c.codesPK.codeValue = :codeValue"),
    @NamedQuery(name = "Codes.findByCodeExtra", query = "SELECT c FROM Codes c WHERE c.codeExtra = :codeExtra"),
    @NamedQuery(name = "Codes.findByCodeDefault", query = "SELECT c FROM Codes c WHERE c.codeDefault = :codeDefault")})
public class Codes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodesPK codesPK;
    @Column(name = "code_extra")
    private String codeExtra;
    @Column(name = "code_default")
    private String codeDefault;

    public Codes() {
    }

    public Codes(CodesPK codesPK) {
        this.codesPK = codesPK;
    }

    public Codes(String codeType, String codeValue) {
        this.codesPK = new CodesPK(codeType, codeValue);
    }

    public CodesPK getCodesPK() {
        return codesPK;
    }

    public void setCodesPK(CodesPK codesPK) {
        this.codesPK = codesPK;
    }

    public String getCodeExtra() {
        return codeExtra;
    }

    public void setCodeExtra(String codeExtra) {
        this.codeExtra = codeExtra;
    }

    public String getCodeDefault() {
        return codeDefault;
    }

    public void setCodeDefault(String codeDefault) {
        this.codeDefault = codeDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codesPK != null ? codesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codes)) {
            return false;
        }
        Codes other = (Codes) object;
        if ((this.codesPK == null && other.codesPK != null) || (this.codesPK != null && !this.codesPK.equals(other.codesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Codes[ codesPK=" + codesPK + " ]";
    }
    
}
