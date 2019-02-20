/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.swrestclient;

import cz.cvut.fit.tjv.semestralwork.swclientview.AddressDTO;
import cz.cvut.fit.tjv.semestralwork.swclientview.PersonDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PersonClient {

    private static final String RESOURCE_PATH = "/person";
    private static final String RESOURCE_ID_PATH = "{pid}";

    private static final PersonClient INSTANCE = new PersonClient();

    private PersonClient() {
    }
    
    public static PersonClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateJson(PersonDTO e) {
        PersonDTO res = RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).put(Entity.json(e), PersonDTO.class));
        e.setId(res.getId());
        e.setName(res.getName());
        e.setSurname(res.getSurname());
        e.setDateOfBirth(res.getDateOfBirth());
        e.setIdentificationNumber(res.getIdentificationNumber());
        e.setAddresses(res.getAddresses());
    }

    public void createOrUpdateXml(PersonDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public PersonDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_JSON).get(PersonDTO.class));
    }

    public PersonDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_XML).get(PersonDTO.class));
    }

    public PersonDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(PersonDTO[].class));
    }

    public PersonDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(PersonDTO[].class));
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

    private void addOrRemoveAddressJson(int pid, int addressId, boolean remove) {
        RestClient.processRequest(RESOURCE_PATH, t -> {
            int status = t.path(RESOURCE_ID_PATH).resolveTemplate("pid", pid).queryParam("remove", remove).request()
                    .post(Entity.json(AddressClient.getInstance().retrieveJson(addressId))).getStatus();
            if (status != 200)
                throw new SWServiceException(null);
            else
                return null;
        });

    }

    public void addAddressJson(int pid, int addressId) {
        addOrRemoveAddressJson(pid, addressId, false);
    }

    public void removeAddressJson(int pid, int addressId) {
        addOrRemoveAddressJson(pid, addressId, true);
    }
      
    
      
}
