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

public class Train {

    private int remainingCapacity = 4;
    private List<Passenger> passengers = new ArrayList<>();
    private boolean direction = true;
    private List<Station> nextStations = new ArrayList<>();
    private double x;
    private double y;
    private int metroLineIndex;

    public Train(boolean direction, List<Station> nextStations, int metroLineIndex) {
        this.direction = direction;
        this.nextStations = nextStations;
        this.metroLineIndex = metroLineIndex;

    }

    public int getCapacity() {
        return remainingCapacity;
    }

    public int getMetroLineIndex() {
        return metroLineIndex;
    }

    public List<Station> getNextStations() {
        return nextStations;
    }

    public void pickUp(Passenger passenger) {
        passengers.add(passenger);
        remainingCapacity -= 1;
    }

    public void dropOff(Passenger passenger) {
        if (passengers.contains(passenger)) {
            passengers.remove(passenger);
            remainingCapacity += 1;
        } else {
            return;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setNextStations(List<Station> next) {
        nextStations = next;
    }

    public boolean getDirection() {
        return this.direction;
    }

    public void flipDirection() {
        direction = !direction;
    }
    
    public List<Passenger> getPassengers(){
        return passengers;
    }
}
