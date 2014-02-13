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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descriptors.findAll", query = "SELECT d FROM Descriptors d"),
    @NamedQuery(name = "Descriptors.findByTypeObj", query = "SELECT d FROM Descriptors d WHERE d.descriptorsPK.typeObj = :typeObj"),
    @NamedQuery(name = "Descriptors.findByIdObj", query = "SELECT d FROM Descriptors d WHERE d.descriptorsPK.idObj = :idObj")})
public class Descriptors implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DescriptorsPK descriptorsPK;
    @Lob
    private String header;
    @Lob
    private String footer;

    public Descriptors() {
    }

    public Descriptors(DescriptorsPK descriptorsPK) {
        this.descriptorsPK = descriptorsPK;
    }

    public Descriptors(char typeObj, long idObj) {
        this.descriptorsPK = new DescriptorsPK(typeObj, idObj);
    }

    public DescriptorsPK getDescriptorsPK() {
        return descriptorsPK;
    }

    public void setDescriptorsPK(DescriptorsPK descriptorsPK) {
        this.descriptorsPK = descriptorsPK;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (descriptorsPK != null ? descriptorsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descriptors)) {
            return false;
        }
        Descriptors other = (Descriptors) object;
        if ((this.descriptorsPK == null && other.descriptorsPK != null) || (this.descriptorsPK != null && !this.descriptorsPK.equals(other.descriptorsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Descriptors[ descriptorsPK=" + descriptorsPK + " ]";
    }
    
}
