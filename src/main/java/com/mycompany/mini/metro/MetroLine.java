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

    public MetroLine(int num) {
        this.number = num;
    }

    public int getNumber() {
        return this.number;
    }

    public void addStation(Station station) {
        this.route.add(station);
    }

    public void rewriteRoute(List<Station> route) {
        this.route = route;
    }

    public void clearRoute() {
        this.route.clear();
    }

    public boolean contains(StationShape shape) {
        if (route.contains(shape)) {
            return true;
        } else {
            return false;
        }
    }
}
