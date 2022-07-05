package org.example;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.caches.MyCacheStore1;
import org.example.controllers.GeoLocationController;
import org.example.dao.GeoLocationDAO;
import org.example.entity.GeoLocation;
import org.example.services.GeoLocationService;

import javax.ws.rs.client.Client;
import java.util.concurrent.TimeUnit;

public class trueApplication extends Application<trueConfiguration> {

    public static void main(final String[] args) throws Exception {
        new trueApplication().run(args);
    }

    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void initialize(final Bootstrap<trueConfiguration> bootstrap) {
        // TODO: application initialization
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final trueConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final MyCacheStore1<GeoLocation> cacheStore = new MyCacheStore1<>(60, TimeUnit.SECONDS);
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());

        final GeoLocationDAO geoLocationDAO = new GeoLocationDAO(hibernate.getSessionFactory());
        final GeoLocationService service = new GeoLocationService(geoLocationDAO, client, cacheStore);
        environment.jersey().register(new GeoLocationController(service));
    }

    private final HibernateBundle<trueConfiguration> hibernate = new HibernateBundle<trueConfiguration>(GeoLocation.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(trueConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}
