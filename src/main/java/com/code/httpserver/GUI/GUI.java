package com.code.httpserver.GUI;

import com.code.httpserver.HttpServer;
import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;
import com.code.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI  implements ActionListener {
    private  JFrame mainFrame;
    private  JPanel mainPannel;
    private JButton startServer;
    private JButton maintainServer;
    private JButton stopServer;
    private JLabel output;
    private final static Logger LOGGER =  LoggerFactory.getLogger(HttpServer.class);
    private Configuration config;
    ServerListenerThread serverListenerThread = null;

    public GUI() throws IOException {
         mainFrame = new JFrame();
         mainPannel = new JPanel();
         startServer = new JButton("Start Server");
         //startServer.setBounds(100,100,100,200);
         mainPannel.add(startServer);

         maintainServer = new JButton("Maintain Server");

         stopServer = new JButton("Stop Server");

         output = new JLabel("SERVER STATE IS: -------");


        startServer.addActionListener(this);
        maintainServer.addActionListener(this);
        stopServer.addActionListener(this);

        mainPannel.setBorder(BorderFactory.createEmptyBorder(150,150,150,150));
        //mainPannel.setLayout(new GridLayout(0,1));
        mainPannel.add(maintainServer);
        mainPannel.add(stopServer);
        mainPannel.add(output);

        mainFrame.add(mainPannel, BorderLayout.CENTER);
        mainFrame.setSize(200,200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("VVS PROJECT UI");
        mainFrame.pack();
        mainFrame.setVisible(true);


    }

    void runServerBackEnd() throws IOException {
        config=null;
        serverListenerThread=null;
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        config = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("SERVER STARTING...");
        LOGGER.info("USING PORT: " + config.getPort());
        LOGGER.info("USING WEBROOT:" + config.getWebroot());
        serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
        serverListenerThread.run();
    }
    void stopServerBackEnd() throws IOException {
        config=null;
        serverListenerThread=null;
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        config= ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("SERVER STOP...");
        LOGGER.info("USING PORT: " + config.getPort());
        LOGGER.info("USING WEBROOT:" + config.getWebroot());

        serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
        serverListenerThread.interrupt();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startServer){
            try {
                runServerBackEnd();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            output.setText("SERVER STATE IS: STARTED - WORKING");
        }
        if(e.getSource()==maintainServer){
            output.setText("SERVER STATE IS: MAINTAINED");
        }
        if(e.getSource()==stopServer){
            try {
                stopServerBackEnd();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            output.setText("SERVER STATE IS: STOP");

        }
    }
}
