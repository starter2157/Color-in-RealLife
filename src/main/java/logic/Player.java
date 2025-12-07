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
    private double timeReduce;
    private City cityMap;
    private PlaceName currentLocation;
    private Transportation transportation;
    private final int MAX_TIME_PER_TURN = 960;

    // Player Initialize

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.job = null;
        this.inventory = new Inventory();
        this.timeUsed = 0;
        this.cityMap = new City();
        this.currentLocation = PlaceName.HOME;
        setTransportation(Transportation.WALK);
    }

    // Player Action Method

    public void rest(){
        useTime(60);
        reduceStress(1);
    }

    public void work(int money){
        useTime(60);
        gainMoney(money);
        gainStress(1);
        gainWorkExperience(4);
    }

    public void study(){
        useTime(60);
        gainStress(2);
        gainEducation(1);
    }

    public void travel(PlaceName destination){
        travelTime(destination);
        setCurrentLocation(destination);
    }

    // Adjust Player Stats

    private void gainStress(int stress){
        stats.setStress(stats.getStress() + stress);
    }

    private void reduceStress(int stress){
        stats.setStress(stats.getStress() - stress);
    }

    private void gainWorkExperience(int workExperience){
        stats.setWorkExperience(stats.getWorkExperience() + workExperience);
    }

    private void gainEducation(int education){
        stats.setEducation(stats.getEducation() + education);
    }

    private void gainHappiness(int happiness){
        stats.setHappiness(stats.getHappiness() + happiness);
    }

    private void reduceHappiness(int happiness){
        stats.setHappiness(stats.getHappiness() - happiness);
    }

    private void gainMoney(int money){
        stats.setMoney(stats.getMoney() + money);
    }

    private void reduceMoney(int money){
        stats.setMoney(stats.getMoney() + money);
    }

    // Time Management

    private void useTime(int time){
        this.timeUsed += time;
    }

    private void travelTime(PlaceName placeName){

    }

    // Turn Management

    public void startTurn(){
        while(timeUsed < MAX_TIME_PER_TURN){
            this.doAction();
        }
        timeUsed = 0;
    }

    public void doAction(){
        // do something depend on user click 1.rest 2.work 3.study 4.travel

    }

    /* Win Condition
    1. Mode Short Requirement (10 Rounds)
        - money 1200
        - education 15
        - happiness 500
    2. Mode Medium Requirement (20 Rounds)
        - money 2500
        - education 25
        - happiness 750
    3. Mode Long Requirement (30 Rounds)
        - money 4500
        - education 40
        - happiness 1500
    4. Mode Marathon Requirement (Till one Player meets requirement)
        - money 10000
        - education 82 (learn everything)
        - happiness 4000 */

    public boolean isWin(GameMode gameMode){
        if (gameMode.equals(GameMode.SHORT) &&
                stats.getMoney() >= 1200 &&
                stats.getEducation() >= 15 &&
                stats.getHappiness() >= 500) return true;
        else if(gameMode.equals(GameMode.MEDIUM) &&
                stats.getMoney() >= 2500 &&
                stats.getEducation() >= 25 &&
                stats.getHappiness() >= 750) return true;
        else if(gameMode.equals(GameMode.LONG) &&
                stats.getMoney() >= 4000 &&
                stats.getEducation() >= 40 &&
                stats.getHappiness() >= 1500) return true;
        else return gameMode.equals(GameMode.MARATHON) &&
                    stats.getMoney() >= 10000 &&
                    stats.getEducation() >= 82 &&
                    stats.getHappiness() >= 4000;
    }

    // Getter and Setter

    public void setTransportation(Transportation transportation){
        this.transportation = transportation;
        if(transportation.equals(Transportation.CAR)) setTimeReduce(4);
        else if(transportation.equals(Transportation.BICYCLE)) setTimeReduce(1.5);
        else if(transportation.equals(Transportation.BUS)) setTimeReduce(2);
        else setTimeReduce(1);
    }

    public void setTimeReduce(double timeReduce){
        this.timeReduce = timeReduce;
    }

    public void setCurrentLocation(PlaceName placeName){
        this.currentLocation = placeName;
    }

    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTimeReduce() {
        return timeReduce;
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

    public Transportation getTransportation() {
        return transportation;
    }

    public int getMAX_TIME_PER_TURN() {
        return MAX_TIME_PER_TURN;
    }
}
