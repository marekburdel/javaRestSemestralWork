/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.rest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class Endpoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Stream
                .of(PersonResource.class, AddressResource.class, StreetResource.class)
                .collect(Collectors.toSet());
    }
    
}
