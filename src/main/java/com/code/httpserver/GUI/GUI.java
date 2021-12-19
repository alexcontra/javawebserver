package com.code.httpserver.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI  implements ActionListener {
    private  JFrame mainFrame;
    private  JPanel mainPannel;
    private JButton startServer;
    private JButton maintainServer;
    private JButton stopServer;
    private JLabel output;
    public GUI(){
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startServer){

        }
        if(e.getSource()==maintainServer){

        }
        if(e.getSource()==stopServer){

        }
    }
}
