package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;

//класс, который управляет игрой, рисует и обновляет физику

public class Game extends Canvas implements Runnable {

    public final static int WIDTH = 1000;
    public final static int HEIGHT = WIDTH * 9 / 16; // Соотношение сторон 16:9

    public boolean running = false; // true, если игра запущена
    private Thread gameThread; // поток, в котором игра обновляется и рисуется (однопоточная игра)
    // мяч, отскакивает от стен и ракеток
    private Ball ball;
    // ракетки
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    // Объект главного меню
    private MainMenu menu;
    private final String name1;
    private final String name2;

    //конструктор
    public Game(String name1, String name2) {

        canvasSetup();
        this.name1 = name1;
        this.name2 = name2;
        new Window("Ping-Pong", this);

        initialise();

        this.addKeyListener(new KeyInput(leftPaddle, rightPaddle));
        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);
        this.setFocusable(true);
    }

    //инициализация всех игровых объектов
    private void initialise() {
        // инициализация мяча
        ball = new Ball();

        // инициализация ракеток
        leftPaddle = new Paddle(Color.green, true);
        rightPaddle = new Paddle(Color.red, false);

        // инициализация главного меню
        menu = new MainMenu(this);
    }

    //настройка окна по размерам
    private void canvasSetup() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

   //Игровой цикл
    @Override
    public void run() { // позволяет создавать потоки

        this.requestFocus(); // Делаем запрос, чтобы наш компонент был в сфокусированном состоянии

        // игровой таймер
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                update();
                delta--;
                draw();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
            if(leftPaddle.getPoint() == 11 || rightPaddle.getPoint() == 11)
            {
                // Запись в файл
                new WriteToFile(leftPaddle, rightPaddle, this.name1, this.name2);
                leftPaddle.setPoint();
                rightPaddle.setPoint();
            }
        }
        stop();
    }

    //начать игру
    public synchronized void start() { // доступ к методу блокируется для других потоков, если его уже использует один поток
        gameThread = new Thread(this);
        //поскольку "this" - это класс "Game", где мы сейчас находимся,
        // и он реализует Runnable интерфейс, мы можем передать его в конструктор потока.
        // Этот поток с вызовом метода "run", который унаследовал этот класс (выше).
        gameThread.start(); // запускаем поток
        running = true;
    }

    //остановить игру
    public void stop() { // Этот метод приостановит выполнение текущего потока пока другой не закончит свое выполнение
        try {
            gameThread.join();
            running = false;
        } catch (InterruptedException e) { // Если поток прерывается, бросается это исключение
            e.printStackTrace();
        }
    }

     //Прорисовка
    public void draw() {
        // инициализация инструментов рисования перед самим рисованием

        BufferStrategy buffer = this.getBufferStrategy(); //извлечь буфер, чтобы мы могли их использовать,
        // буфер похож на пустое окно, на котором можно рисовать

        if (buffer == null) { // если его нет, рисовать не сможем
            this.createBufferStrategy(3); // Создание тройного буфера
            //тройная буферизация в основном означает, что у нас есть 3 разных холста,
            // которые используются для повышения производительности, но недостатки заключаются в том,
            // что больше буферов, больше памяти требуется, поэтому, если получим ошибку памяти
            // или что-то в этом роде, надо сделать 2 вместо 3.

            return;
        }

        Graphics g = buffer.getDrawGraphics(); // извлечение инструментов рисования из буферов
        //Графика - это класс, используемый для рисования прямоугольников, овалов и
        // всевозможных форм и изображений, поэтому это инструмент, используемый для рисования в буфере.

        // фон
        drawBackground(g);

        // содержимое главного меню
        if (menu.active)
            menu.draw(g);

        // мяч
        ball.draw(g);

        // ракетки (с их помощью будет начислен счет)
        leftPaddle.draw(g);
        rightPaddle.draw(g);

        g.dispose(); // Удаляет этот графический контекст и освобождает все системные ресурсы, которые он использует.
        buffer.show(); // показывает нам 3 прямоугольника, которые нарисовали
    }

    //фон
    private void drawBackground(Graphics g) {
        // черный фон
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.green);
        g.setFont(new Font("Roboto", Font.PLAIN, 48));
        g.drawString(this.name1, 180, 45);
        g.setColor(Color.red);
        g.drawString(this.name2, 650, 45);
        // Пунктирная линия посередине
        g.setColor(Color.white);
        Graphics2D g2d = (Graphics2D) g; // более сложный класс Graphics, используемый для рисования объектов
        // (например, передать объект в параметре, а не размеры или координаты)
        // Пунктирная линия:
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
        g2d.setStroke(dashed);
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }

    //обновление настроек и перемещение всех объектов
    public void update() {

        if (!menu.active) {
            // обновить мяч (движения)
            ball.update(leftPaddle, rightPaddle);

            // обновить ракетки (движения)
            leftPaddle.update(ball);
            rightPaddle.update(ball);
        }
    }
    
    /*
     используется для сохранения значения между минимальным и максимальным

     value - целое число имеющегося у нас значения
     min   - минимальное целое число
     max   - максимальное целое число
     значение, если значение находится между минимумом и максимумом
     минимум возвращается, если значение меньше минимума
     максимум возвращается, если значение больше максимального
     */
    public static int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    //возвращаем 1 или -1
    public static int sign(double d) {
        if (d <= 0)
            return -1;

        return 1;
    }

    //запуск приложения
    public static void main(String[] args) {
        new InputName();
    }

}