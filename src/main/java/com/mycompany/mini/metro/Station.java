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

public class Station {

    private StationShape shape;
    private String name;
    private double x;
    private double y;
    private List<Passenger> waiting = new ArrayList<>();

    public Station(StationShape shape, String name, double x, double y) {
        this.shape = shape;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public StationShape getShape() {
        return this.shape;
    }

    public String getName() {
        return this.name;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public List<Passenger> getWaiting() {
        return this.waiting;
    }

    public void addPassenger(Passenger passenger) {
        waiting.add(passenger);
    }

    @Override
    public String toString() {
        String result = name + " shape: " + shape
                + " waiting: " + waiting.size() + " position: x=" + x + " y=" + y;
        return result;
    }
}
