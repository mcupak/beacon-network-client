package com.dnastack.bob.client.exceptions;

/**
 * This is a parent exception for any errors occurring in the Beacon Network client.<p>
 * These errors include unknown server responses, errors in the client itself as well as expected
 * business error responses that are meant to be processed by the client code using the Beacon Network client.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconNetworkClientException extends Exception {
    public BeaconNetworkClientException(String message) {
        super(message);
    }
}
