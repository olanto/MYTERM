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
@Table(name = "vj_sourcetarget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjSourcetarget.findAll", query = "SELECT v FROM VjSourcetarget v"),
    @NamedQuery(name = "VjSourcetarget.findByIdOwner", query = "SELECT v FROM VjSourcetarget v WHERE v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByUuid", query = "SELECT v FROM VjSourcetarget v WHERE v.uuid = :uuid AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySource", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idOwner = :idOwner AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySource2", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.idOwner = :idOwner AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findPublicBySource", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idOwner = :idOwner AND v.statusSource like 'p' AND v.statusTarget like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceResourceSubjectField", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceResourceSubjectField2", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findPublicBySourceResourceSubjectField", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.statusSource like 'p' AND v.statusTarget like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceResource", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idResource = :idResource AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceResource2", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.idResource = :idResource AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findPublicBySourceResource", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.idResource = :idResource AND v.statusSource like 'p' AND v.statusTarget like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceSubjectField", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findAllBySourceSubjectField2", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findPublicBySourceSubjectField", query = "SELECT DISTINCT v FROM VjSourcetarget v WHERE v.source LIKE :source AND v.solang = :solang AND v.talang = :talang AND v.subjectField LIKE :subjectField AND v.statusSource like 'p' AND v.statusTarget like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByIdTermSource", query = "SELECT v FROM VjSourcetarget v WHERE v.idTermSource = :idTermSource AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findBySolang", query = "SELECT v FROM VjSourcetarget v WHERE v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByTarget", query = "SELECT v FROM VjSourcetarget v WHERE v.target = :target AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByIdTermTarget", query = "SELECT v FROM VjSourcetarget v WHERE v.idTermTarget = :idTermTarget AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByTalang", query = "SELECT v FROM VjSourcetarget v WHERE v.talang = :talang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByIdConcept", query = "SELECT v FROM VjSourcetarget v WHERE v.idConcept = :idConcept AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSourcetarget.findByResourceName", query = "SELECT v FROM VjSourcetarget v WHERE v.resourceName = :resourceName AND v.idOwner = :idOwner")})
public class VjSourcetarget implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @Column(name = "source")
    private String source;
    @Basic(optional = false)
    @Column(name = "id_term_source")
    private long idTermSource;
    @Basic(optional = false)
    @Column(name = "solang")
    private String solang;
    @Basic(optional = false)
    @Column(name = "target")
    private String target;
    @Basic(optional = false)
    @Column(name = "id_term_target")
    private long idTermTarget;
    @Basic(optional = false)
    @Column(name = "talang")
    private String talang;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Basic(optional = false)
    @Column(name = "subject_field")
    private String subjectField;
    @Basic(optional = false)
    @Column(name = "status_source")
    private char statusSource;
    @Basic(optional = false)
    @Column(name = "status_target")
    private char statusTarget;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;

    public VjSourcetarget() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getIdTermSource() {
        return idTermSource;
    }

    public void setIdTermSource(long idTermSource) {
        this.idTermSource = idTermSource;
    }

    public String getSolang() {
        return solang;
    }

    public void setSolang(String solang) {
        this.solang = solang;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getIdTermTarget() {
        return idTermTarget;
    }

    public void setIdTermTarget(long idTermTarget) {
        this.idTermTarget = idTermTarget;
    }

    public String getTalang() {
        return talang;
    }

    public void setTalang(String talang) {
        this.talang = talang;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(String subjectField) {
        this.subjectField = subjectField;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public char getStatusSource() {
        return statusSource;
    }

    public void setStatusSource(char statusSource) {
        this.statusSource = statusSource;
    }

    public char getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(char statusTarget) {
        this.statusTarget = statusTarget;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }
}
