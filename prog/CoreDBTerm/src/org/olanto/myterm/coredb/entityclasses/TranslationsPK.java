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
public class TranslationsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "type_obj")
    private String typeObj;
    @Basic(optional = false)
    @Column(name = "id_obj")
    private long idObj;

    public TranslationsPK() {
    }

    public TranslationsPK(String idLanguage, String typeObj, long idObj) {
        this.idLanguage = idLanguage;
        this.typeObj = typeObj;
        this.idObj = idObj;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getTypeObj() {
        return typeObj;
    }

    public void setTypeObj(String typeObj) {
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
        hash += (idLanguage != null ? idLanguage.hashCode() : 0);
        hash += (typeObj != null ? typeObj.hashCode() : 0);
        hash += (int) idObj;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TranslationsPK)) {
            return false;
        }
        TranslationsPK other = (TranslationsPK) object;
        if ((this.idLanguage == null && other.idLanguage != null) || (this.idLanguage != null && !this.idLanguage.equals(other.idLanguage))) {
            return false;
        }
        if ((this.typeObj == null && other.typeObj != null) || (this.typeObj != null && !this.typeObj.equals(other.typeObj))) {
            return false;
        }
        if (this.idObj != other.idObj) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.TranslationsPK[ idLanguage=" + idLanguage + ", typeObj=" + typeObj + ", idObj=" + idObj + " ]";
    }
    
}
