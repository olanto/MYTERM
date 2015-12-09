/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "vj_codifications")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "VjCodifications.findAll", query = "SELECT v FROM VjCodifications v"),
//    @NamedQuery(name = "VjCodifications.findByUuid", query = "SELECT v FROM VjCodifications v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjCodifications.findFieldsByLanguage", query = "SELECT v FROM VjCodifications v WHERE v.codeType = :codeType AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findTermTypesByLanguage", query = "SELECT v.codeValueLang FROM VjCodifications v WHERE v.codeType LIKE 'term_type' AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findTermPOSByLanguage", query = "SELECT v FROM VjCodifications v WHERE v.codeType LIKE 'term_partofspeech' AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findTermGenderByLanguage", query = "SELECT v.codeValueLang FROM VjCodifications v WHERE v.codeType LIKE 'term_gender' AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findOwnerRolesByLanguage", query = "SELECT v FROM VjCodifications v WHERE v.codeType LIKE 'role' AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findOwnerStatusByLanguage", query = "SELECT v.codeValueLang FROM VjCodifications v WHERE v.codeType LIKE 'owner_status' AND v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjCodifications.findResourcePrivacyByLanguage", query = "SELECT v.codeValueLang FROM VjCodifications v WHERE v.codeType LIKE 'privacy' AND v.idLanguage = :idLanguage"),
//    @NamedQuery(name = "VjCodifications.findByIdLanguage", query = "SELECT v FROM VjCodifications v WHERE v.idLanguage = :idLanguage"),
//    @NamedQuery(name = "VjCodifications.findByCodeType", query = "SELECT v FROM VjCodifications v WHERE v.codeType = :codeType"),
//    @NamedQuery(name = "VjCodifications.findByCodeValue", query = "SELECT v FROM VjCodifications v WHERE v.codeValue = :codeValue"),
//    @NamedQuery(name = "VjCodifications.findByCodeExtra", query = "SELECT v FROM VjCodifications v WHERE v.codeExtra = :codeExtra"),
//    @NamedQuery(name = "VjCodifications.findByCodeValueLang", query = "SELECT v FROM VjCodifications v WHERE v.codeValueLang = :codeValueLang"),
//    @NamedQuery(name = "VjCodifications.findByCodeExtraLang", query = "SELECT v FROM VjCodifications v WHERE v.codeExtraLang = :codeExtraLang"),
//    @NamedQuery(name = "VjCodifications.findByCodeDefault", query = "SELECT v FROM VjCodifications v WHERE v.codeDefault = :codeDefault")
})
public class VjCodifications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "code_type")
    private String codeType;
    @Basic(optional = false)
    @Column(name = "code_value")
    private String codeValue;
    @Column(name = "code_extra")
    private String codeExtra;
    @Basic(optional = false)
    @Column(name = "code_value_lang")
    private String codeValueLang;
    @Column(name = "code_extra_lang")
    private String codeExtraLang;
    @Column(name = "code_default")
    private String codeDefault;

    public VjCodifications() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeExtra() {
        return codeExtra;
    }

    public void setCodeExtra(String codeExtra) {
        this.codeExtra = codeExtra;
    }

    public String getCodeValueLang() {
        return codeValueLang;
    }

    public void setCodeValueLang(String codeValueLang) {
        this.codeValueLang = codeValueLang;
    }

    public String getCodeExtraLang() {
        return codeExtraLang;
    }

    public void setCodeExtraLang(String codeExtraLang) {
        this.codeExtraLang = codeExtraLang;
    }

    public String getCodeDefault() {
        return codeDefault;
    }

    public void setCodeDefault(String codeDefault) {
        this.codeDefault = codeDefault;
    }
    
}
