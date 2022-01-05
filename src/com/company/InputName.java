package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputName implements ActionListener {
    JFrame frame = new JFrame();
    JButton applyButton = new JButton("Apply");
    JTextField textField1 = new JTextField("", 25);
    JTextField textField2 = new JTextField("", 25);
    JLabel label1 = new JLabel("Player name 1:");
    JLabel label2 = new JLabel("Player name 2:");
    Paddle left;
    Paddle right;

    InputName(){
        applyButton.setBounds(100,200,200,40);
        applyButton.setFocusable(false);
        applyButton.addActionListener(this);
        textField1.setBounds(100, 60, 200, 40);
        textField1.setFocusable(true);
        textField1.addActionListener(this);

        label1.setBounds(100, 40, 200, 20);
        label2.setBounds(100, 100, 200, 20);

        textField2.setBounds(100, 120, 200, 40);
        textField2.setFocusable(true);
        textField2.addActionListener(this);

        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(30,20,20,20));
        container.add(applyButton);
        container.add(textField1);
        container.add(textField2);
        container.add(label1);
        container.add(label2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == applyButton) {
            String user1 = textField1.getText();
            String user2 = textField2.getText();
            frame.dispose();
            Game game = new Game(user1, user2);
        }
    }
}
