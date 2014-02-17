/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "translations")
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
    @Column(name = "label")
    private String label;

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
