package entity.places;

import entity.base.Restable;
import logic.Player;

public class Home extends Location implements Restable {

    private final int REST_VALUE = 200;

    public Home(){
        super(PlaceType.HOME);
    }

    @Override
    public void rest(Player player) {
        player.rest(REST_VALUE);
    }
}
