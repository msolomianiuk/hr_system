package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class InterviewResult {

    private Integer interviewerId;

    private Integer mark;

    private String comment;

    private Recommendation recommendation;
    private User interviewer;

    public InterviewResult() {

    }

    public Integer getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Integer interviewerId) {
        this.interviewerId = interviewerId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }
}
