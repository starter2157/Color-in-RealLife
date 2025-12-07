package logic;

import application.Main;
import entity.places.PlaceName;
import player.Inventory;
import entity.jobs.Job;
import player.Stats;

public class Player {
    private String name;
    private Stats stats;
    private Job job;
    private Inventory inventory;
    private int timeUsed;
    private City cityMap;
    private PlaceName currentLocation;
    private Transportation transportation;
    private final int MAX_TIME_PER_TURN = 1000;

    // Player Initialize

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.job = null;
        this.inventory = new Inventory();
        this.timeUsed = 0;
        this.cityMap = new City();
        this.currentLocation = PlaceName.HOME;
        this.transportation = Transportation.WALK;
    }

    // Player Method

    public void rest(int amount){
        stats.reduceStress(amount);
    }

    public void gainStress(int stress){
        stats.gainStress(stress);
    }

    public void startTurn(){
        while(timeUsed < MAX_TIME_PER_TURN){
            this.doAction();
        }
    }

    public void doAction(){

    }

    public boolean isWin(){
        if(stats.getMoney() == 1000 && stats.getEducation() == 1000 && stats.getHappiness() == 1000) return true;
        return false;
    }

    // Getter and Setter
}
