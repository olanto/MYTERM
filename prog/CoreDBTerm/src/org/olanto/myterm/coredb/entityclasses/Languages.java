/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "languages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Languages.findAll", query = "SELECT l FROM Languages l"),
    @NamedQuery(name = "Languages.findByIdLanguage", query = "SELECT l FROM Languages l WHERE l.idLanguage = :idLanguage"),
    @NamedQuery(name = "Languages.findByLanguageDefaultName", query = "SELECT l FROM Languages l WHERE l.languageDefaultName = :languageDefaultName")})
public class Languages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "language_default_name")
    private String languageDefaultName;

    public Languages() {
    }

    public Languages(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public Languages(String idLanguage, String languageDefaultName) {
        this.idLanguage = idLanguage;
        this.languageDefaultName = languageDefaultName;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getLanguageDefaultName() {
        return languageDefaultName;
    }

    public void setLanguageDefaultName(String languageDefaultName) {
        this.languageDefaultName = languageDefaultName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLanguage != null ? idLanguage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Languages)) {
            return false;
        }
        Languages other = (Languages) object;
        if ((this.idLanguage == null && other.idLanguage != null) || (this.idLanguage != null && !this.idLanguage.equals(other.idLanguage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Languages[ idLanguage=" + idLanguage + " ]";
    }
    
}
