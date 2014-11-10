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
    @NamedQuery(name = "VjUsersResources.findByIdResource", query = "SELECT v FROM VjUsersResources v WHERE v.idResource = :idResource"),
    @NamedQuery(name = "VjUsersResources.findByResourceName", query = "SELECT v FROM VjUsersResources v WHERE v.resourceName = :resourceName"),
    @NamedQuery(name = "VjUsersResources.findByResourcePrivacy", query = "SELECT v FROM VjUsersResources v WHERE v.resourcePrivacy = :resourcePrivacy")})
public class VjUsersResources implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
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
}
