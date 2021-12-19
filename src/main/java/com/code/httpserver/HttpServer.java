package com.code.httpserver;

import com.code.httpserver.GUI.GUI;
import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;
import com.code.httpserver.core.ServerListenerThread;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import org.slf4j.Logger;

/*
We'll consider this class as  a driver class
*/
public class HttpServer {
    private final static Logger LOGGER =  LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        GUI UI = new GUI();
        LOGGER.info("SERVER STARTING...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("USING PORT: "+config.getPort());
        LOGGER.info("USING WEBROOT:"+config.getWebroot());

        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
            serverListenerThread.start();
            serverListenerThread.run();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO HANDLE
        }
    }
}
