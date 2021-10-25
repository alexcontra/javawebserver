package com.code.httpserver.http;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion; // FROM THE REQUEST
    private HttpVersion bestCompatibleHttpVersion;

    public HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    void setMethod(String methodName) throws HttpParseException {
        for (HttpMethod method : HttpMethod.values()) {
            if (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParseException(HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);

    }

    void setRequestTarget(String requestT) {
        if (requestT != null || requestT.length() == 0) {
            try {
                throw new HttpParseException(HttpStatusCodes.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
            } catch (HttpParseException e) {
                e.printStackTrace();
            }
        }
        this.requestTarget = requestT;
    }

    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParseException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            throw new HttpParseException(
                    HttpStatusCodes.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED
            );
        }
    }
}