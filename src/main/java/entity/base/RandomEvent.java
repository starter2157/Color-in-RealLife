package entity.base;

import logic.Player;

public abstract class RandomEvent {
    private String description;

    public RandomEvent(String description){
        this.description = description;
    }

    abstract public void trigger(Player player);

    public String getDescription(){
        return description;
    }

}
