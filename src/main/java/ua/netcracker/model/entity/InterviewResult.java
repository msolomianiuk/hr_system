package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class InterviewResult {

    private List<User> interviewers;

    private Map<Integer,Integer> marks;

    private Map<Integer,String> responds;

    private int daysDetailsId;

    private Map<Integer,String> recommendations;

    public InterviewResult() {

    }

    public void addInterviewer(User interviewer){
        this.interviewers.add(interviewer);
    }

    public List<User> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<User> interviewers) {
        this.interviewers = interviewers;
    }

    public Map<Integer, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<Integer, Integer> marks) {
        this.marks = marks;
    }

    public Map<Integer, String> getResponds() {
        return responds;
    }

    public void setResponds(Map<Integer, String> responds) {
        this.responds = responds;
    }

    public int getDaysDetailsId() {
        return daysDetailsId;
    }

    public void setDaysDetailsId(int daysDetailsId) {
        this.daysDetailsId = daysDetailsId;
    }

    public Map<Integer, String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Map<Integer, String> recommendations) {
        this.recommendations = recommendations;
    }
}
