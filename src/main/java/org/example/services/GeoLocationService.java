package org.example.services;

import org.example.caches.MyCacheStore1;
import org.example.dao.GeoLocationDAO;
import org.example.entity.GeoLocation;
import org.example.exceptions.GeoLocationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mohammad Aiub Khan
 */
public class GeoLocationService {
    private static Logger LOGGER = LoggerFactory.getLogger(GeoLocationService.class);
    private GeoLocationDAO geoLocationDAO;

    private Client client;
    MyCacheStore1<GeoLocation> cacheStore;

    public GeoLocationService(GeoLocationDAO dao, Client client, MyCacheStore1<GeoLocation> cacheStore) {
        this.geoLocationDAO = dao;
        this.client = client;
        this.cacheStore = cacheStore;
    }

    public GeoLocation getGeoLocationByName(String ipAddress) {
        GeoLocation geoLocation;
        geoLocation = cacheStore.get(ipAddress);
        if (geoLocation == null) {
            LOGGER.info("not in cache", ipAddress);
            try {
                geoLocation = geoLocationDAO.findByName(ipAddress);
            } catch (NoResultException e) {
                LOGGER.info("No data Found On for " + ipAddress + " " + e.getLocalizedMessage());
            }
            if (geoLocation == null) {
                LOGGER.info("not in db", ipAddress);
                try {
                    WebTarget webTarget = client.target("http://ip-api.com/json/" + ipAddress);
                    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
                    Response response = invocationBuilder.get();
                    geoLocation = response.readEntity(GeoLocation.class);
                } catch (Exception e) {
                    throw new GeoLocationNotFoundException("No Data Found for this IP:" + ipAddress);
                }
                LOGGER.info("from api call " + ipAddress);
                geoLocationDAO.insert(geoLocation);
            }
            cacheStore.add(ipAddress, geoLocation);
        }
        return geoLocation;
    }
}
