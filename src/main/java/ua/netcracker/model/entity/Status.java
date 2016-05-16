package ua.netcracker.model.entity;

/**
 * Created by Nikita on 28.04.2016.
 */
public enum Status {
    Rejected(0, "Rejected"), New(1, "New"), Ready(2, "Ready"), Interview(3, "Interview"), No_interview(4, "No_interview"), Interview_dated(5, "Interview_dated"), Interview_process(6, "Interview_process"), Interviews_passed(7, "Interviews_passed"), Trainee_accepted(8, "Trainee_accepted"), Job_accepted(9, "Job_accepted");

    int id;
    String status;

    Status(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

}
