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
public class LanguageDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String idLanguage;
    private String languageDefaultName;

    public LanguageDTO() {
    }

    public LanguageDTO(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public LanguageDTO(String idLanguage, String languageDefaultName) {
        this.idLanguage = idLanguage;
        this.languageDefaultName = languageDefaultName;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getLanguageDefaultName() {
        return languageDefaultName;
    }

    public void setLanguageDefaultName(String languageDefaultName) {
        this.languageDefaultName = languageDefaultName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLanguage != null ? idLanguage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LanguageDTO)) {
            return false;
        }
        LanguageDTO other = (LanguageDTO) object;
        if ((this.idLanguage == null && other.idLanguage != null) || (this.idLanguage != null && !this.idLanguage.equals(other.idLanguage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Languages[ idLanguage=" + idLanguage + " ]";
    }
}