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
public class DomainDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idDomain;
    private String domainDefaultName;

    public DomainDTO() {
    }

    public DomainDTO(Long idDomain) {
        this.idDomain = idDomain;
    }

    public DomainDTO(Long idDomain, String domainDefaultName) {
        this.idDomain = idDomain;
        this.domainDefaultName = domainDefaultName;
    }

    public Long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(Long idDomain) {
        this.idDomain = idDomain;
    }

    public String getDomainDefaultName() {
        return domainDefaultName;
    }

    public void setDomainDefaultName(String domainDefaultName) {
        this.domainDefaultName = domainDefaultName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDomain != null ? idDomain.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DomainDTO)) {
            return false;
        }
        DomainDTO other = (DomainDTO) object;
        if ((this.idDomain == null && other.idDomain != null) || (this.idDomain != null && !this.idDomain.equals(other.idDomain))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Domains[ idDomain=" + idDomain + " ]";
    }
}
