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
@Table(name = "users_resources")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersResources.findAll", query = "SELECT u FROM UsersResources u"),
    @NamedQuery(name = "UsersResources.findByIdLink", query = "SELECT u FROM UsersResources u WHERE u.idLink = :idLink"),
    @NamedQuery(name = "UsersResources.findByIdResource", query = "SELECT u FROM UsersResources u WHERE u.idResource = :idResource"),
    @NamedQuery(name = "UsersResources.findByIdOwner", query = "SELECT u FROM UsersResources u WHERE u.idOwner = :idOwner"),
    @NamedQuery(name = "UsersResources.findByOwnerRoles", query = "SELECT u FROM UsersResources u WHERE u.ownerRoles = :ownerRoles"),
    @NamedQuery(name = "UsersResources.findByDefaultResource", query = "SELECT u FROM UsersResources u WHERE u.defaultResource = :defaultResource")})
public class UsersResources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_link")
    private Long idLink;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;
    @Column(name = "owner_roles")
    private String ownerRoles;
    @Column(name = "default_resource")
    private String defaultResource;
    @Lob
    @Column(name = "extra")
    private String extra;

    public UsersResources() {
    }

    public UsersResources(Long idLink) {
        this.idLink = idLink;
    }

    public UsersResources(Long idLink, long idResource, long idOwner) {
        this.idLink = idLink;
        this.idResource = idResource;
        this.idOwner = idOwner;
    }

    public Long getIdLink() {
        return idLink;
    }

    public void setIdLink(Long idLink) {
        this.idLink = idLink;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }

    public String getOwnerRoles() {
        return ownerRoles;
    }

    public void setOwnerRoles(String ownerRoles) {
        this.ownerRoles = ownerRoles;
    }

    public String getDefaultResource() {
        return defaultResource;
    }

    public void setDefaultResource(String defaultResource) {
        this.defaultResource = defaultResource;
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
        if (!(object instanceof UsersResources)) {
            return false;
        }
        UsersResources other = (UsersResources) object;
        if ((this.idLink == null && other.idLink != null) || (this.idLink != null && !this.idLink.equals(other.idLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.UsersResources[ idLink=" + idLink + " ]";
    }
    
}
