/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.persistance;

import java.util.Objects;
import javax.ejb.Stateless;

@Stateless
public class PersonController extends AbstractCRUDController<PersonEntity> {

    public PersonController() {
        super(PersonEntity.class);
    }

    @Override
    protected Integer getEntityId(PersonEntity e) {
        return e.getId();
    }
    
    public PersonEntity addAddress(int id, AddressEntity a)  {
        PersonEntity e = retrieveEntity(id);
        e.getAddresses()
                .add(Objects.requireNonNull(a));
        updateOrCreate(e);
        return e;
    }
    
    public PersonEntity removeAddress(int id, AddressEntity a) {
        PersonEntity e = retrieveEntity(id);
        e.getAddresses()
                .remove(Objects.requireNonNull(a));
        updateOrCreate(e);
        return e;
    }
    
    
}
