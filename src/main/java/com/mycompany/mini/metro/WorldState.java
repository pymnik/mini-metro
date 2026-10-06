/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

/**
 *
 * @author nikit
 */
import java.util.ArrayList;
import java.util.List;

public class WorldState {

    private List<Station> stations = new ArrayList<>();
    private List<MetroLine> lines = new ArrayList<>();
    private boolean gameOver = false;
    private long elapsed = System.nanoTime();

    public WorldState() {
        stations.add(new Station(StationShape.DIAMOND, "AMS", 350.0, 250.0));
        stations.add(new Station(StationShape.CIRCLE, "EIND", 430.0, 440.0));
        stations.add(new Station(StationShape.TRIANGLE, "GRO", 540.0, 100.0));

        for (int i = 0; i < 3; i++) {
            lines.add(new MetroLine(i));
        }
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public Station getStationById(int id) {
        if (id > stations.size()) {
            return null;
        } else {
            return stations.get(id);
        }
    }

    public List<MetroLine> getLines() {
        return this.lines;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver(){
        this.gameOver = true;
    }

    public long getElapsed() {
        return this.elapsed;
    }

    public void changeElapsed(double dt) {
        this.elapsed += dt;
    }
}
