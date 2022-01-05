package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Главное меню в начале игры
public class MainMenu extends MouseAdapter {

    public boolean active; // true, если отображается главное меню

    // Кнопка "играть"
    private Rectangle playBtn; // Кнопка "играть"
    private String playTxt = "Play";
    private boolean pHighlight = false; // true, если указатель мыши наведен на кнопку "играть"

    // Quit button
    private Rectangle quitBtn; // Кнопка "выход"
    private String quitTxt = "Quit";
    private boolean qHighlight = false; // true, если указатель мыши наведен на кнопку выхода

    private Font font;

    // Конструктор
    public MainMenu(Game game) {

        active = true;
        game.start();

        // положение и размеры каждой кнопки
        int x, y, w, h;

        w = 300;
        h = 150;

        y = Game.HEIGHT / 2 - h / 2;

        x = Game.WIDTH / 4 - w / 2;
        playBtn = new Rectangle(x, y, w, h);

        x = Game.WIDTH * 3 / 4 - w / 2;
        quitBtn = new Rectangle(x, y, w, h);

        font = new Font("Roboto", Font.PLAIN, 100);
    }

    // Рисование кнопок (прямоугольники) и текста в главном меню
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(font);

        // Рисование кнопок
        g.setColor(Color.black);
        if (pHighlight)
            g.setColor(Color.white);
        g2d.fill(playBtn);

        g.setColor(Color.black);
        if (qHighlight)
            g.setColor(Color.white);
        g2d.fill(quitBtn);

        // Рисование границ кнопок
        g.setColor(Color.white);
        g2d.draw(playBtn);
        g2d.draw(quitBtn);

        // текст в кнопках

        int strWidth, strHeight;

        // Текст кнопки "играть"
        strWidth = g.getFontMetrics(font).stringWidth(playTxt);
        strHeight = g.getFontMetrics(font).getHeight();

        g.setColor(Color.green);
        g.drawString(playTxt, (int) (playBtn.getX() + playBtn.getWidth() / 2 - strWidth / 2),
                (int) (playBtn.getY() + playBtn.getHeight() / 2 + strHeight / 4));

        // Текст кнопки "выход"
        strWidth = g.getFontMetrics(font).stringWidth(quitTxt);
        strHeight = g.getFontMetrics(font).getHeight();

        g.setColor(Color.red);
        g.drawString(quitTxt, (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - strWidth / 2),
                (int) (quitBtn.getY() + quitBtn.getHeight() / 2 + strHeight / 4));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();

        if (playBtn.contains(p))
            active = false;
        else if (quitBtn.contains(p))
            System.exit(0);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        Point p = e.getPoint();

        // определение, зависает ли мышь внутри одной из кнопок
        pHighlight = playBtn.contains(p);
        qHighlight = quitBtn.contains(p);

    }

}
