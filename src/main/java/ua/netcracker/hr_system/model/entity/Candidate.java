package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Candidate {

    private int ID;

    private int userID;

    private int courseID;

    private int statusID;

    private Status status;

    private List<Integer> questionsID;

    private User user;

    private Map<Integer, Integer> mark;

    private Map<Integer, String> response;

    private Map<Integer, String> recommendation;

    private int interviewDaysDetails;

    private Map<Integer, Object> answers;


    public Candidate() {
    }

    public Candidate(int ID, int userID, int interviewDaysDetails, int statusID, int courseID) {
        this.ID = ID;
        this.interviewDaysDetails = interviewDaysDetails;
        this.userID = userID;
        this.statusID = statusID;
        this.courseID = courseID;
    }

    public Map<Integer, Object> getAnswers() {
        return answers;
    }

    public int getInterviewDaysDetails() {
        return interviewDaysDetails;
    }

    public Map<Integer, String> getRecommendation() {
        return recommendation;
    }

    public Map<Integer, String> getResponse() {
        return response;
    }

    public Map<Integer, Integer> getMark() {
        return mark;
    }

    public User getUser() {
        return user;
    }

    public List<Integer> getQuestionsID() {
        return questionsID;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusID() {
        return statusID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getUserID() {
        return userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setQuestionsID(List<Integer> questionsID) {
        this.questionsID = questionsID;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMark(Map<Integer, Integer> mark) {
        this.mark = mark;
    }

    public void setResponse(Map<Integer, String> response) {
        this.response = response;
    }

    public void setRecommendation(Map<Integer, String> recommendation) {
        this.recommendation = recommendation;
    }

    public void setInterviewDaysDetails(int interviewDaysDetails) {
        this.interviewDaysDetails = interviewDaysDetails;
    }

    public void setAnswers(Map<Integer, Object> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", courseID=" + courseID +
                ", statusID=" + statusID +
                ", status=" + status +
                ", questionsID=" + questionsID +
                ", user=" + user +
                ", mark=" + mark +
                ", response=" + response +
                ", recommendation=" + recommendation +
                ", interviewDaysDetails=" + interviewDaysDetails +
                ", answers=" + answers +
                '}';
    }
}
