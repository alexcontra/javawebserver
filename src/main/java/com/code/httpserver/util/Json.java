package com.code.httpserver.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static ObjectMapper myObjectMapper=defaultObjectMapper();
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper obj  = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return obj;
    }
}
