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
public class ResourceDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idResource;
    private String ownerMailing;
    private String resourceName;
    private String resourcePrivacy;
    private String resourceNote;
    private String extra;

    public ResourceDTO() {
    }

    public ResourceDTO(Long idResource) {
        this.idResource = idResource;
    }

    public ResourceDTO(Long idResource, String ownerMailing, String resourceName, String resourcePrivacy) {
        this.idResource = idResource;
        this.ownerMailing = ownerMailing;
        this.resourceName = resourceName;
        this.resourcePrivacy = resourcePrivacy;
    }

    public ResourceDTO(Long idResource, String resourceName, String resourcePrivacy) {
        this.idResource = idResource;
        this.resourceName = resourceName;
        this.resourcePrivacy = resourcePrivacy;
    }

    public Long getIdResource() {
        return idResource;
    }

    public void setIdResource(Long idResource) {
        this.idResource = idResource;
    }

    public String getOwnerMailing() {
        return ownerMailing;
    }

    public void setOwnerMailing(String ownerMailing) {
        this.ownerMailing = ownerMailing;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
        if (!(object instanceof ResourceDTO)) {
            return false;
        }
        ResourceDTO other = (ResourceDTO) object;
        if ((this.idResource == null && other.idResource != null) || (this.idResource != null && !this.idResource.equals(other.idResource))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Resources[ idResource=" + idResource + " ]";
    }

    /**
     * @return the resourceNote
     */
    public String getResourceNote() {
        return resourceNote;
    }

    /**
     * @param resourceNote the resourceNote to set
     */
    public void setResourceNote(String resourceNote) {
        this.resourceNote = resourceNote;
    }
}
