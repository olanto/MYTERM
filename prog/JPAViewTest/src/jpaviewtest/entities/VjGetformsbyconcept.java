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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simple
 */
@Entity
@Table(name = "vj_getformsbyconcept")
@XmlRootElement
@NamedQueries({
    //    @NamedQuery(name = "VjGetformsbyconcept.findAll", query = "SELECT v FROM VjGetformsbyconcept v"),
    //    @NamedQuery(name = "VjGetformsbyconcept.findByUuid", query = "SELECT v FROM VjGetformsbyconcept v WHERE v.uuid = :uuid"),
    //    @NamedQuery(name = "VjGetformsbyconcept.findByIdConcept", query = "SELECT v FROM VjGetformsbyconcept v WHERE v.idConcept = :idConcept"),
    @NamedQuery(name = "VjGetformsbyconcept.findformsExceptsolang", query = "SELECT v FROM VjGetformsbyconcept v WHERE v.idConcept = :idConcept AND v.idLanguage <> :idLanguage AND v.source <> '?'"),
    @NamedQuery(name = "VjGetformsbyconcept.findByLC", query = "SELECT v FROM VjGetformsbyconcept v WHERE v.idConcept = :idConcept AND v.idLanguage = :idLanguage")})

public class VjGetformsbyconcept implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Lob
    @Column(name = "source")
    private String source;

    public VjGetformsbyconcept() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
