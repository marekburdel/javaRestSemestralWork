/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.rest;

import cz.cvut.fit.tjv.semestralwork.persistance.AddressController;
import cz.cvut.fit.tjv.semestralwork.persistance.AddressEntity;
import cz.cvut.fit.tjv.semestralwork.swclientview.AddressDTO;
import cz.cvut.fit.tjv.semestralwork.swclientview.PersonDTO;
import cz.cvut.fit.tjv.semestralwork.swclientview.StreetDTO;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/address")
public class AddressResource extends AbstractCRUDResource<AddressEntity, AddressDTO> {
    
    static final Function<AddressEntity, AddressDTO> ENTITY_TO_DTO_CONVERTER
            = e -> e == null ? null : new AddressDTO(
                    e.getId(), 
                    e.getNumber(), 
                    StreetResource.ENTITY_TO_DTO_CONVERTER.apply(e.getStreet())
            );
    static final Function<AddressDTO, AddressEntity> DTO_TO_ENTITY_CONVERTER
            = d -> new AddressEntity(
                    d.getId(), 
                    d.getNumber(), 
                    StreetResource.DTO_TO_ENTITY_CONVERTER.apply(d.getStreet())   
            );

    @EJB
    private AddressController controller;
    
    public AddressResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }
    
    @Override
    protected AddressController getController() {
        return controller;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response retrieveById(@PathParam("id") Integer id) {
        return super.retrieveById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteById(@PathParam("id") Integer id) {
        return super.deleteById(id);
    }

    @POST
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addOrRemovePersons(PersonDTO s, @PathParam("id") int id, @QueryParam("remove") boolean remove) {
        try {
            if (remove) {
                return Response
                        .ok(entityToDtoConverter.apply(controller.removePerson(id, PersonResource.DTO_TO_ENTITY_CONVERTER.apply(s))))
                        .build();
            } else {
                return Response
                        .ok(entityToDtoConverter.apply(controller.addPerson(id, PersonResource.DTO_TO_ENTITY_CONVERTER.apply(s))))
                        .build();
            }
        } catch (EJBException exception) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Override
    protected boolean containsEntityId(AddressDTO dto) {
        return dto.getId() != null;
    }
}
