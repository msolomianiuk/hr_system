package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Candidate {

    private int id;

    private int userId;

    private int courseId;

    private int statusId;

    private String statusValue;

    private int questionId;

    private User user;

    private String answerValue;

    public Candidate() {
    }

    public Candidate(int id, int userId, int statusId, int courseId) {
        this.id = id;
        this.userId = userId;
        this.statusId = statusId;
        this.courseId = courseId;
    }

    public Candidate(int userId, int statusId, int courseId) {
        this.userId = userId;
        this.statusId = statusId;
        this.courseId = courseId;
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

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int status_id) {
        this.statusId = status_id;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course_id) {
        this.courseId = course_id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    @Override
    public String toString() {
        return "Candidate {" +
                "id=" + id +
                ", user_id=" + userId +
                ", status_id=" + statusId +
                ", status_value=" + statusValue +
                ", course_id=" + courseId +
                "}";
    }

    public void setUser(User user) {
        this.user = user;
    }
}
