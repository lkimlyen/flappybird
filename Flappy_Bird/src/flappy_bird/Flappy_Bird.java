package flappy_bird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Flappy_Bird implements ActionListener, MouseListener, KeyListener {

    public static Flappy_Bird flappybird;
    public final int WIDTH = 400, HEIGHT = 400;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public Random rand;
    public int ticks, yMotion, score;
    public boolean gameOver, started;

    public Flappy_Bird() {
        JFrame jframe = new JFrame();

        Timer timer = new Timer(20, this);
        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setTitle("Flappy Bee");
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);//cho phép jframe được thay đổi kích thuớc hay ko
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 10, 10);
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }

    public void addColumn(boolean start) {
        int space = 150;
        int width = 50;
        int heigth = 25 + rand.nextInt(150);
        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 150, HEIGHT - heigth - 60, width, heigth));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 150, 0, width, HEIGHT - heigth - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 300, HEIGHT - heigth - 60, width, heigth));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - heigth - space));

        }
    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 5;
        ticks++;
        if(started){
        for (int i = 0; i < columns.size(); i++) {
            Rectangle column = columns.get(i);
            column.x -= speed;
        }
        if (ticks % 2 == 0 && yMotion < 15) {
            yMotion += 2;
        }
        for (int i = 0; i < columns.size(); i++) {
            Rectangle column = columns.get(i);
            if (column.x + column.width < 0) {
                columns.remove(column);
                if (column.y == 0) {
                    addColumn(false);
                }
            }
        }

        bird.y += yMotion;
        for(Rectangle column : columns) {
            if(column.y == 0 && bird.x + bird.width / 2 > column.x + column.width/2 - 5 && bird.x + bird.width / 2 < column.x + column.width / 2 +5 )
            {
                score++;
            }
            if (column.intersects(bird)) {
                gameOver = true;
                if(bird.x <= column.x)
                {
                bird.x = column.x - bird.width;
                }
                else{
                if(column.y != 0)
                {
                    bird.y = column.y - bird.height;
                }
                else if(bird.y < column.height)
                {
                    bird.y = column.height;
                }
                }
            }
        }
        if (bird.y > HEIGHT - 60 || bird.y < 0) {
            gameOver = true;
             }
        if(bird.y + yMotion >= HEIGHT - 60)
        {
            bird.y = HEIGHT - 60 - bird.height;
        }
        }
        renderer.repaint();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.ORANGE);
        g.fillRect(0, HEIGHT - 60, WIDTH, 60);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 60, WIDTH, 10);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column : columns) {
            paintColumn(g, column);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,50));
        if(!started)
        {
            g.drawString("Click to start", 35 , HEIGHT / 2 - 25);
        }
        if(gameOver)
        {
            g.drawString("GAME OVER", 50 , HEIGHT / 2 - 25);
        }
        if(!gameOver && started)
        {
            g.drawString(String.valueOf(score),HEIGHT / 2 - 25,50);
        }
    }
public void jump(){
    if(gameOver)
    {
           bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 10, 10);
       columns.clear();
       yMotion = 0;
       score = 0;

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

     
        gameOver = false;
    }
    if(!started)
    {
        started = true;
    }
    if(!gameOver){
        if(yMotion > 0)
        {
            yMotion = 0;
        }
        yMotion -= 10;
    }
}
    public static void main(String[] args) {
        flappybird = new Flappy_Bird();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            jump();
        }
    }

}
