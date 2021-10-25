package com.code.httpserver;

import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;

/*
We'll consider this class as  a driver class
*/
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("SERVER STARTING...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("USING PORT: "+config.getPort());
        System.out.print("USING WEBROOT:"+config.getWebroot());
    }
}
