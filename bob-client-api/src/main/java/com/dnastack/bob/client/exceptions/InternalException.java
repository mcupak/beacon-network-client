package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the Beacon Network client fails to perform a requested operation due to, for example,
 * errors during communication to the server, unknown response from the server or any other internal errors.<p>
 * For business errors (operation specific), children of {@link ErrorStatusException} are used.
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
