package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mohammad Aiub Khan
 */
@Entity
@Table(name = "geolocation")
@Getter
@Setter
@NamedQueries({@NamedQuery(name = GeoLocation.GET_GEOLOCATION_BY_IP, query = GeoLocation.GET_DEPARTMENT_BY_ID_QUERY)})
public class GeoLocation {
    static final String GET_DEPARTMENT_BY_ID_QUERY = "FROM GeoLocation g where g.query = :query";
    public static final String GET_GEOLOCATION_BY_IP = "GET_GEOLOCATION_BY_IP";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotEmpty
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    private String query;
    @Column
    private String status;
    @Column
    private String country;
    @Column
    private String countryCode;
    @Column
    private String region;
    @Column
    private String regionName;
    @Column
    private String city;
    @Column(length = 5)
    private String zip;
    @Column
    private String lat;
    @Column
    private String lon;
    @Column
    private String timezone;
    @Column
    private String isp;
    @Column
    private String org;
    @Column
    private String as;
}
