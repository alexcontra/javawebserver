package com.code.httpserver.http;

public class HttpPraseException extends Exception{
    private HttpStatusCodes errorStatus;

    public HttpPraseException(HttpStatusCodes errorStatus) {
        super(errorStatus.MESSAGE);
        this.errorStatus = errorStatus;
    }

    public HttpStatusCodes getErrorStatus() {
        return errorStatus;
    }
}
