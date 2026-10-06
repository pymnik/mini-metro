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
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            
            // 1. Memory and logic
            WorldState world = new WorldState();
            Mechanics mechanics = new Mechanics(world);

            // 2. GUI: the window from the builder + the renderer
            MiniMetroGUI frame = new MiniMetroGUI();
            Renderer renderer = new Renderer(world, mechanics);

            // 3. Put the renderer inside GamePanel, on top of the background map
            JPanel gamePanel = frame.getGamePanel();
            gamePanel.setLayout(new BorderLayout());
            gamePanel.add(renderer, BorderLayout.CENTER);

            // 4. Connect the buttons
//            frame.getLine1Button().addActionListener(e -> renderer.setSelectedLine(0));
//            frame.getLine2Button().addActionListener(e -> renderer.setSelectedLine(1));
//            frame.getLine3Button().addActionListener(e -> renderer.setSelectedLine(2));

            // 5. Show the window
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // 6. Game loop: update the rules, then redraw, about 60 times per second
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
