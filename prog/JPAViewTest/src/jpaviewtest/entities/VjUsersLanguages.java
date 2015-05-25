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
    @NamedQuery(name = "VjUsersLanguages.findAll", query = "SELECT v FROM VjUsersLanguages v"),
    @NamedQuery(name = "VjUsersLanguages.findByUuid", query = "SELECT v FROM VjUsersLanguages v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjUsersLanguages.findByIdOwner", query = "SELECT v FROM VjUsersLanguages v WHERE v.idOwner = :idOwner"),
    @NamedQuery(name = "VjUsersLanguages.findByIdLanguage", query = "SELECT v FROM VjUsersLanguages v WHERE v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjUsersLanguages.findByLanguageDefaultName", query = "SELECT v FROM VjUsersLanguages v WHERE v.languageDefaultName = :languageDefaultName")})
public class VjUsersLanguages implements Serializable {
    @Basic(optional = false)
    @Column(name = "owner_first_name")
    private String ownerFirstName;
    @Basic(optional = false)
    @Column(name = "owner_mailing")
    private String ownerMailing;

    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "language_default_name")
    private String languageDefaultName;

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
}
