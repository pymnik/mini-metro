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

    private int remainingCapacity = 6;
    private List<Passenger> passengers;
    private boolean direction = true;
    private List<Station> nextStations = new ArrayList<>();
    private int x;
    private int y;

    public Train(boolean direction, List<Station> nextStations, int x, int y) {
        this.direction = direction;
        this.nextStations = nextStations;
        this.x = x;
        this.y = y;
    }

    public int getCapacity() {
        return remainingCapacity;
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
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}
