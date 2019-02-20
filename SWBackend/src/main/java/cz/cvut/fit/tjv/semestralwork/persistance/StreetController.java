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
public class StreetController extends AbstractCRUDController<StreetEntity> {

    public StreetController() {
        super(StreetEntity.class);
    }

    @Override
    protected Integer getEntityId(StreetEntity e) {
        return e.getId();
    }
    
    public StreetEntity addAddress(int id, AddressEntity s)  {
        StreetEntity e = retrieveEntity(id);
        e.getAddresses()
                .add(Objects.requireNonNull(s));
        updateOrCreate(e);
        return e;
    }

    public StreetEntity removeAddress(int id, AddressEntity s) {
        StreetEntity e = retrieveEntity(id);
        e.getAddresses()
                .remove(Objects.requireNonNull(s));
        updateOrCreate(e);
        return e;
    }    
    
    
    
    
    
    
}
