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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "owners")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Owners.findAll", query = "SELECT o FROM Owners o ORDER BY o.ownerLastName, o.ownerFirstName"),
    @NamedQuery(name = "Owners.findByIdOwner", query = "SELECT o FROM Owners o WHERE o.idOwner = :idOwner"),
    @NamedQuery(name = "Owners.findByOwnerFirstName", query = "SELECT o FROM Owners o WHERE o.ownerFirstName = :ownerFirstName"),
    @NamedQuery(name = "Owners.findByOwnerLastName", query = "SELECT o FROM Owners o WHERE o.ownerLastName = :ownerLastName"),
    @NamedQuery(name = "Owners.findByOwnerMailing", query = "SELECT o FROM Owners o WHERE o.ownerMailing LIKE :ownerMailing ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerMailingStatus", query = "SELECT o FROM Owners o WHERE o.ownerMailing LIKE :ownerMailing AND o.ownerStatus LIKE :ownerStatus ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerMailingRole", query = "SELECT o FROM Owners o WHERE o.ownerMailing LIKE :ownerMailing AND o.ownerRoles LIKE :ownerRoles ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerRoleStatus", query = "SELECT o FROM Owners o WHERE o.ownerStatus LIKE :ownerStatus AND o.ownerRoles LIKE :ownerRoles ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerMailingStatusRole", query = "SELECT o FROM Owners o WHERE o.ownerMailing LIKE :ownerMailing AND o.ownerStatus LIKE :ownerStatus AND o.ownerRoles LIKE :ownerRoles ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerMailingAndHash", query = "SELECT o FROM Owners o WHERE o.ownerMailing = :ownerMailing AND o.ownerHash = :ownerHash"),
    @NamedQuery(name = "Owners.findByOwnerHash", query = "SELECT o FROM Owners o WHERE o.ownerHash = :ownerHash"),
    @NamedQuery(name = "Owners.findByOwnerRole", query = "SELECT o FROM Owners o WHERE o.ownerRoles LIKE :ownerRoles ORDER BY o.idOwner DESC"),
    @NamedQuery(name = "Owners.findByOwnerStatus", query = "SELECT o FROM Owners o WHERE o.ownerStatus LIKE :ownerStatus ORDER BY o.idOwner DESC")})
public class Owners implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_owner")
    private Long idOwner;
    @Basic(optional = false)
    @Column(name = "owner_first_name")
    private String ownerFirstName;
    @Basic(optional = false)
    @Column(name = "owner_last_name")
    private String ownerLastName;
    @Basic(optional = false)
    @Column(name = "owner_mailing")
    private String ownerMailing;
    @Column(name = "owner_hash")
    private String ownerHash;
    @Basic(optional = false)
    @Column(name = "owner_status")
    private String ownerStatus;
    @Column(name = "owner_roles")
    private String ownerRoles;

    public Owners() {
    }

    public Owners(Long idOwner) {
        this.idOwner = idOwner;
    }

    public Owners(Long idOwner, String ownerFirstName, String ownerLastName, String ownerMailing, String ownerStatus) {
        this.idOwner = idOwner;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerMailing = ownerMailing;
        this.ownerStatus = ownerStatus;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
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

    public String getOwnerMailing() {
        return ownerMailing;
    }

    public void setOwnerMailing(String ownerMailing) {
        this.ownerMailing = ownerMailing;
    }

    public String getOwnerHash() {
        return ownerHash;
    }

    public void setOwnerHash(String ownerHash) {
        this.ownerHash = ownerHash;
    }

    public String getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(String ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOwner != null ? idOwner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Owners)) {
            return false;
        }
        Owners other = (Owners) object;
        if ((this.idOwner == null && other.idOwner != null) || (this.idOwner != null && !this.idOwner.equals(other.idOwner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Owners[ idOwner=" + idOwner + " ]";
    }

    /**
     * @return the ownerRoles
     */
    public String getOwnerRoles() {
        return ownerRoles;
    }

    /**
     * @param ownerRoles the ownerRoles to set
     */
    public void setOwnerRoles(String ownerRoles) {
        this.ownerRoles = ownerRoles;
    }
}
