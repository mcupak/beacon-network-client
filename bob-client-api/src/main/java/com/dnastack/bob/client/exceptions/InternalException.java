package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the Beacon Network client meets an error during communication to the server,
 * receives unknown response from the server, or due to any other internal errors.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class InternalException extends Exception {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
