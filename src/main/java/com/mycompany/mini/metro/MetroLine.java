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

public class MetroLine {

    private int number;
    private List<Station> route = new ArrayList<>();
    private List<Train> trains = new ArrayList<>();

    public MetroLine(int num) {
        this.number = num;
    }

    public int getNumber() {
        return this.number;
    }

    public void addStation(Station station) {
        this.route.add(station);
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void removeTrain(Train train) {
        trains.remove(train);
    }

    public void rewriteRoute(List<Station> route) {
        this.route = route;
    }

    public void clearRoute() {
        this.route.clear();
    }

    public List<Station> getRoute() {
        return this.route;
    }
    
    public Station getOriginStation(){
        return route.getFirst();
    }

    public boolean contains(Station station) {
        if (route.contains(station)) {
            return true;
        } else {
            return false;
        }
    }
}
