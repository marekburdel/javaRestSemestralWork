/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.rest;


import cz.cvut.fit.tjv.semestralwork.persistance.PersonController;
import cz.cvut.fit.tjv.semestralwork.persistance.StreetController;
import cz.cvut.fit.tjv.semestralwork.persistance.StreetEntity;
import static cz.cvut.fit.tjv.semestralwork.rest.PersonResource.DTO_TO_ENTITY_CONVERTER;
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

/**
 *
 * @author Marek
 */
@Path("/street")
public class StreetResource extends AbstractCRUDResource<StreetEntity, StreetDTO> {
    
    static final Function<StreetEntity, StreetDTO> ENTITY_TO_DTO_CONVERTER
            = e -> e == null ? null : new StreetDTO(
                    e.getId(),
                    e.getName(),
                    e.getCity(),
                    e.getPostalCode(),
                    e.getCountry()
            );
    static final Function<StreetDTO, StreetEntity> DTO_TO_ENTITY_CONVERTER
            = d -> new StreetEntity(
                    d.getId(),
                    d.getName(),
                    d.getCity(),
                    d.getPostalCode(),
                    d.getCountry()
            );

    @EJB
    private StreetController controller;
    
    public StreetResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }
    
    @Override
    protected StreetController getController() {
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

    @Override
    protected boolean containsEntityId(StreetDTO dto) {
        return dto.getId() != null;
    }
}
