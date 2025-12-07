package entity.places;

public class Location {
    protected final PlaceName placeName;
    protected final int DISTANT;

    public Location(PlaceName placeName, int distant){
        this.placeName = placeName;
        this.DISTANT = distant;
    }

}
