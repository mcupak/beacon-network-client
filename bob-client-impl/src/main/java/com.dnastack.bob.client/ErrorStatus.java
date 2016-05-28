package com.dnastack.bob.client;

/**
 * Business logic error statuses that are received in the http body from the server.
 * These statuses are resolved to the corresponding exceptions.
 * These statuses don't include any other error statuses, for example related to internal server errors or
 * mismatch between the Beacon Network client and the server workflow logic (for ex: different versions).
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
enum ErrorStatus {
    FORBIDDEN("FORBIDDEN"),
    NOT_FOUND("NOT_FOUND");

    String value;

    ErrorStatus(String value) {
        this.value = value;
    }

    public static ErrorStatus fromString(String value) {
        if (value != null) {
            for (ErrorStatus errorStatus : ErrorStatus.values()) {
                if (value.equalsIgnoreCase(errorStatus.value)) {
                    return errorStatus;
                }
            }
        }
        return null;
    }
}
