package entity.jobs;

import entity.base.Workable;
import logic.Player;

public class Job implements Workable {
    protected int salary;

    public Job (int salary) {
        this.salary = salary;
    }

    public void work (Player player) {
        player.work();
        player.gainMoney(getSalary());
        player.gainStress(1);
    }

    public int getSalary () {
        return salary;
    }
}
