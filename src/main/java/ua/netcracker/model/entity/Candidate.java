package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Candidate {

    private int id;

    private int userId;

    private int courseId;

    private int statusId;

    private Status status;

    private int interviewDaysDetailsId;

    private Collection<Answer> answers;

    private Collection<InterviewResult> interviewResults;

    private User user;


    public Candidate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getInterviewDaysDetailsId() {
        return interviewDaysDetailsId;
    }

    public void setInterviewDaysDetailsId(int interviewDaysDetailsId) {
        this.interviewDaysDetailsId = interviewDaysDetailsId;
    }
}
