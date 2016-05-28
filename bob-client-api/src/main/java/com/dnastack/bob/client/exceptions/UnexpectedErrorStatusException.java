package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the Beacon Network client receives a recognized business error,
 * but didn't expect it from the requested operation.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class UnexpectedErrorStatusException extends InternalException {
    public UnexpectedErrorStatusException(String rawErrorStatus, String errorMessage) {
        super(String.format(
                "Received known, but unexpected error status from server. Status %s. Server message: %s.",
                rawErrorStatus, errorMessage));
    }
}
