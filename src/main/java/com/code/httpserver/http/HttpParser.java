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
    public HttpRequest parseHttpRequest(InputStream inputStream)  throws HttpPraseException{
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest request = new HttpRequest();
        try {
            parserRequestLine(reader,request);
        } catch (IOException  e) {
            e.printStackTrace();
        }
        parseHeaders(reader,request);
        parseBody(reader,request);
        return  request;
    }
    private void parseBody(InputStreamReader inputStream, HttpRequest request) {
    }

    private void parseHeaders(InputStreamReader inputStream, HttpRequest request) {
    }

    private void parserRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpPraseException {
        StringBuilder processingData = new StringBuilder();
        boolean methodParser= false;
        boolean requestTargetParsed=false;
        int _bytes;
        while ((_bytes=reader.read())>=0){
            if(_bytes == CR){
                _bytes = reader.read();
                if(_bytes==LF){
                    LOGGER.info("REQUEST LINE VERSION TO PROCESS : { } "+processingData.toString());
                    if(!methodParser || !requestTargetParsed){
                        throw new HttpPraseException(HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }
            if(_bytes==SP){
                if(!methodParser){

                    LOGGER.info("REQUEST LINE TO PROCESS : { } "+processingData.toString());
                    //clearing the buffer
                    request.setMethod(processingData.toString());
                    methodParser=true;
                }else if(!requestTargetParsed)
                {
                    LOGGER.info("REQUEST TARGET TO PROCESS : { } "+processingData.toString());
                    requestTargetParsed=true;
                }
                else {
                    throw new HttpPraseException(HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingData.delete(0,processingData.length());
            }else
            {
                 processingData.append((char)_bytes);
                 if(!methodParser){
                     if(processingData.length()>HttpMethod.MAX_LENGTH){
                         throw new HttpPraseException(HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
                     }
                 }
            }
        }
    }

}
