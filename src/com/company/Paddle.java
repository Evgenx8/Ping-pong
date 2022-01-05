package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Ракетки
public class Paddle {

    private int x, y; // позиция
    private int vel = 0; // скорость и направление ракетки
    private int speed = 10; // скорость движения ракетки
    private int width = 22, height = 85; // размер ракеткок
    private int score = 0; // очки игрока
    private Color color; // цвет ракетки
    private boolean left; // правда, если это левая ракетка

    //создание начальных свойств ракетки
    public Paddle(Color c, boolean left) {
        // начальные свойства
        color = c;
        this.left = left;

        if (left) // разные значения x для правой или левой ракетки
            x = 0;
        else
            x = Game.WIDTH - width;

        y = Game.HEIGHT / 2 - height / 2;

    }

    //добавить очко игроку
    public void addPoint() {
        score++;
    }
    public int getPoint()
    {
        return score;
    }
    public void setPoint()
    {
        score = 0;
    }

    // Рисование ракетки и счёта
    public void draw(Graphics g) {

        // рисование ракетки
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // рисование счёта
        int sx; // x позиция строки
        int padding = 25; // пробел между пунктирной линией и счетом
        String scoreText = Integer.toString(score);
        Font font = new Font("Roboto", Font.PLAIN, 50);

        if (left) {
            int strWidth = g.getFontMetrics(font).stringWidth(scoreText); // ширина строки, чтобы правильно ее отцентрировать
            sx = Game.WIDTH / 2 - padding - strWidth;
        } else {
            sx = Game.WIDTH / 2 + padding;
        }

        g.setFont(font);
        g.drawString(scoreText, sx, 50);
    }

    //обновить положение и столкновения
    public void update(Ball b) {

        // Обновление позиции
        y = Game.ensureRange(y + vel, 0, Game.HEIGHT - height);

        // столкновения
        int ballX = b.getX();
        int ballY = b.getY();

        if (left) {

            if (ballX <= width + x && ballY + Ball.SIZE >= y && ballY <= y + height)
                b.changeXDir();

        } else {

            if (ballX + Ball.SIZE >= x && ballY + Ball.SIZE >= y && ballY <= y + height)
                b.changeXDir();
        }
    }

    /*
    переключение направления

    direction - -1 верх и 1 вниз
    */
    public void switchDirections(int direction) {
        vel = speed * direction;
    }

    //перестать двигать ракеткой
    public void stop() {
        vel = 0;
    }
}
