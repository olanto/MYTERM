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
public class LangSetDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private Long idLangset;
    private String idLanguage;
    private long idConcept;
    private String langsetNote;
    private Integer seq;
    private String extra;

    public LangSetDTO() {
    }

    public LangSetDTO(Long idLangset) {
        this.idLangset = idLangset;
    }

    public LangSetDTO(Long idLangset, String idLanguage, long idConcept) {
        this.idLangset = idLangset;
        this.idLanguage = idLanguage;
        this.idConcept = idConcept;
    }

    public Long getIdLangset() {
        return idLangset;
    }

    public void setIdLangset(Long idLangset) {
        this.idLangset = idLangset;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
        hash += (idLangset != null ? idLangset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LangSetDTO)) {
            return false;
        }
        LangSetDTO other = (LangSetDTO) object;
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
     * @return the langsetNote
     */
    public String getLangsetNote() {
        return langsetNote;
    }

    /**
     * @param langsetNote the langsetNote to set
     */
    public void setLangsetNote(String langsetNote) {
        this.langsetNote = langsetNote;
    }
}
