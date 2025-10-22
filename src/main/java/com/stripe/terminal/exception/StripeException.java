package com.stripe.terminal.exception;

/**
 * Base exception for Stripe API errors.
 */
public class StripeException extends Exception {
    private final int statusCode;
    private final String requestId;
    private final String errorType;
    private final String errorCode;

    public StripeException(String message, int statusCode) {
        this(message, statusCode, null, null, null);
    }

    public StripeException(String message, int statusCode, String requestId, String errorType, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.requestId = requestId;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "StripeException{" +
                "message='" + getMessage() + '\'' +
                ", statusCode=" + statusCode +
                ", requestId='" + requestId + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
