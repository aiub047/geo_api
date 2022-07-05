package org.example.exceptions;

/**
 * @author Mohammad Aiub Khan
 */
public class GeoLocationNotFoundException extends RuntimeException {
    public GeoLocationNotFoundException(String msg) {
        super(msg);
    }
}
