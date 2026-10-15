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
    private boolean pause = false;

    public Mechanics(WorldState world) {
        this.world = world;
        this.stations = world.getStations();
    }

    public void update(double dt) {
        if (world.isGameOver() || this.pause) {
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
        if (world.getTrainByLine(line) != null) {
            world.getTrainByLine(line).addLastStation(station);
        }

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
    }

    private void updateTrainPos(Train train) {
        if (train.getNextStations().isEmpty()) {
            return;
        }

        Station nextStation = train.getNextStations().getFirst();
        double dX = nextStation.getX() - train.getX();
        double dY = nextStation.getY() - train.getY();
        double dist = Math.hypot(dX, dY);

        // snap to the station
        if (dist <= trainSpeed) {
            train.setX(nextStation.getX());
            train.setY(nextStation.getY());
            train.getNextStations().removeFirst();

            if (train.getNextStations().isEmpty()) {
                train.flipDirection();
                List<Station> route = new ArrayList<>(world.getLineById(train.getMetroLineIndex()).getRoute());
                if (!train.getDirection()) {
                    route = route.reversed();
                }
                route.removeFirst();
                train.setNextStations(new ArrayList<>(route));
            }

            dropOffPassengers(nextStation, train);
            collectPassengers(nextStation, train);
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
        for (Passenger p : new ArrayList<>(station.getWaiting())) {
            if (train.getCapacity() == 0) {
                return;
            }
            if (shouldTake(p, station, train)) {
                p.setIntermediate(findTransfer(p, train));
                train.pickUp(p);
                station.removePassenger(p);
            }
        }
    }

    public void dropOffPassengers(Station station, Train train) {
        List<Passenger> copyOfPassengers = new ArrayList(train.getPassengers());
        Iterator<Passenger> iterator = copyOfPassengers.iterator();
        if (!train.getPassengers().isEmpty()) {
            while (iterator.hasNext()) {
                Passenger p = iterator.next();
                if (p.getDestination().equals(station.getShape())) {
                    train.dropOff(p);
                } else if (p.getIntermediate() != null) {
                    if (p.getIntermediate().equals(station)) {
                        station.addPassenger(p);
                        train.dropOff(p);
                        p.setIntermediate(null);
                    }
                }
            }
        }
    }

    private boolean reachesShape(List<Station> stations, StationShape shape) {
        for (Station s : stations) {
            if (s.getShape() == shape) {
                return true;
            }
        }
        return false;
    }

    private boolean lineReachesShape(Station station, StationShape shape) {
        for (MetroLine line : world.getLines()) {
            if (line.contains(station) && reachesShape(line.getRoute(), shape)) {
                return true;
            }
        }
        return false;
    }

    private Station findTransfer(Passenger p, Train train) {
        for (Station candidate : train.getNextStations()) {
            for (int i = 0; i < world.getLines().size(); i++) {
                if (i == train.getMetroLineIndex()) {
                    continue;
                }
                MetroLine other = world.getLines().get(i);
                if (other.contains(candidate)
                        && reachesShape(other.getRoute(), p.getDestination())) {
                    return candidate;
                }
            }
        }
        return null;
    }

    public boolean shouldTake(Passenger p, Station here, Train train) {
        StationShape goal = p.getDestination();
        if (reachesShape(train.getNextStations(), goal)) {
            return true;                               // direct way
        }
        if (lineReachesShape(here, goal)) {
            return false;                              // direct way exist so wait for it
        }
        return findTransfer(p, train) != null;         // find a transfer
    }
    
    public void pause(){
        this.pause = !this.pause;
        
    }
    
    public boolean getPause(){
        return pause;
    }
}
