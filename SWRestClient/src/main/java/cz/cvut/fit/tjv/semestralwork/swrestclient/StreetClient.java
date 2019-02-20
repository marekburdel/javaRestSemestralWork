/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.swrestclient;

import cz.cvut.fit.tjv.semestralwork.swclientview.AddressDTO;
import cz.cvut.fit.tjv.semestralwork.swclientview.StreetDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Marek
 */
public class StreetClient {
    private static final String RESOURCE_PATH = "street";
    private static final String RESOURCE_ID_PATH = "{pid}";

    private static final StreetClient INSTANCE = new StreetClient();

    private StreetClient() {
    }
    
    public static StreetClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateJson(StreetDTO e) {
        StreetDTO res = RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).put(Entity.json(e), StreetDTO.class));
        e.setId(res.getId());
        e.setName(res.getName());
        e.setCity(res.getCity());
        e.setPostalCode(res.getPostalCode());
        e.setCountry(res.getCountry());
        e.setAddresses(res.getAddresses());
    }

    public void createOrUpdateXml(StreetDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public StreetDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_JSON).get(StreetDTO.class));
    }

    public StreetDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("pid", id)
                .request(MediaType.APPLICATION_XML).get(StreetDTO.class));
    }

    public StreetDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(StreetDTO[].class));
    }

    public StreetDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(StreetDTO[].class));
    }
    
    public List<AddressDTO> getStreetAddresses(StreetDTO street, List<AddressDTO> addresses) {
        List<AddressDTO> selectedStreetAddresses = new ArrayList<>();
                for (AddressDTO a : addresses){
                    if (Objects.equals(a.getStreet().getId(), street.getId()))
                        selectedStreetAddresses.add(a);
                }
        return selectedStreetAddresses;
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
