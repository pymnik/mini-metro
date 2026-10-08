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
import java.util.Iterator;

public class Mechanics {

    private double spawnInterval = 2.0;
    private double spawnTimer = 1.0;
    private int stationCapacity = 6;
    private int trainSpeed = 1;
    private int stationRadius = 20;
    private WorldState world;
    private List<Station> stations;
    private Random random = new Random();

    public Mechanics(WorldState world) {
        this.world = world;
        this.stations = world.getStations();
    }

    public void update(double dt) {
        if (world.isGameOver()) {
            return;
        }

        world.changeElapsed(dt);
        spawnTimer -= dt;
        while (spawnTimer <= 0) {
            spawnPassenger();
            spawnInterval *= 0.999; //spawn faster overtime
            spawnTimer += spawnInterval;
        }

        for (Station station : stations) {
            if (station.getWaiting().size() > stationCapacity) {
                world.setGameOver();
            }
        }

        for (Train train : world.getTrains()) {
            updateTrain(train);
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
        if (line.contains(station)) {
            return station.getName() + " is already on line " + (lineIndex + 1) + ".";
        }
        line.addStation(station);
        if (line.getRoute().size() == 2) {
            Train train = new Train(
                    true,
                    new ArrayList<>(line.getRoute()),
                    lineIndex
            );
            world.addTrain(train, line);
            setInitialTrainPos(train, line);
        }
        return null;
    }

    public void updateTrain(Train train) {
        updateTrainPos(train);
//        updateTrainPassengers();
    }

    private void updateTrainPos(Train train) {
        if (train.getNextStations().isEmpty()) {
            return;
        }

        Station nextStation = train.getNextStations().getFirst();
        double dX = nextStation.getX() - train.getX();
        double dY = nextStation.getY() - train.getY();
        double dist = Math.hypot(dX, dY);

        // Close enough to arrive this tick: snap to the station and move on.
        if (dist <= trainSpeed) {
            train.setX(nextStation.getX());
            train.setY(nextStation.getY());
            train.getNextStations().removeFirst();

            dropOffPassengers(nextStation, train);
            collectPassengers(nextStation, train);

            if (train.getNextStations().isEmpty()) {
                train.flipDirection();
                List<Station> route = world.getLineById(train.getMetroLineIndex()).getRoute();
                if (train.getDirection()) {
                    train.setNextStations(new ArrayList<>(route));
                } else {
                    train.setNextStations(new ArrayList<>(route.reversed()));
                }
            }
            return;
        }

        train.setX(train.getX() + dX / dist * trainSpeed);
        train.setY(train.getY() + dY / dist * trainSpeed);
    }

    public void setInitialTrainPos(Train train, MetroLine line) {
        Station originStation = line.getOriginStation();
        train.setX(originStation.getX());
        train.setY(originStation.getY());
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

    public int getStationSize() {
        return this.stationRadius;
    }

    public void collectPassengers(Station station, Train train) {
        Iterator<Passenger> iterator = station.getWaiting().iterator();
        while (iterator.hasNext()) {
            Passenger p = iterator.next();
            train.pickUp(p);
            iterator.remove();
        }
    }

    public void dropOffPassengers(Station station, Train train) {
        
        Iterator<Passenger> iterator = train.getPassengers().iterator();
        if (!train.getPassengers().isEmpty()) {
            while (iterator.hasNext()) {
                Passenger p = iterator.next();
                if (p.getDestination() == station.getShape()) {
                    iterator.remove();
                    
                }
            }
        }
    }
}
