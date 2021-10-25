package com.code.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; //SP 32
    private static final int CR = 0x0D; //CR 13
    private static final int LF = 0x0A; // LF 10
    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        HttpRequest request = new HttpRequest();
        parserRequestLine(reader,request);
        parseHeaders(reader,request);
        parseBody(reader,request);
        return  request;
    }
    private void parseBody(InputStreamReader inputStream, HttpRequest request) {
    }

    private void parseHeaders(InputStreamReader inputStream, HttpRequest request) {
    }

    private void parserRequestLine(InputStreamReader inputStream, HttpRequest request) throws IOException {
        int _bytes;
        while ((_bytes=inputStream.read())>=0){
            if(_bytes==CR){
                _bytes = inputStream.read();
                if(_bytes==LF){
                    return;
                }
            }
        }
    }

}
