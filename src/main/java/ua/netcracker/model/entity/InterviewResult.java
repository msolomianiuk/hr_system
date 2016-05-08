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

    private String recommendation;

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

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
