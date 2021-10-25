package com.code.httpserver.config;

import com.code.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    //we work with this configuration as singleton
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;
    private ConfigurationManager() {
    }
    public static ConfigurationManager getInstance(){
        if(myConfigurationManager==null){
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }
    //This will be used to load configuration file by the path provider
    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer buffer = new StringBuffer();
        int i;
      try{
          while(( i=fileReader.read())!=-1){
              buffer.append((char)i);
          }
      }catch(IOException e){
            throw new HttpConfigurationException(e);
      }
        JsonNode config = null;
        try {
            config = Json.parse(buffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("ERROR AT PARSING CONFIG");
        }
        try {
            myCurrentConfiguration = Json.fromJson(config,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("ERROR PARSING CONFIG INTERNAL");
        }
    }
    //The method returns the current configuration
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration==null){
            throw new HttpConfigurationException("NO CURRENT CONFIGURATION WAS SET");
        }
        return myCurrentConfiguration;
    }
}
