package com.liuyao.tank;

import java.awt.*;

public class Tank {

    private static final int SPEED = 10;

    private int x, y;
    private Dir dir = Dir.DOWN;
    private boolean moving = false;
    private TankFrame tankFrame;

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public Dir getDir() { return dir; }
    public void setDir(Dir dir) { this.dir = dir; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tf;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(color);
        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;
        }
    }

    public void fire() {
        this.tankFrame.bullets.add(new Bullet(this.x, this.y, this.dir, this.tankFrame));
    }
}