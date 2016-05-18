package ua.netcracker.model.entity;

/**
 * Created by Nikita on 28.04.2016.
 */
public enum Status {
    Rejected(1, "Rejected"), Ready(2, "Ready"), No_interview(3, "No_interview"), Interview(4, "Interview"), Interview_dated(5, "Interview_dated"), Interview_process(6, "Interview_process"), Interview_passed(7, "Interviews_passed"), Trainee_accepted(8, "Trainee_accepted"), Job_accepted(9, "Job_accepted");



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
