package com.company;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    public static final int SIZE = 16;

    private int x, y; // положение
    private int xVel, yVel; // 1 или -1
    private int speed = 8; // скорость мяча 12312423234123

    //конструктор
    public Ball() {
        reset();
    }

     //установка начального положения и скорости
    private void reset() {
        // исходное положение
        x = Game.WIDTH / 2 - SIZE / 2;
        y = Game.HEIGHT / 2 - SIZE / 2;

        // Начальная скорость
        xVel = Game.sign(Math.random() * 2.0 - 1);
        yVel = Game.sign(Math.random() * 2.0 - 1);
    }

     //рисование мочи
     //g: Графический объект для рисования всего
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, SIZE, SIZE);
    }

     //обновить положение и столкновения
     //lp: левая
     //rp: правая
    public void update(Paddle lp, Paddle rp) {

        // обновление позиции
        x += xVel * speed;
        y += yVel * speed;

        // столкновения
        // с потолком и полом
        if (y + SIZE >= Game.HEIGHT || y <= 0)
            changeYDir();

        // со стенами
        if (x + SIZE >= Game.WIDTH) { // правая стенка
            lp.addPoint();
            reset();
        }
        if (x <= 0) { // левая стенка
            rp.addPoint();
            reset();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //изменить направление x, если мяч идет направо. Идти налево, иначе идти направо
    public void changeXDir() {
        xVel *= -1;
    }

    //изменить направление y, если мяч летит вверх, опуститься, в противном случае подниматься
    public void changeYDir() {
        yVel *= -1;
    }

}
