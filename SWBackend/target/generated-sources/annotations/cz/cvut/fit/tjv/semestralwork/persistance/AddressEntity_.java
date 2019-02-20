package cz.cvut.fit.tjv.semestralwork.persistance;

import cz.cvut.fit.tjv.semestralwork.persistance.PersonEntity;
import cz.cvut.fit.tjv.semestralwork.persistance.StreetEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-29T10:11:37")
@StaticMetamodel(AddressEntity.class)
public class AddressEntity_ { 

    public static volatile SingularAttribute<AddressEntity, Integer> number;
    public static volatile CollectionAttribute<AddressEntity, PersonEntity> persons;
    public static volatile SingularAttribute<AddressEntity, StreetEntity> street;
    public static volatile SingularAttribute<AddressEntity, Integer> id;

}