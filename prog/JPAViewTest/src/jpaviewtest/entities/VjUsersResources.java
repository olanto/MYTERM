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
@Table(name = "vj_users_resources")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjUsersResources.findAll", query = "SELECT v FROM VjUsersResources v"),
    @NamedQuery(name = "VjUsersResources.findByUuid", query = "SELECT v FROM VjUsersResources v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjUsersResources.findByIdOwner", query = "SELECT v FROM VjUsersResources v WHERE v.idOwner = :idOwner"),
    @NamedQuery(name = "VjUsersResources.findByOwnerMailing", query = "SELECT v FROM VjUsersResources v WHERE v.ownerMailing = :ownerMailing"),
    @NamedQuery(name = "VjUsersResources.findByOwnerMailingOwnerRoles", query = "SELECT v FROM VjUsersResources v WHERE v.ownerMailing = :ownerMailing AND v.ownerRoles = :ownerRoles"),
    @NamedQuery(name = "VjUsersResources.findByIdResource", query = "SELECT v FROM VjUsersResources v WHERE v.idResource = :idResource"),
    @NamedQuery(name = "VjUsersResources.findByResourceName", query = "SELECT v FROM VjUsersResources v WHERE v.resourceName = :resourceName"),
    @NamedQuery(name = "VjUsersResources.findByResourcePrivacy", query = "SELECT v FROM VjUsersResources v WHERE v.resourcePrivacy = :resourcePrivacy"),
    @NamedQuery(name = "VjUsersResources.findByIdOwnerIdResource", query = "SELECT v FROM VjUsersResources v WHERE v.idOwner = :idOwner AND v.idResource = :idResource"),
    @NamedQuery(name = "VjUsersResources.findByIdResourceOwnerRoles", query = "SELECT v FROM VjUsersResources v WHERE v.idResource = :idResource AND v.ownerRoles = :ownerRoles"),
    @NamedQuery(name = "VjUsersResources.findByIdOwnerIdResourceOwnerRoles", query = "SELECT v FROM VjUsersResources v WHERE v.idOwner = :idOwner AND v.idResource = :idResource AND v.ownerRoles = :ownerRoles"),
    @NamedQuery(name = "VjUsersResources.findByIdOwnerOwnerRoles", query = "SELECT v FROM VjUsersResources v WHERE v.idOwner = :idOwner AND v.ownerRoles = :ownerRoles"),
    @NamedQuery(name = "VjUsersResources.findByOwnerRoles", query = "SELECT v FROM VjUsersResources v WHERE v.ownerRoles = :ownerRoles")})
public class VjUsersResources implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Basic(optional = false)
    @Column(name = "owner_first_name")
    private String ownerFirstName;
    @Basic(optional = false)
    @Column(name = "owner_mailing")
    private String ownerMailing;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "resource_privacy")
    private String resourcePrivacy;
    @Lob
    @Column(name = "resource_note")
    private String resourceNote;
    @Column(name = "owner_roles")
    private String ownerRoles;
    @Basic(optional = false)
    @Column(name = "owner_last_name", nullable = false, length = 32)
    private String ownerLastName;

    public VjUsersResources() {
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

    public String getOwnerMailing() {
        return ownerMailing;
    }

    public void setOwnerMailing(String ownerMailing) {
        this.ownerMailing = ownerMailing;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePrivacy() {
        return resourcePrivacy;
    }

    public void setResourcePrivacy(String resourcePrivacy) {
        this.resourcePrivacy = resourcePrivacy;
    }

    public String getResourceNote() {
        return resourceNote;
    }

    public void setResourceNote(String resourceNote) {
        this.resourceNote = resourceNote;
    }

    public String getOwnerRoles() {
        return ownerRoles;
    }

    public void setOwnerRoles(String ownerRoles) {
        this.ownerRoles = ownerRoles;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }
}
