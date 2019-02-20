/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.swrestclient;

import cz.cvut.fit.tjv.semestralwork.swclientview.AddressDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Marek
 */
public class AddressClient {
    private static final String RESOURCE_PATH = "/address";
    private static final String RESOURCE_ID_PATH = "{pid}";

    private static final AddressClient INSTANCE = new AddressClient();

    private AddressClient() {
    }
    
    public static AddressClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateJson(AddressDTO e) {
        AddressDTO res = RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).put(Entity.json(e), AddressDTO.class));
        e.setId(res.getId());
        e.setNumber(res.getNumber());
        e.setStreet(res.getStreet());
        e.setPersons(res.getPersons());
    }

    public void createOrUpdateXml(AddressDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public AddressDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_JSON).get(AddressDTO.class));
    }

    public AddressDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_XML).get(AddressDTO.class));
    }

    public AddressDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(AddressDTO[].class));
    }

    public AddressDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(AddressDTO[].class));
    }
    

    public void delete(Integer id) {
        RestClient.processRequest(RESOURCE_PATH,
                t -> {
                    int status = t.path(RESOURCE_ID_PATH).resolveTemplate("pid", id).request().delete().getStatus();
                    if (status != 204) {
                        throw new SWServiceException(null);
                    } else {
                        return null;
                    }
                });
    }
}
