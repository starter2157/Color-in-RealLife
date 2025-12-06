package entity.base;

import entity.places.PlaceType;

public class Location {
    protected PlaceType placeType;

    public Location(PlaceType placeType){
        this.placeType = placeType;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }
}
