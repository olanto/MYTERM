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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "vj_users_languages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjUsersLanguages.findAll", query = "SELECT v FROM VjUsersLanguages v order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByUuid", query = "SELECT v FROM VjUsersLanguages v WHERE v.uuid = :uuid order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByIdLink", query = "SELECT v FROM VjUsersLanguages v WHERE v.idLink = :idLink order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByIdOwner", query = "SELECT v FROM VjUsersLanguages v WHERE v.idOwner = :idOwner order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByIdLanguage", query = "SELECT v FROM VjUsersLanguages v WHERE v.idLanguage = :idLanguage order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByIdOwnerIdLanguage", query = "SELECT v FROM VjUsersLanguages v WHERE v.idOwner = :idOwner AND v.idLanguage = :idLanguage order by v.ownerMailing asc"),
    @NamedQuery(name = "VjUsersLanguages.findByLanguageDefaultName", query = "SELECT v FROM VjUsersLanguages v WHERE v.languageDefaultName = :languageDefaultName order by v.ownerMailing asc")})
public class VjUsersLanguages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_link", nullable = false)
    private long idLink;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "language_default_name")
    private String languageDefaultName;
    @Basic(optional = false)
    @Column(name = "owner_first_name")
    private String ownerFirstName;
    @Basic(optional = false)
    @Column(name = "owner_mailing")
    private String ownerMailing;
    @Basic(optional = false)
    @Column(name = "owner_last_name", nullable = false, length = 32)
    private String ownerLastName;

    public VjUsersLanguages() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
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

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerMailing() {
        return ownerMailing;
    }

    public void setOwnerMailing(String ownerMailing) {
        this.ownerMailing = ownerMailing;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public long getIdLink() {
        return idLink;
    }

    public void setIdLink(long idLink) {
        this.idLink = idLink;
    }
}
