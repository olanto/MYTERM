/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.myterm.coredb.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "domains")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domains.findAll", query = "SELECT d FROM Domains d order by d.domainDefaultName"),
    @NamedQuery(name = "Domains.findByIdDomain", query = "SELECT d FROM Domains d WHERE d.idDomain = :idDomain"),
    @NamedQuery(name = "Domains.findByDomainDefaultName", query = "SELECT d FROM Domains d WHERE d.domainDefaultName = :domainDefaultName")})
public class Domains implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_domain")
    private Long idDomain;
    @Basic(optional = false)
    @Column(name = "domain_default_name")
    private String domainDefaultName;

    public Domains() {
    }

    public Domains(Long idDomain) {
        this.idDomain = idDomain;
    }

    public Domains(Long idDomain, String domainDefaultName) {
        this.idDomain = idDomain;
        this.domainDefaultName = domainDefaultName;
    }

    public Long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(Long idDomain) {
        this.idDomain = idDomain;
    }

    public String getDomainDefaultName() {
        return domainDefaultName;
    }

    public void setDomainDefaultName(String domainDefaultName) {
        this.domainDefaultName = domainDefaultName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDomain != null ? idDomain.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domains)) {
            return false;
        }
        Domains other = (Domains) object;
        if ((this.idDomain == null && other.idDomain != null) || (this.idDomain != null && !this.idDomain.equals(other.idDomain))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.olanto.myterm.coredb.entityclasses.Domains[ idDomain=" + idDomain + " ]";
    }
    
}
