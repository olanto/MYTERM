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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author simple
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Langsets.findAll", query = "SELECT l FROM Langsets l"),
    @NamedQuery(name = "Langsets.findByIdLangset", query = "SELECT l FROM Langsets l WHERE l.idLangset = :idLangset"),
    @NamedQuery(name = "Langsets.findBySeq", query = "SELECT l FROM Langsets l WHERE l.seq = :seq")})
public class Langsets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_langset")
    private Long idLangset;
      @Column(name = "extra")
    private String extra;
  private Integer seq;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLangset")
    private Collection<Terms> termsCollection;
    @JoinColumn(name = "id_language", referencedColumnName = "id_language")
    @ManyToOne(optional = false)
    private Languages idLanguage;
    @JoinColumn(name = "id_concept", referencedColumnName = "id_concept")
    @ManyToOne(optional = false)
    private Concepts idConcept;

    public Langsets() {
    }

    public Langsets(Long idLangset) {
        this.idLangset = idLangset;
    }

    public Long getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(Long idLangset) {
        this.idLangset = idLangset;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @XmlTransient
    public Collection<Terms> getTermsCollection() {
        return termsCollection;
    }

    public void setTermsCollection(Collection<Terms> termsCollection) {
        this.termsCollection = termsCollection;
    }

    public Languages getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Languages idLanguage) {
        this.idLanguage = idLanguage;
    }

    public Concepts getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(Concepts idConcept) {
        this.idConcept = idConcept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLangset != null ? idLangset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Langsets)) {
            return false;
        }
        Langsets other = (Langsets) object;
        if ((this.idLangset == null && other.idLangset != null) || (this.idLangset != null && !this.idLangset.equals(other.idLangset))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Langsets[ idLangset=" + idLangset + " ]";
    }

    /**
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }
    
}
