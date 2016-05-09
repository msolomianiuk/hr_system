package ua.netcracker.model.entity;

/**
 * Created by Nikita on 28.04.2016.
 */
public enum Status {
    NEW(1, "New"), READY(2, "Ready"), INTERVIEW(3, "Interview"), NO_INTERVIEW(4, "No interview"), INTERVIEW_DATED(5, "Interview dated"),
    INTERVIEW_IN_PROCESS(6, "Interview process"), INTERVIEWS_PASSED(7, "Interviews passed"), TRAINEE_ACCEPTED(8, "Trainee accepted"),
    REJECTED(9, "Rejected"), JOB_ACCEPTED(10, "Job accepted");

    int id;
    String status;

    Status(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
