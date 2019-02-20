/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.persistance;

import java.util.Objects;
import javax.ejb.Stateless;

/**
 *
 * @author Marek
 */
@Stateless
public class AddressController extends AbstractCRUDController<AddressEntity> {

    public AddressController() {
        super(AddressEntity.class);
    }

    @Override
    protected Integer getEntityId(AddressEntity e) {
        return e.getId();
    }
    
    public AddressEntity addPerson(int id, PersonEntity a)  {
        AddressEntity e = retrieveEntity(id);
        e.getPersons()
                .add(Objects.requireNonNull(a));
        updateOrCreate(e);
        return e;
    }
    
    public AddressEntity removePerson(int id, PersonEntity a) {
        AddressEntity e = retrieveEntity(id);
        e.getPersons()
                .remove(Objects.requireNonNull(a));
        updateOrCreate(e);
        return e;
    }
    
}
