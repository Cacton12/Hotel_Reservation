/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

import java.time.LocalDate;

/**
 *
 * @author Colby
 */
public class HotelReservation {
    //Arrival
    private LocalDate arrival_date;

    public LocalDate getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(LocalDate arrival_date) {
        this.arrival_date = arrival_date;
    }
    //Departure
    private LocalDate Departure_date;

    public LocalDate getDeparture_date() {
        return Departure_date;
    }

    public void setDeparture_date(LocalDate Departure_date) {
        this.Departure_date = Departure_date;
    }
    //numNights
    
    private int numNights;

    public int getNumNights() {
        return numNights;
    }

    public void setNumNights(int numNights) {
        this.numNights = numNights;
    }
    //Price
    private double price;
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public HotelReservation(LocalDate arrival_date, LocalDate Departure_date, int numNights, double price) {
        this.arrival_date = arrival_date;
        this.Departure_date = Departure_date;
        this.numNights = numNights;
        this.price = price;
    }

    public HotelReservation() {
    }
    

    @Override
    public String toString() {
        return "HotelReservation{" + "arrival_date=" + arrival_date + ", Departure_date=" + Departure_date + ", numNights=" + numNights + ", price=" + price + '}';
    }
    


}
