/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.rest;


import cz.cvut.fit.tjv.semestralwork.persistance.AddressEntity;
import cz.cvut.fit.tjv.semestralwork.persistance.PersonController;
import cz.cvut.fit.tjv.semestralwork.persistance.PersonEntity;
import static cz.cvut.fit.tjv.semestralwork.rest.AddressResource.DTO_TO_ENTITY_CONVERTER;
import cz.cvut.fit.tjv.semestralwork.swclientview.AddressDTO;
import cz.cvut.fit.tjv.semestralwork.swclientview.PersonDTO;
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

@Path("/person")
public class PersonResource extends AbstractCRUDResource<PersonEntity, PersonDTO> {

    static final  Function<PersonDTO, PersonEntity> DTO_TO_ENTITY_CONVERTER
            = d -> new PersonEntity(
                    d.getId(), 
                    d.getName(), 
                    d.getSurname(), 
                    d.getDateOfBirth(), 
                    d.getIdentificationNumber(), 
                    d.getAddresses().stream().map(AddressResource.DTO_TO_ENTITY_CONVERTER).collect(Collectors.toList())
                );
     static final Function<PersonEntity, PersonDTO> ENTITY_TO_DTO_CONVERTER
            = e -> e == null ? null : new PersonDTO(
                    e.getId(), 
                    e.getName(), 
                    e.getSurname(), 
                    e.getDateOfBirth(), 
                    e.getIdentificationNumber(), 
                    e.getAddresses().stream().map(AddressResource.ENTITY_TO_DTO_CONVERTER).collect(Collectors.toList())
                 );
    
    @EJB
    private PersonController controller;

    public PersonResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }

    @Override
    protected PersonController getController() {
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
    public Response addOrRemoveAddress(AddressDTO s, @PathParam("id") int id, @QueryParam("remove") boolean remove) {
        try {
            if (remove) {
                return Response
                        .ok(entityToDtoConverter.apply(controller.removeAddress(id, AddressResource.DTO_TO_ENTITY_CONVERTER.apply(s))))
                        .build();
            } else {
                return Response
                        .ok(entityToDtoConverter.apply(controller.addAddress(id, AddressResource.DTO_TO_ENTITY_CONVERTER.apply(s))))
                        .build();
            }
        } catch (EJBException exception) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Override
    protected boolean containsEntityId(PersonDTO dto) {
        return dto.getId() != null;
    }
}
