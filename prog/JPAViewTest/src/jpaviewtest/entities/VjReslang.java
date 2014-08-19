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
@Table(name = "vj_reslang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjReslang.findAll", query = "SELECT v FROM VjReslang v"),
    @NamedQuery(name = "VjReslang.findByUuid", query = "SELECT v FROM VjReslang v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjReslang.findByResourceName", query = "SELECT v FROM VjReslang v WHERE v.resourceName = :resourceName"),
    @NamedQuery(name = "VjReslang.findByIdLanguage", query = "SELECT v FROM VjReslang v WHERE v.idLanguage = :idLanguage"),
    @NamedQuery(name = "VjReslang.findByNbTerms", query = "SELECT v FROM VjReslang v WHERE v.nbTerms = :nbTerms")})
public class VjReslang implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "uuid")
    @Id
    private String uuid;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "id_language")
    private String idLanguage;
    @Basic(optional = false)
    @Column(name = "nb_terms")
    private long nbTerms;

    public VjReslang() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public long getNbTerms() {
        return nbTerms;
    }

    public void setNbTerms(long nbTerms) {
        this.nbTerms = nbTerms;
    }
    
}
