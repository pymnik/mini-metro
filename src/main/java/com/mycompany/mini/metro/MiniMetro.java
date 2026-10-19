/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mini.metro;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author nikit
 */
public class MiniMetro {
    private static WorldState world;
    private static Mechanics mechanics;
    
    public static void main() {

        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();

            world = new WorldState();
            mechanics = new Mechanics(world);
            
            MiniMetroGUI frame = new MiniMetroGUI();
            Renderer renderer = new Renderer(world, mechanics, frame);

            JPanel gamePanel = frame.getGamePanel();
            gamePanel.setLayout(new BorderLayout());
            gamePanel.add(renderer, BorderLayout.CENTER);

            //Connect the buttons
            frame.getLine1Button().addActionListener(e -> renderer.setSelectedLine(0));
            frame.getLine2Button().addActionListener(e -> renderer.setSelectedLine(1));
            frame.getLine3Button().addActionListener(e -> renderer.setSelectedLine(2));
            frame.getPauseButton().addActionListener(e -> renderer.pause());
            frame.getSpeedButton().addActionListener(e -> renderer.speed2x());
            frame.getClearLineButton().addActionListener(e -> renderer.clearLine());
            frame.getRestartButton().addActionListener(e -> {
                world = new WorldState();
                mechanics = new Mechanics(world);
                renderer.setGame(world, mechanics);
            });

            //Show the window
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(16, new ActionListener() {
                private long last = System.nanoTime();

                @Override
                public void actionPerformed(ActionEvent e) {
                    long now = System.nanoTime();
                    double dt = (now - last) / 1_000_000_000.0;
                    last = now;
                    mechanics.update(dt);
                    renderer.repaint();
                }
            });
            timer.start();
        });
    }
}
