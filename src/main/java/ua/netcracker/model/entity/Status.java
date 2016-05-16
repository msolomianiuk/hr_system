package ua.netcracker.model.entity;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Nikita on 28.04.2016.
 */
public enum Status {
    Interview(1, "Interview"),
    Interview_dated(2, "Interview_dated"),
    Interview_passed(3, "Interview_passed"),
    Interview_process(4, "Interview_process"),
    Job_accepted(5, "Job_accepted"),
    New(6, "New"),
    No_interview(7, "No_interview"),
    Ready(8, "Ready"),
    Rejected(9, "Rejected"),
    Trainee_accepted(10, "Trainee_accepted");

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
