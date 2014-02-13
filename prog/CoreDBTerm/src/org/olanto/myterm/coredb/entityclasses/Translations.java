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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Translations.findAll", query = "SELECT t FROM Translations t"),
    @NamedQuery(name = "Translations.findByIdLanguage", query = "SELECT t FROM Translations t WHERE t.translationsPK.idLanguage = :idLanguage"),
    @NamedQuery(name = "Translations.findByTypeObj", query = "SELECT t FROM Translations t WHERE t.translationsPK.typeObj = :typeObj"),
    @NamedQuery(name = "Translations.findByIdObj", query = "SELECT t FROM Translations t WHERE t.translationsPK.idObj = :idObj"),
    @NamedQuery(name = "Translations.findByLabel", query = "SELECT t FROM Translations t WHERE t.label = :label")})
public class Translations implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TranslationsPK translationsPK;
    @Basic(optional = false)
    private String label;
    @JoinColumn(name = "id_language", referencedColumnName = "id_language", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Languages languages;

    public Translations() {
    }

    public Translations(TranslationsPK translationsPK) {
        this.translationsPK = translationsPK;
    }

    public Translations(TranslationsPK translationsPK, String label) {
        this.translationsPK = translationsPK;
        this.label = label;
    }

    public Translations(String idLanguage, String typeObj, long idObj) {
        this.translationsPK = new TranslationsPK(idLanguage, typeObj, idObj);
    }

    public TranslationsPK getTranslationsPK() {
        return translationsPK;
    }

    public void setTranslationsPK(TranslationsPK translationsPK) {
        this.translationsPK = translationsPK;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (translationsPK != null ? translationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Translations)) {
            return false;
        }
        Translations other = (Translations) object;
        if ((this.translationsPK == null && other.translationsPK != null) || (this.translationsPK != null && !this.translationsPK.equals(other.translationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Translations[ translationsPK=" + translationsPK + " ]";
    }
    
}
