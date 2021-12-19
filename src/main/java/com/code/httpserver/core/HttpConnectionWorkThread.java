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


            LOGGER.info("* CONNECTION ACCEPTED: " + socket.getInetAddress());
            //writing
            String html = """ 
                    <html> 
                    <head> 
                        <title> 
                            VSS PROJECT
                        </title>
                    </head> 
                      
                    <body style = "text-align:center;">
                           
                        <h1 style = "color:blue;" > 
                            VSS PROJECT- THIS IS AN HTTP SERVER IMPLEMENTED IN JAVA
                        </h1> 
                          
                        <p id = "GFG_UP" style =
                            "font-size: 16px; font-weight: bold;">     
                        </p>
                          
                        <button onclick = "gfg_Run()"> 
                            Change Color1
                        </button>
                         <button onclick = "gfg_Run2()"> 
                            Change Color2
                        </button>
                        <p id = "GFG_DOWN" style =
                            "color:green; font-size: 20px; font-weight: bold;">
                        </p>
                          
                        <script>
                            var el_up = document.getElementById("GFG_UP");
                            var el_down = document.getElementById("GFG_DOWN");
                            var str = "Click on button to change the background color";
                          
                            el_up.innerHTML = str;
                          
                            function changeColor(color) {
                                document.body.style.background = color;
                            }
                              
                            function gfg_Run() {
                                changeColor('yellow');
                                el_down.innerHTML = "Background Color changed";
                            }         
                              function gfg_Run2() {
                                changeColor('red');
                                el_down.innerHTML = "Background Color changed";
                            }         
                        </script> 
                    </body> 
                </html> 
                    """;
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