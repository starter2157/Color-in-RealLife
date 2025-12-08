package player;

public class Stats {
    private int education;
    private int stress;
    private int happiness;
    private int money;

    // Stats Initialize

    public Stats(){
        this.education = 0;
        this.stress = 0;
        this.happiness = 0;
        this.money = 200;
    }

    // Getter and Setter

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = Math.max(0, education);
    }

    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = Math.max(0, stress);
        this.stress = Math.min(this.stress, 10);
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = Math.max(0, happiness);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Math.max(0, money);
    }

}
