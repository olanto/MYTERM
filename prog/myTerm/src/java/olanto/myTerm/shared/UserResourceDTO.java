/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author simple
 */
public class UserResourceDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idResource;
    private Long ownerID;
    private String ownerRole;

    public UserResourceDTO() {
    }

    public Long getIdResource() {
        return idResource;
    }

    public Long getIdOwner() {
        return ownerID;
    }

    public void setIdResource(Long idResource) {
        this.idResource = idResource;
    }

    public void setIdOwner(Long idowner) {
        this.ownerID = idowner;
    }

    public String getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(String ownerRole) {
        this.ownerRole = ownerRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResource != null ? idResource.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserResourceDTO)) {
            return false;
        }
        UserResourceDTO other = (UserResourceDTO) object;
        if ((this.idResource == null && other.idResource != null) || (this.idResource != null && !this.idResource.equals(other.idResource))) {
            return false;
        }
        if ((this.ownerID == null && other.ownerID != null) || (this.ownerID != null && !this.ownerID.equals(other.ownerID))) {
            return false;
        }
         if ((this.ownerRole == null && other.ownerRole != null) || (this.ownerRole != null && !this.ownerRole.equals(other.ownerRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.UsersResoures[ idResource=" + idResource + " , idOwner=" + ownerID + " , ownerRole=" + ownerRole + " ]";
    }
}
