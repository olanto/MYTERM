/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "VjSource.findByUuid", query = "SELECT v FROM VjSource v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VjSource.findByStatusAndOwner", query = "SELECT v FROM VjSource v WHERE v.solang = :solang AND v.status like :status AND v.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "VjSource.findBySourceStatus", query = "SELECT v FROM VjSource v WHERE v.source = :source  AND v.status like :status AND v.solang = :solang AND v.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "VjSource.findBySourceResourceStatus", query = "SELECT v FROM VjSource v WHERE v.source = :source AND v.idResource = :idResource AND v.status like :status AND v.solang = :solang  AND v.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "VjSource.findBySourceResourceStatusSubjectField", query = "SELECT v FROM VjSource v WHERE v.source = :source AND v.idResource = :idResource AND v.status like :status AND v.subjectField = :subjectField AND v.solang = :solang  AND v.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "VjSource.findPublicBySourceResource", query = "SELECT v FROM VjSource v WHERE v.source = :source AND v.idResource = :idResource AND v.status like 'p' AND v.solang = :solang"),
    @NamedQuery(name = "VjSource.findPublicBySourceResourceSubjectField", query = "SELECT v FROM VjSource v WHERE v.source = :source AND v.idResource = :idResource AND v.status like 'p' AND v.subjectField = :subjectField AND v.solang = :solang"),
    @NamedQuery(name = "VjSource.findBySource", query = "SELECT v FROM VjSource v WHERE v.source = :source"),
    @NamedQuery(name = "VjSource.findByIdTermSource", query = "SELECT v FROM VjSource v WHERE v.idTermSource = :idTermSource"),
    @NamedQuery(name = "VjSource.findBySolang", query = "SELECT v FROM VjSource v WHERE v.solang = :solang"),
    @NamedQuery(name = "VjSource.findByStatus", query = "SELECT v FROM VjSource v WHERE v.status = :status"),
    @NamedQuery(name = "VjSource.findByIdConcept", query = "SELECT v FROM VjSource v WHERE v.idConcept = :idConcept"),
    @NamedQuery(name = "VjSource.findByLastmodifiedBy", query = "SELECT v FROM VjSource v WHERE v.lastmodifiedBy = :lastmodifiedBy"),
    @NamedQuery(name = "VjSource.findByResourceName", query = "SELECT v FROM VjSource v WHERE v.resourceName = :resourceName"),
    @NamedQuery(name = "VjSource.findByIdResource", query = "SELECT v FROM VjSource v WHERE v.idResource = :idResource"),
    @NamedQuery(name = "VjSource.findBySubjectField", query = "SELECT v FROM VjSource v WHERE v.subjectField = :subjectField")})
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
    @Column(name = "status")
    private char status;
    @Basic(optional = false)
    @Column(name = "id_concept")
    private long idConcept;
    @Column(name = "lastmodified_by")
    private BigInteger lastmodifiedBy;
    @Basic(optional = false)
    @Column(name = "resource_name")
    private String resourceName;
    @Basic(optional = false)
    @Column(name = "id_resource")
    private long idResource;
    @Column(name = "subject_field")
    private String subjectField;

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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(long idConcept) {
        this.idConcept = idConcept;
    }

    public BigInteger getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(BigInteger lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
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
}
