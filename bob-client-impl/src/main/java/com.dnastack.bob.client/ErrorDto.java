package com.dnastack.bob.client;

/**
 * This is DTO for error responses.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ErrorDto {
    private String message;
    private String status;

    public ErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
