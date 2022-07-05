package org.example.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.entity.GeoLocation;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * @author Mohammad Aiub Khan
 */

public class GeoLocationDAO extends AbstractDAO<GeoLocation> {
    public GeoLocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public GeoLocation findByID() {
        return findByID();
    }

    public GeoLocation insert(GeoLocation geoLocation) {
        return persist(geoLocation);
    }

    public GeoLocation findByName(String ipAddress) {
        Query query = currentSession().getNamedQuery(GeoLocation.GET_GEOLOCATION_BY_IP).setParameter("query", ipAddress);
        return (GeoLocation) query.getSingleResult();
    }

}
