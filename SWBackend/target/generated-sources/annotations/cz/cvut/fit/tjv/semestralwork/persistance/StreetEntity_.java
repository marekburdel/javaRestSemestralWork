package cz.cvut.fit.tjv.semestralwork.persistance;

import cz.cvut.fit.tjv.semestralwork.persistance.AddressEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-29T10:11:37")
@StaticMetamodel(StreetEntity.class)
public class StreetEntity_ { 

    public static volatile SingularAttribute<StreetEntity, String> country;
    public static volatile CollectionAttribute<StreetEntity, AddressEntity> addresses;
    public static volatile SingularAttribute<StreetEntity, String> city;
    public static volatile SingularAttribute<StreetEntity, String> postalCode;
    public static volatile SingularAttribute<StreetEntity, String> name;
    public static volatile SingularAttribute<StreetEntity, Integer> id;

}