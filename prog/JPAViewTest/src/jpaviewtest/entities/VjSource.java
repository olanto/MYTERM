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
@Table(name = "vj_source")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VjSource.findAll", query = "SELECT v FROM VjSource v"),
    @NamedQuery(name = "VjSource.findByIdOwner", query = "SELECT v FROM VjSource v WHERE v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findByUuid", query = "SELECT v FROM VjSource v WHERE v.uuid = :uuid AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findAllBySource", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findPublicBySource", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idOwner = :idOwner AND v.status like 'p'"),
    @NamedQuery(name = "VjSource.findByIdTermSource", query = "SELECT v FROM VjSource v WHERE v.idTermSource = :idTermSource AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBySolang", query = "SELECT v FROM VjSource v WHERE v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBySourceSolang", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findByIdConcept", query = "SELECT v FROM VjSource v WHERE v.idConcept = :idConcept AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findByResourceName", query = "SELECT v FROM VjSource v WHERE v.resourceName LIKE :resourceName AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findPublicBysourceIdResource", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.idResource = :idResource AND v.solang = :solang AND v.status like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findALLBysourceIdResource", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.idResource = :idResource AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBysourceIdResource", query = "SELECT v FROM VjSource v WHERE v.source LIKE :source AND v.idResource = :idResource AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findByIdResource", query = "SELECT v FROM VjSource v WHERE v.idResource = :idResource AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBySubjectField", query = "SELECT v FROM VjSource v WHERE v.subjectField LIKE :subjectField AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBysourceSubjectField", query = "SELECT v FROM VjSource v WHERE  v.source LIKE :source AND v.subjectField LIKE :subjectField AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findPublicBysourceSubjectField", query = "SELECT v FROM VjSource v WHERE  v.source LIKE :source AND v.subjectField LIKE :subjectField AND v.solang = :solang AND v.status like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findALLBysourceSubjectField", query = "SELECT v FROM VjSource v WHERE  v.source LIKE :source AND v.subjectField LIKE :subjectField AND v.solang = :solang AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findPublicBySourceResourceSubjectField", query = "SELECT DISTINCT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.status like 'p' AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findALLBySourceResourceSubjectField", query = "SELECT DISTINCT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner"),
    @NamedQuery(name = "VjSource.findBySourceResourceSubjectField", query = "SELECT DISTINCT v FROM VjSource v WHERE v.source LIKE :source AND v.solang = :solang AND v.idResource = :idResource AND v.subjectField LIKE :subjectField AND v.idOwner = :idOwner")
})
public class VjSource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "uuid")
    @Id
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
    @Column(name = "id_concept")
    private long idConcept;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Column(name = "subject_field")
    private String subjectField;
    @Basic(optional = false)
    @Column(name = "status")
    private char status;
    @Basic(optional = false)
    @Column(name = "id_owner")
    private long idOwner;

    public VjSource() {
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

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public String getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(String subjectField) {
        this.subjectField = subjectField;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }
}
