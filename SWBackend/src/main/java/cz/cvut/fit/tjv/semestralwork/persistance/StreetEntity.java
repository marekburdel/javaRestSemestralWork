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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "SW_STREET")
@XmlRootElement
public class StreetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SID")
    private Integer id;
    @Column(name = "SNAME")
    private String name;
    @Column(name = "SCITY")
    private String city;
    @Column(name = "SPC")
    private String postalCode;
    @Column(name = "SCOUNTRY")
    private String country;
    
    @OneToMany(mappedBy = "street")
    private Collection<AddressEntity> addresses = new ArrayList<>();

    public StreetEntity() {
    }
    
    public StreetEntity(Integer id, String name, String city, String postalCode, String country) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Collection<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressEntity> addresses) {
        this.addresses = addresses;
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
        if (!(object instanceof StreetEntity)) {
            return false;
        }
        StreetEntity other = (StreetEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fit.tjv.sw.persistence.Street[ id=" + id + " ]";
    }
    
}
