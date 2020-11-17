/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 * @author ianchong16
 */
@Entity
@Table(name = "PRIVATEMESSAGEMODEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privatemessagemodel.findAll", query = "SELECT p FROM Privatemessagemodel p")
    , @NamedQuery(name = "Privatemessagemodel.findById", query = "SELECT p FROM Privatemessagemodel p WHERE p.id = :id")
    , @NamedQuery(name = "Privatemessagemodel.findByName", query = "SELECT p FROM Privatemessagemodel p WHERE p.name = :name")
    , @NamedQuery(name = "Privatemessagemodel.findByPmname", query = "SELECT p FROM Privatemessagemodel p WHERE p.pmname = :pmname")
    , @NamedQuery(name = "Privatemessagemodel.findByPmid", query = "SELECT p FROM Privatemessagemodel p WHERE p.pmid = :pmid")
//custom query 1        
    , @NamedQuery(name = "Privatemessagemodel.findByNameAndPmid", query = "SELECT p FROM Privatemessagemodel p WHERE p.name = :name and p.pmid = :pmid")    
//custom query 2
    , @NamedQuery(name = "Privatemessagemodel.findByNameAndId", query = "SELECT p FROM Privatemessagemodel p WHERE p.name = :name and p.id = :id") 
        
    , @NamedQuery(name = "Privatemessagemodel.findByNameAdvanced", query = "SELECT p FROM Privatemessagemodel p WHERE LOWER(p.name) LIKE  CONCAT('%', LOWER(:name), '%')") 
})
public class Privatemessagemodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PMNAME")
    private String pmname;
    @Column(name = "PMID")
    private Integer pmid;
    @Lob
    @Column(name = "PM")
    private String pm;

    public Privatemessagemodel() {
    }

    public Privatemessagemodel(Integer id) {
        this.id = id;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPmName() {
        return pmname;
    }

    public void setPmName(String pmname) {
        this.pmname = pmname;
    }

    public Integer getPmID() {
        return pmid;
    }

    public void setPmID(Integer pmid) {
        this.pmid = pmid;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privatemessagemodel)) {
            return false;
        }
        Privatemessagemodel other = (Privatemessagemodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Privatemessagemodel[ id=" + id + " ]";
    }
    
}
