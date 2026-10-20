/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mini.metro;

/**
 *
 * @author lasse
 */
public class Statistics {
    private int delivered = 0;
    private int waiting = 0;
    private int onTrains = 0;
    private String mostCrowded = "";
    
    public static String formatTime(double elapsedSeconds){
        int total = (int) elapsedSeconds;
        return String.format("%d:%02d", total/60, total%60);
    }
    
    public void addDelivered(){
        this.delivered++;
    }
    public int getDelivered(){
        return this.delivered;
    }
    
    public void addWaiting(){
        this.waiting++;
    }
    public void removeWaiting(){
        this.waiting--;
    }
    public int getWaiting(){
        if(this.waiting < 0){
            return 0;
        } else{
            return this.waiting;
        }
    }
    
    public void addOnTrains(){
        this.onTrains++;
    }
    public void removeOnTrains(){
        this.onTrains--;
    }
    public int getOnTrains(){
        return this.onTrains;
    }
    
    public String getMostCrowded(){
        return this.mostCrowded;
    }
    public void setMostCrowded(String mostCrowded){
        this.mostCrowded = mostCrowded;
    }
}
