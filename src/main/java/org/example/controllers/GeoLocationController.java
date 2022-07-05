package org.example.controllers;

import io.dropwizard.hibernate.UnitOfWork;
import org.example.entity.GeoLocation;
import org.example.services.GeoLocationService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Mohammad Aiub Khan
 */
@Path("/geolocation")
@Produces(MediaType.APPLICATION_JSON)
public class GeoLocationController {

    private final GeoLocationService service;

    public GeoLocationController(GeoLocationService service) {
        this.service = service;
    }

    @GET
    @Path("/{ip}")
    @UnitOfWork
    public GeoLocation getGeoLocationByIP(@PathParam("ip") String ipAddress) {
        return service.getGeoLocationByName(ipAddress);
    }
}
