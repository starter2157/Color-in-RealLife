package logic;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public City getCityMap() {
        return cityMap;
    }

    public void setCityMap(City cityMap) {
        this.cityMap = cityMap;
    }

    public PlaceName getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(PlaceName currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public int getMAX_TIME_PER_TURN() {
        return MAX_TIME_PER_TURN;
    }
}
