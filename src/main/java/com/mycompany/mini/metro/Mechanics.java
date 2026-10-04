/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

/**
 *
 * @author nikit
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Mechanics {

    private double spawnInterval = 2.0;
    private int stationCapacity = 6;
    private int stationRadius = 20;
    private WorldState world;
    private List<Station> stations = world.getStations();
    private Random random = new Random();

    public Mechanics(WorldState world) {
        this.world = world;
    }

    public void update(double dt) {
        if (world.isGameOver()) {
            return;
        }

        world.changeElapsed(dt);
        if (spawnInterval - dt <= 0) {
            spawnPassenger();
            spawnInterval *= 0.999; //spawn faster overtime
        }

        for (Station station : stations) {
            if (station.getWaiting().size() > stationCapacity) {
                world.setGameOver();
            }
        }

    }

    public void spawnPassenger() {
        Station station = stations.get(random.nextInt(stations.size()));
        StationShape[] shapes = StationShape.values();
        StationShape destination = shapes[random.nextInt(shapes.length)];
        while (station.getShape() == destination) {
            //make sure the passanger has to travel somewhere. 
            //circle passenger on circle station does not make sense
            destination = shapes[random.nextInt(shapes.length)];
        }
        station.addPassenger(new Passenger(destination));
    }

    public String addStationToLine(int lineIndex, Station station) {
        if (world.isGameOver()) {
            return "Game over.";
        }
        MetroLine line = world.getLines().get(lineIndex);
        if (line.contains(station.getShape())) {
            return station.getName() + " is already on line " + (lineIndex + 1) + ".";
        }
        line.addStation(station);
        return null;
    }

    public Station findStationAt(double x, double y) {
        for (Station station : world.getStations()) {
            //distance between two points
            if (Math.hypot(station.getX() - x, station.getY() - y) <= stationRadius) {
                return station;
            }
        }
        return null;
    }
}
