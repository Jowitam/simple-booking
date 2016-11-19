package com.github.jowitam;

/**
 * klasa opisująca dostępność pokoju (true or false)
 * available = dostępność
 */
public class Booking {

    private final boolean available;

    public Booking(boolean available){
        this.available = available;
    }


    public boolean getAvailable(){
        return available;
    }
}
