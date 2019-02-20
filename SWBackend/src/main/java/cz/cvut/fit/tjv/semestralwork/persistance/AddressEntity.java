/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.persistance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "SW_ADDRESS")
@XmlRootElement
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AID")
    private Integer id;
    
    @Column(name = "ANUMBER")
    private Integer number;
    
    @ManyToMany(targetEntity = PersonEntity.class ,mappedBy = "addresses")
    private Collection<PersonEntity> persons = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "SID", referencedColumnName = "SID")
    private StreetEntity street;

    public AddressEntity() {
    }

    public AddressEntity(Integer id, Integer number, StreetEntity street) {
        this.id = id;
        this.number = number;
        this.street = street;
    }
    

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

 
    public Collection<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(Collection<PersonEntity> persons) {
        this.persons = persons;
    }
    
    public StreetEntity getStreet() {
        return street;
    }

    public void setStreet(StreetEntity street) {
        this.street = street;
    }
    
    
    public Integer getId() {
        return id;
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
        if (!(object instanceof AddressEntity)) {
            return false;
        }
        AddressEntity other = (AddressEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fit.tjv.sw.persistence.Address[ id=" + id + " ]";
    }
    
}
