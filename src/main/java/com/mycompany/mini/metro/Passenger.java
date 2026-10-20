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

public class Passenger {

    private StationShape destination;
    private Station intermediate;

    public Passenger(StationShape destination) {
        this.destination = destination;
    }

    public StationShape getDestination() {
        return this.destination;
    }

    public Station getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(Station st) {
        this.intermediate = st;
    }

    @Override
    public String toString() {
        String result = "Passenger of shape " + this.getDestination();
        return result;
    }
}
