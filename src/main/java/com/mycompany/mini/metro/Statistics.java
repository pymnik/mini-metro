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
        return this.waiting;
    }
}
