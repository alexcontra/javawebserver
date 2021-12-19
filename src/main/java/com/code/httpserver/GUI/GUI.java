package com.code.httpserver.GUI;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI(){
        JFrame mainFrame = new JFrame();
        JPanel mainPannel = new JPanel();
        JButton startServer = new JButton("Start Server");
        JButton maintainServer = new JButton("Maintain Server");
        JButton stopServer = new JButton("Stop Server");
        mainPannel.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
        mainPannel.setLayout(new GridLayout(0,1));
        mainFrame.add(mainPannel, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("VVS PROJECT UI");
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
