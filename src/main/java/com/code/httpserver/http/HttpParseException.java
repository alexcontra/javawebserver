package com.code.httpserver.http;

public class HttpParseException extends Exception{
    private HttpStatusCodes errorStatus;

    public HttpParseException(HttpStatusCodes errorStatus) {
        super(errorStatus.MESSAGE);
        this.errorStatus = errorStatus;
    }

    public HttpStatusCodes getErrorStatus() {
        return errorStatus;
    }
}
