package com.liuyao.tank;

import com.liuyao.tank.enumm.Dir;
import com.liuyao.tank.enumm.Group;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tank extends TankParent {

    private static final int SPEED = 5;
    public static BufferedImage goodTankU = readImg("GoodTank.png"),
            goodTankL = rotateImage(goodTankU, -90),
            goodTankR = rotateImage(goodTankU, 90),
            goodTankD = rotateImage(goodTankU, 180);
    public static BufferedImage badTankU = readImg("BadTank.png"),
            badTankL = rotateImage(badTankU, -90),
            badTankR = rotateImage(badTankU, 90),
            badTankD = rotateImage(badTankU, 180);
    public static final int WIDTH = goodTankU.getWidth(), HEIGHT = goodTankU.getHeight();

    private Dir dir = Dir.DOWN;
    private boolean moving = true;
    private Random random = new Random();

    public Dir getDir() { return dir; }
    public void setDir(Dir dir) { this.dir = dir; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }

    public Tank(int x, int y, Dir dir, TankFrame tf, Group group) {
        super(tf, x, y);
        this.dir = dir;
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!living) {
            tankFrame.tanks.remove(this);
        }
        switch (this.group){
            case BAD:
                switch (this.dir) {
                    case LEFT: g.drawImage(badTankL, x, y, null); break;
                    case RIGHT: g.drawImage(badTankR, x, y, null); break;
                    case UP: g.drawImage(badTankU, x, y, null); break;
                    case DOWN: g.drawImage(badTankD, x, y, null); break;
                }
                break;
            case GOOD:
                switch (this.dir) {
                    case LEFT: g.drawImage(goodTankL, x, y, null); break;
                    case RIGHT: g.drawImage(goodTankR, x, y, null); break;
                    case UP: g.drawImage(goodTankU, x, y, null); break;
                    case DOWN: g.drawImage(goodTankD, x, y, null); break;
                }
                break;
        }

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
        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        Bullet b = new Bullet(bX, bY, this.dir, this.tankFrame, this.group);
        this.tankFrame.bullets.add(b);
    }
}
