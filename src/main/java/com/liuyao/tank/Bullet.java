package com.liuyao.tank;

import com.liuyao.tank.enumm.Dir;
import com.liuyao.tank.enumm.Group;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends TankParent{

    public static BufferedImage bulletU = readImg("Bullet.png"),
            bulletL = rotateImage(bulletU, -90),
            bulletR = rotateImage(bulletU, 90),
            bulletD = rotateImage(bulletU, 180);
    public static final int WIDTH = bulletD.getWidth(), HEIGHT = bulletD.getHeight();
    private static final int SPEED = 20;

    private Dir dir;

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        super(tf, x, y);
        this.dir = dir;
        this.group = group;
    }

    public void paint(Graphics g){
        if (!living) tankFrame.bullets.remove(this);
        switch (dir) {
            case LEFT: g.drawImage(bulletL, x, y, null); break;
            case RIGHT: g.drawImage(bulletR, x, y, null); break;
            case UP: g.drawImage(bulletU, x, y, null); break;
            case DOWN: g.drawImage(bulletD, x, y, null); break;
        }
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x, y, WIDTH, HEIGHT);
//        g.setColor(c);

        move();
    }

    private void move() {
        switch (dir) {
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    //碰撞检测
    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (rect1.intersects(rect2)){
            tank.die();
            this.die();
        }
    }
}
