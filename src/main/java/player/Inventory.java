package player;

import entity.items.Lottery;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Lottery> items;
    private int numberOfItem;

    public Inventory(){
        this.items = new ArrayList<>();
        numberOfItem = 0;
    }

    public void addItem(Lottery lottery){
        items.add(lottery);
        numberOfItem++;
    }

    public int numberOfItem(){
        return numberOfItem;
    }

    public List<Lottery> getItems() {
        return items;
    }

}
