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
public class UserLanguageDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String langID;
    private Long ownerID;

    public UserLanguageDTO() {
    }

    public Long getIdOwner() {
        return ownerID;
    }

    public void setIdOwner(Long ownerID) {
        this.ownerID = ownerID;
    }

    public String getIdLang() {
        return langID;
    }

    public void setIdLang(String langID) {
        this.langID = langID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ownerID != null ? ownerID.hashCode() : 0);
        hash += (langID != null ? langID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLanguageDTO)) {
            return false;
        }
        UserLanguageDTO other = (UserLanguageDTO) object;
        if ((this.langID == null && other.langID != null) || (this.langID != null && !this.langID.equals(other.langID))) {
            return false;
        }
        if ((this.ownerID == null && other.ownerID != null) || (this.ownerID != null && !this.ownerID.equals(other.ownerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.UsersLanguages[ idOwner=" + ownerID + ", langID" + langID + " ]";
    }
}
