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
import java.util.HashMap;

public class WorldState {

    private List<Station> toAddStations = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private List<MetroLine> lines = new ArrayList<>();
    private List<Train> trains = new ArrayList<>();
    private HashMap<MetroLine, Train> trainMap = new HashMap<>();
    private boolean gameOver = false;
    private long elapsed = System.nanoTime();

    public WorldState() {
        stations.add(new Station(StationShape.CIRCLE, "HAG", 460, 480));
        stations.add(new Station(StationShape.SQUARE, "RTM", 520, 520));
        stations.add(new Station(StationShape.TRIANGLE, "AMS", 580, 400));
        stations.add(new Station(StationShape.DIAMOND, "GRO", 900, 150));

        toAddStations.add(new Station(StationShape.CIRCLE, "BRE", 580, 610));
        toAddStations.add(new Station(StationShape.TRIANGLE, "ZWL", 830, 350));
        toAddStations.add(new Station(StationShape.SQUARE, "UTR", 660, 460));
        toAddStations.add(new Station(StationShape.CIRCLE, "TLB", 660, 620));
        toAddStations.add(new Station(StationShape.CIRCLE, "EIN", 745, 650));
        toAddStations.add(new Station(StationShape.CIRCLE, "ARN", 800, 520));
        toAddStations.add(new Station(StationShape.DIAMOND, "MST", 800, 850));
        toAddStations.add(new Station(StationShape.CIRCLE, "ENS", 950, 450));
        toAddStations.add(new Station(StationShape.TRIANGLE, "ANT", 530, 720));

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

    public MetroLine getLineById(int id) {
        return lines.get(id);
    }

    public void addTrain(Train train, MetroLine line) {
        trains.add(train);
        line.addTrain(train);
        trainMap.put(line, train);
    }

    public List<Train> getTrains() {
        return trains;
    }

    public Train getTrainByLine(MetroLine line) {
        return trainMap.get(line);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public long getElapsed() {
        return this.elapsed;
    }

    public void changeElapsed(double dt) {
        this.elapsed += dt;
    }

    public void addActiveStation() {
        if (toAddStations.size() > 0) {
            System.out.println(toAddStations.getFirst());
            stations.add(toAddStations.getFirst());
            toAddStations.removeFirst();
        }
    }

    public void clearSelectedLine(int lineIndex) {
        MetroLine line = lines.get(lineIndex);
        Train train = trainMap.remove(line);   // returns the removed train (or null)
        if (train != null) {
            trains.remove(train);              // remove by object, not index
        }
        line.removeTrain();
        line.clearRoute();
    }
}
