package logic;

import entity.base.Location;
import player.Inventory;
import entity.base.Job;
import player.Stats;

public class Player {
    private String name;
    private Stats stats;
    private Job job;
    private Inventory inventory;
    private int money;
    private int loan;
    private int timeUnits;
    private City cityMap;
    private Location currentLocation;

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.job = null;
        this.inventory = new Inventory();
        this.money = 200;
        this.loan = 0;
        this.timeUnits = 100;
        this.cityMap = new City();

    }

}
