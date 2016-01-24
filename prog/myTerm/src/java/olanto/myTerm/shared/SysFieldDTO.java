/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package olanto.myTerm.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author nizar ghoula - simple
 */
public class SysFieldDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String idField;
    private String type;
    private Boolean publicVisibility = false;
    private Boolean isExtraPublic = false;
    private Boolean isHiddenPublic = false;
    private Boolean formVisibility = false;
    private Boolean isExtraForm = false;
    private Boolean isHiddenForm = false;
    private int position = 1;

    public SysFieldDTO() {
    }

    public SysFieldDTO(String type, String visibilityPublic, String position, String visibilityForm) {
        this.idField = "sys_field" + type;
        this.type = type;
        if (visibilityPublic.equalsIgnoreCase("visible")) {
            this.publicVisibility = true;
        } else if (visibilityPublic.equalsIgnoreCase("extra")) {
            this.isExtraPublic = true;
        } else if (visibilityPublic.equalsIgnoreCase("hidden")) {
            this.isHiddenPublic = true;
        }
        if (visibilityForm.equalsIgnoreCase("visible")) {
            this.formVisibility = true;
        } else if (visibilityForm.equalsIgnoreCase("extra")) {
            this.isExtraForm = true;
        } else if (visibilityForm.equalsIgnoreCase("hidden")) {
            this.isHiddenForm = true;
        }
        if (!position.isEmpty()) {
            this.position = Integer.parseInt(position);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

        public Boolean getVisibilityForm() {
        return formVisibility;
    }

    public Boolean isExtraForm() {
        return isExtraForm;
    }

    public Boolean isHiddenForm() {
        return isHiddenForm;
    }

    public void setVisibilityForm(Boolean formVisibility) {
        this.formVisibility = formVisibility;
    }

    public void setAsExtraForm(Boolean isExtraForm) {
        this.isExtraForm = isExtraForm;
    }
    
    public void setAsHiddenForm(Boolean isHiddenForm) {
        this.isHiddenForm = isHiddenForm;
    }
    
    public Boolean getVisibilityPublic() {
        return publicVisibility;
    }

    public Boolean isExtraPublic() {
        return isExtraPublic;
    }

    public Boolean isHiddenPublic() {
        return isHiddenPublic;
    }

    public void setVisibilityPublic(Boolean visibility) {
        this.publicVisibility = visibility;
    }

    public void setAsExtraPublic(Boolean isExtra) {
        this.isExtraPublic = isExtra;
    }
    
    public void setAsHiddenPublic(Boolean isHiddenPublic) {
        this.isHiddenPublic = isHiddenPublic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idField != null ? idField.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysFieldDTO)) {
            return false;
        }
        SysFieldDTO other = (SysFieldDTO) object;
        if ((this.idField == null && other.idField != null) || (this.idField != null && !this.idField.equals(other.idField))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Field Type: " + this.type + "\nField Value: " + this.publicVisibility + ";" + this.position;
    }
}
