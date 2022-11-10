package com.game.snake;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame e = new Snake();
                e.setVisible(true);
            }
        });
    }
}