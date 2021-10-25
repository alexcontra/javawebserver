package com.code.httpserver;

import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

        try {
            ServerSocket serverSocket  = new ServerSocket(config.getPort());
            //prompt the socket and accept any connections
            Socket socket =   serverSocket.accept();
           InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            //TODO PROCEED READING
            //writing
            String html = "<html><head><title>Simple JAVA HTTTP SERVER</title></head><body><h1>CONTENT OF HTTP SERVER</h1></body></html>";
            final String CRLF = "\n\r"; //13 ,10
            String response = "HTTP/1.1 200 OK"+CRLF + "CONTENT-LENGHT: "+html.getBytes().length+CRLF + CRLF+html+CRLF;//Status line : HTTP  VERSION-> RESPONSE -> CODE -> RESPONSE_MESSAGE
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
