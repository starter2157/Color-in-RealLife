package logic;

import entity.base.Location;
import entity.places.*;

import java.util.ArrayList;
import java.util.List;

public class City {
    private List<Location> locations;

    public City(){
        this.locations = new ArrayList<Location>();
        this.locations.add(new Home());
        this.locations.add(new School());
        this.locations.add(new Store());
        this.locations.add(new Theatre());
        this.locations.add(new Workplace());
    }


}
