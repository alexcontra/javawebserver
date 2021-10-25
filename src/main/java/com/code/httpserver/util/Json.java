package com.code.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {
    private static ObjectMapper myObjectMapper=defaultObjectMapper();
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper obj  = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return obj;
    }
    public static JsonNode parse(String source) throws JsonProcessingException {
        return myObjectMapper.readTree(source);
    }
    public static <A> A fromJson(JsonNode node , Class<A> classz) throws JsonProcessingException {
        return  myObjectMapper.treeToValue(node,classz);
    }
    public static JsonNode toJson(Object obj){
        return myObjectMapper.valueToTree(obj);
    }
    public static String genString(JsonNode node) throws JsonProcessingException {
        return  generateJson(node,false);
    }
    public static String genStringToPretty(JsonNode node) throws JsonProcessingException {
        return  generateJson(node,true);
    }
    private static String generateJson(Object obj,boolean pretty) throws JsonProcessingException {
        ObjectWriter objWriter = myObjectMapper.writer();
        if(pretty){
            objWriter= objWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objWriter.writeValueAsString(obj);
    }
}
