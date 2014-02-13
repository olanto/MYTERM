/**********
    Copyright © 2013-2014 Olanto Foundation Geneva

   This file is part of myTERM.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author simple
 */
@Embeddable
public class DescriptorsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "type_obj")
    private char typeObj;
    @Basic(optional = false)
    @Column(name = "id_obj")
    private long idObj;

    public DescriptorsPK() {
    }

    public DescriptorsPK(char typeObj, long idObj) {
        this.typeObj = typeObj;
        this.idObj = idObj;
    }

    public char getTypeObj() {
        return typeObj;
    }

    public void setTypeObj(char typeObj) {
        this.typeObj = typeObj;
    }

    public long getIdObj() {
        return idObj;
    }

    public void setIdObj(long idObj) {
        this.idObj = idObj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) typeObj;
        hash += (int) idObj;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DescriptorsPK)) {
            return false;
        }
        DescriptorsPK other = (DescriptorsPK) object;
        if (this.typeObj != other.typeObj) {
            return false;
        }
        if (this.idObj != other.idObj) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.DescriptorsPK[ typeObj=" + typeObj + ", idObj=" + idObj + " ]";
    }
    
}
