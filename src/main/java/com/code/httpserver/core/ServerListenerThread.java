package com.code.httpserver.core;

import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;


public class ServerListenerThread extends Thread{
    private final static Logger LOGGER =  LoggerFactory.getLogger(ServerListenerThread.class);
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket  = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        //TO ACCEPT A CONNECTION OR MULTIPLE CONNECTIONS
        try {
            //to accept multiple connections by different browsers or multiple browsers
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                //prompt the socket and accept any connections
                Socket socket =   serverSocket.accept();
                HttpConnectionWorkThread workThread = new HttpConnectionWorkThread(socket);
                workThread.run();
            }
        } catch (IOException e) {
            LOGGER.info("PROBLEM WITH SOCKET",e);
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try{
                     serverSocket.close();
                }catch (IOException e){}
            }
        }
        super.run();
    }

}
