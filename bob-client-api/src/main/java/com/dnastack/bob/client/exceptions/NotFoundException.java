package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the requested object is not found.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class NotFoundException extends ErrorStatusException {
    public NotFoundException(String message) {
        super(message);
    }
}
