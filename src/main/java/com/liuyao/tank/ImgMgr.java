package com.liuyao.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImgMgr {

    public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD;
    public static BufferedImage badTankL, badTankU, badTankR, badTankD;
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            goodTankU = ImageIO.read(ImgMgr.class.getClassLoader()
                    .getResourceAsStream("static/img/tank/GoodTank.png"));
            goodTankL = rotateImage(goodTankU, -90);
            goodTankR = rotateImage(goodTankU, 90);
            goodTankD = rotateImage(goodTankU, 180);

            badTankU = ImageIO.read(ImgMgr.class.getClassLoader()
                    .getResourceAsStream("static/img/tank/BadTank.png"));
            badTankL = rotateImage(badTankU, -90);
            badTankR = rotateImage(badTankU, 90);
            badTankD = rotateImage(badTankU, 180);

            bulletU = ImageIO.read(ImgMgr.class.getClassLoader().getResourceAsStream("static/img/tank/Bullet.png"));
            bulletL = rotateImage(bulletU, -90);
            bulletR = rotateImage(bulletU, 90);
            bulletD = rotateImage(bulletU, 180);

            for(int i=0; i<16; i++)
                explodes[i] = ImageIO.read(ImgMgr.class.getClassLoader()
                        .getResourceAsStream("static/img/tank/e" + (i+1) + ".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
