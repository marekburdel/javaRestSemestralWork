/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.persistance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "SW_PERSON")
@XmlRootElement
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PID")
    private Integer id;
    
    @Column(name = "PNAME")
    private String name;
    
    @Column(name = "PSURNAME")
    private String surname;
    
    @Column(name = "PDOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    @Column(name = "PIN")
    private String identificationNumber;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "SW_PERSON_ADDRESS",
            joinColumns = {@JoinColumn(name = "PID", referencedColumnName = "PID")},
            inverseJoinColumns = {@JoinColumn(name = "AID", referencedColumnName = "AID")}
    )
    @XmlTransient
    private Collection<AddressEntity> addresses = new ArrayList<>();

    public PersonEntity() {
    }

    public PersonEntity(Integer id, String name, String surname, Date dateOfBirth, String identificationNumber, Collection<AddressEntity> addresses) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.identificationNumber = identificationNumber;
        this.addresses = addresses;
    }
    
   
   
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    
    public Collection<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof PersonEntity)) {
            return false;
        }
        PersonEntity other = (PersonEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fit.tjv.sw.persistence.Person[ id=" + id + " ]";
    }
    
}
