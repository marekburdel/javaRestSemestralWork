/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.semestralwork.swrestclient;

import java.util.function.Function;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class RestClient {
    public static final String BACKEND_REST_URL = "http://localhost:8080/SWBackend/rest";
    private static final ClientBuilder BUILDER = ClientBuilder.newBuilder();
    
    private static Client getClientInstance() {
        return BUILDER.build();
    }
    
    private static WebTarget getTarget(Client c, String path) {
        return c.target(BACKEND_REST_URL).path(path);
    }
    
    public static <V> V processRequest(String resourcePath, Function<WebTarget, V> request) {
        final Client c = RestClient.getClientInstance();
        try {
            return request.apply(getTarget(c, resourcePath));
        } catch (WebApplicationException e) {
            throw e;
        } finally {
            c.close();
        }
    }   
}
