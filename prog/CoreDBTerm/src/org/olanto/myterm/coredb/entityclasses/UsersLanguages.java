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
@Table(name = "users_languages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersLanguages.findAll", query = "SELECT u FROM UsersLanguages u ORDER BY u.idLanguage, u.idOwner"),
    @NamedQuery(name = "UsersLanguages.findByIdLink", query = "SELECT u FROM UsersLanguages u WHERE u.idLink = :idLink"),
    @NamedQuery(name = "UsersLanguages.findByIdLanguage", query = "SELECT u FROM UsersLanguages u WHERE u.idLanguage = :idLanguage"),
    @NamedQuery(name = "UsersLanguages.findByIdOwner", query = "SELECT u FROM UsersLanguages u WHERE u.idOwner = :idOwner")})
public class UsersLanguages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_link")
    private Long idLink;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Lob
    @Column(name = "extra")
    private String extra;

    public UsersLanguages() {
    }

    public UsersLanguages(Long idLink) {
        this.idLink = idLink;
    }

    public UsersLanguages(Long idLink, String idLanguage, long idOwner) {
        this.idLink = idLink;
        this.idLanguage = idLanguage;
        this.idOwner = idOwner;
    }

    public Long getIdLink() {
        return idLink;
    }

    public void setIdLink(Long idLink) {
        this.idLink = idLink;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
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
        hash += (idLink != null ? idLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersLanguages)) {
            return false;
        }
        UsersLanguages other = (UsersLanguages) object;
        if ((this.idLink == null && other.idLink != null) || (this.idLink != null && !this.idLink.equals(other.idLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.UsersLanguages[ idLink=" + idLink + " ]";
    }
    
}
