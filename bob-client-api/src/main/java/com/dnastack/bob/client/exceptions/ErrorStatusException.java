package com.dnastack.bob.client.exceptions;

/**
 * This is a parent exception for any known but not necessarily expected
 * business error statuses received from server.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ErrorStatusException extends Exception {
    public ErrorStatusException(String message) {
        super(message);
    }
}
