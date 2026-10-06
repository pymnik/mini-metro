/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mini.metro;

import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author nikit
 */
public class MiniMetro {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        MiniMetroGUI gui = new MiniMetroGUI();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
    }
}
