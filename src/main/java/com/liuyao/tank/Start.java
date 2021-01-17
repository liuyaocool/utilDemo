package com.liuyao.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Start {

    public static void main(String[] args) {
        // 窗口类
        TankFrame frame = new TankFrame();

        while (true) {
            try {
                Thread.sleep(50);
                frame.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }
}
