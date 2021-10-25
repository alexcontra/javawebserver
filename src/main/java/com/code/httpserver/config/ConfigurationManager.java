package com.code.httpserver.config;

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
    public void loadConfigurationFile(String filePath){

    }
    //The method returns the current configuration
    public void getCurrentConfiguration(){

    }
}
