package cz.cvut.fit.tjv.semestralwork.persistance;

import cz.cvut.fit.tjv.semestralwork.persistance.AddressEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-29T10:11:37")
@StaticMetamodel(PersonEntity.class)
public class PersonEntity_ { 

    public static volatile CollectionAttribute<PersonEntity, AddressEntity> addresses;
    public static volatile SingularAttribute<PersonEntity, String> surname;
    public static volatile SingularAttribute<PersonEntity, String> name;
    public static volatile SingularAttribute<PersonEntity, String> identificationNumber;
    public static volatile SingularAttribute<PersonEntity, Date> dateOfBirth;
    public static volatile SingularAttribute<PersonEntity, Integer> id;

}