package ua.netcracker.model.entity;

/**
 * Created by Nikita on 28.04.2016.
 */
public enum Status {
    Rejected(1, "Rejected"), New(2, "New"), Ready(3, "Ready"), Interview(4, "Interview"), No_interview(5, "No_interview"), Interview_dated(6, "Interview_dated"), Interview_process(7, "Interview_process"), Interview_passed(8, "Interviews_passed"), Trainee_accepted(9, "Trainee_accepted"), Job_accepted(10, "Job_accepted");

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
