package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Candidate {

    private int id;

    private int userId;

    private int courseId;

    private int statusId;

    private Status statusValue;

    private int questionId;

    private User user;

    private Map<Integer, Integer> mark;

    private Map<Integer, String> response;

    private Map<Integer, String> recommendation;

    private int interviewDaysDetails;

    private Map<Integer, Object> answer;

    public Candidate() {
    }

    public void setAnswer(Map<Integer, Object> answer) {
        this.answer = answer;
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

    public Candidate(int id, int userId, int courseId, int statusId, String statusValue, int questionId, User user,
                      Map<Integer, Integer> mark, Map<Integer, String> response, Map<Integer,
            String> recommendation, int interviewDaysDetails, Map<Integer, Object> answer) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.statusId = statusId;
        this.questionId = questionId;
        this.user = user;

        this.mark = mark;
        this.response = response;
        this.recommendation = recommendation;
        this.interviewDaysDetails = interviewDaysDetails;
        this.answer = answer;
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

    public Map<Integer, Object> getAnswerValue() {
        return answer;
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
