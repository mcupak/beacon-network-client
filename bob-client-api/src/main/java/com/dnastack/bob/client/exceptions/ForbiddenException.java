package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the requested operation is forbidden by the server.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ForbiddenException extends ErrorStatusException {
    public ForbiddenException(String message) {
        super(message);
    }
}
