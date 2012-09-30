/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import android.location.Location;
import java.util.ArrayList;

/**
 *
 * @author damian
 */
public class Listing {
    private String name;
    private Location location;
    private double price;
    String fbid;
    private String description;
    private ArrayList<String> tags;
    
    public Listing(String name, Location location, double price, String description, ArrayList<String> tags, String fbid){
        this.name = name;
        this.location = location;
        this.price = price;
        this.description = description;
        this.tags = tags;
        this.fbid = fbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    
}
