package player;

public class Stats {
    private int education;
    private int stress;
    private int happiness;
    private int money;
    private int workExperience;

    // Stats Initialize

    public Stats(){
        this.education = 0;
        this.stress = 0;
        this.happiness = 0;
        this.workExperience = 0;
        this.money = 200;
    }

    // Method

    public void reduceStress(int amount){
        setStress(getStress() - amount);
    }

    public void gainStress(int amount){
        setStress(getStress() + amount);
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

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = Math.max(0, workExperience);
    }
}
