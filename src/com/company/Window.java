package com.company;

import javax.swing.*;
import java.awt.*;

// Окно
public class Window {

    //Создание окна
    //title - название игры
    //game  - игра

    public Window(String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game); // Игра наследуется от Canvas, объекта Component, поэтому ее можно поместить в JFrame.
        frame.pack();
        frame.setLocationRelativeTo(null); // центрируем окно
        frame.setVisible(true);
    }
}
