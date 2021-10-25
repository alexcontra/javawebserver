package com.code.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//
public class HttpConnectionWorkThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkThread.class);
    InputStream inputStream = null;
    OutputStream outputStream = null;
    private Socket socket;

    public HttpConnectionWorkThread(Socket socket) {
        this.socket = socket;
    }

    //THIS CLASS IS TO AVOID THE THREAD CREATING PROCESS DELAY , WHICH CALL IN A NEW PAGE IT WILL CREATE ANOTHER THREAD WORK
    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //TODO PROCEED READING
            LOGGER.info("* CONNECTION ACCEPTED: " + socket.getInetAddress());
            //writing
            String html = "<html><head><title>VSS PROJECT</title></head><body><h1>CONTENT OF HTTP SERVER</h1></body></html>";
            //the end carriage return and line feed , we need this to check the message in our output stream
            final String CRLF = "\n\r"; //13 ,10
            String response = "HTTP/1.1 200 OK" + CRLF + "CONTENT-LENGTH: " + html.getBytes().length + CRLF + CRLF + html + CRLF;//Status line : HTTP  VERSION-> RESPONSE -> CODE -> RESPONSE_MESSAGE
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            LOGGER.info("CONNECTION FINISHED");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("THERE IS A PROBLEM WITH THE COMMUNICATION",e);
            //TODO HANDLE
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }

            }
        }
    }
}