/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

/**
 *
 * @author nikit
 */
public class Passenger {

    private StationShape destination;

    public Passenger(StationShape destination) {
        this.destination = destination;
    }

    public StationShape getDestination() {
        return this.destination;
    }
}
