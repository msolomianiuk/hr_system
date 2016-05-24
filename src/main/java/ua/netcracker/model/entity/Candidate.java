package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by on 24.04.2016.
 *
 * @author Oleg
 */
@Component
public class Candidate implements Comparable<Candidate> {

    private int id;

    private int userId;

    private int courseId;


    private int statusId;

    private Status status;

    private int interviewDaysDetailsId;

    private Collection<Answer> answers;

    public Collection<InterviewResult> getInterviewResults() {
        return interviewResults;
    }

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

    public void setInterviewResults(Collection<InterviewResult> interviewResults) {
        this.interviewResults = interviewResults;
    }

    public void setInterviewDaysDetailsId(int interviewDaysDetailsId) {
        this.interviewDaysDetailsId = interviewDaysDetailsId;
    }

    public int compareTo(Candidate c) {
        int result = this.user.getName().concat(user.getSurname()).concat(user.getPatronymic()).compareTo(c.user.getName().concat(user.getSurname()).concat(user.getPatronymic()));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        if (id != candidate.id) return false;
        return user != null ? user.equals(candidate.user) : candidate.user == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
