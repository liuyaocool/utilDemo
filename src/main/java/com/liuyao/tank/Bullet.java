package com.liuyao.tank;

import java.awt.*;

public class Bullet {

    private static final int SPEED = 20;
    private static final int WIDTH = 10, HEIGHT = 10;
    private final TankFrame tankFrame;

    private int x,y;
    private Dir dir;
    private boolean live;

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        move();
    }

    private void move() {
        if (!live) {
            this.tankFrame.bullets.remove(this);
        }
        switch (dir) {
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) live = false;
    }


    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tf;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
