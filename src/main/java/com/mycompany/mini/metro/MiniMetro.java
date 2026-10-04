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

        Station ams = new Station(StationShape.CIRCLE, "ÄMS", 100.0, 150.0);
        Station eind = new Station(StationShape.SQUARE, "EIND", 50.0, 400.0);

        Passenger a = new Passenger(StationShape.SQUARE);
        Passenger b = new Passenger(StationShape.CIRCLE);

        ams.addPassenger(a);
        eind.addPassenger(b);

        System.out.println(ams.toString());
        System.out.println(eind.toString());
        System.out.println("First passenger at ams wants to:" + ams.getWaiting().get(0).getDestination());

//        WorldState s = new WorldState();
//        Mechanics m = new Mechanics(s);
//        Renderer r = new Renderer(s, m); // Draw state. Call m.addTrain on button press.
//        
//        Timer t = new Timer(20, e -> {
    

////            System.out.println(e);
//            m.update();
//            r.repaint();
//        })
    }
}

//class WorldState() {
//    ArrayList<Train> trains;
//    ArrayList<Passenger> passengers;
//}
//
//class Mechanics {
//    public Mechanics(WorldState s) {
//        this.s = s;
//    }
//    
//    public void update() {
//        // Update positions, randomly add passengers.
//        long currTime = System.nanoseconds();
//        long deltaTime = currTime - previousTime;
//    }
//}
