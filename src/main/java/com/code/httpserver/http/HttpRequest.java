package com.code.httpserver.http;

public class HttpRequest extends HttpMessage{
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    public HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) throws HttpPraseException {
        for(HttpMethod method : HttpMethod.values()){
            if(methodName.equals(method.name())){
                this.method=method;
                return;
            }
        }
        throw new HttpPraseException(HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);

        }
}
