package com.accelaero.customer.Response;

public enum ResponseStatus {
    UNKNOWN_ERROR(-1),
    SUCCESS(0),
    ERROR(1),
    INVALID_CUSTOMER(2),
    BAD_REQUEST(3),
    ERROR_FROM_CONSUMER(4),
    EMPTY_USER_NAME(5),
    INVALID_LENGTH_FOR_USER_NAME(6),
    DUPLICATE_USER_NAME(7),
    EMPTY_PASSwORD(8),
    INVALID_LENGTH_FOR_PASSWORD(9),
    PASSWORDS_DOES_NOT_MATCH(10),
    AUTHENTICATION_FAILED(11);

    private int status;

    ResponseStatus(int i) {
        this.status = i;
    }

    public int getStatus() {
        return status;
    }
}
