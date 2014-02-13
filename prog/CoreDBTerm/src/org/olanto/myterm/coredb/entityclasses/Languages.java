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
import javax.persistence.Id;
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
    @NamedQuery(name = "Languages.findAll", query = "SELECT l FROM Languages l"),
    @NamedQuery(name = "Languages.findByIdLanguage", query = "SELECT l FROM Languages l WHERE l.idLanguage = :idLanguage"),
    @NamedQuery(name = "Languages.findByLanguageDefaultName", query = "SELECT l FROM Languages l WHERE l.languageDefaultName = :languageDefaultName")})
public class Languages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "language_default_name")
    private String languageDefaultName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLanguage")
    private Collection<Terms> termsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languages")
    private Collection<Translations> translationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLanguage")
    private Collection<Langsets> langsetsCollection;

    public Languages() {
    }

    public Languages(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public Languages(String idLanguage, String languageDefaultName) {
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

    @XmlTransient
    public Collection<Terms> getTermsCollection() {
        return termsCollection;
    }

    public void setTermsCollection(Collection<Terms> termsCollection) {
        this.termsCollection = termsCollection;
    }

    @XmlTransient
    public Collection<Translations> getTranslationsCollection() {
        return translationsCollection;
    }

    public void setTranslationsCollection(Collection<Translations> translationsCollection) {
        this.translationsCollection = translationsCollection;
    }

    @XmlTransient
    public Collection<Langsets> getLangsetsCollection() {
        return langsetsCollection;
    }

    public void setLangsetsCollection(Collection<Langsets> langsetsCollection) {
        this.langsetsCollection = langsetsCollection;
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
        if (!(object instanceof Languages)) {
            return false;
        }
        Languages other = (Languages) object;
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
