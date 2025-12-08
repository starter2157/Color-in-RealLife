package entity.jobs;

import entity.base.Workable;
import logic.Player;

public class Job implements Workable {
    protected JobType jobType;
    protected int salary;

    public Job (JobType jobType, int salary) {
        this.jobType = jobType;
        this.salary = salary;
    }

    public int work (Player player) {
        return salary;
    }

    public JobType getJobType () {
        return jobType;
    }

    public int getSalary () {
        return salary;
    }
}
