package ua.netcracekr.hr_system.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by user on 24.04.2016.
 */
@Component
public class InterviewResult {
    /**
     * Table "interview_result"
     * ID entry in table "users"
     */
    private int interviewerId;
    /**
     * Table "interview_result"
     * ID entry in table "candidate"
     */
    private int candidateId;
    /**
     * Table "interview_result"
     * Mark for Candidate with candidate_id from Interviewer with interviewer_id
     */
    private int mark;
    /**
     * Table "interview_result"
     * Response for Candidate with candidate_id from Interviewer with interviewer_id
     */
    private String response;
    /**
     * Table "interview_result"
     * ID entry in table "interview_days_details"
     */
    private int daysDetailsId;
    /**
     * Tables "interview_result" and "recommendation"
     * ID entry in table "recommendation"
     */
    private int recommendationId;

    /**
     * Table "recommendation"
     * Value recommendation by recommendation_id
     */
    private String recommendationValue;

    public InterviewResult() {

    }

    public InterviewResult(int candidateId, int mark, String response, int recommendationId, int daysDetailsId) {
        this.candidateId = candidateId;
        this.mark = mark;
        this.response = response;
        this.recommendationId = recommendationId;
        this.daysDetailsId = daysDetailsId;
    }

    public InterviewResult(int interviewerId, int candidateId, int mark, String response, int recommendationId, int daysDetailsId) {
        this.interviewerId = interviewerId;
        this.candidateId = candidateId;
        this.mark = mark;
        this.response = response;
        this.recommendationId = recommendationId;
        this.daysDetailsId = daysDetailsId;
    }

    public int getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(int interviewerId) {
        this.interviewerId = interviewerId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getDaysDetailsId() {
        return daysDetailsId;
    }

    public void setDaysDetailsId(int daysDetailsId) {
        this.daysDetailsId = daysDetailsId;
    }

    public String getRecommendationValue() {
        return recommendationValue;
    }

    public void setRecommendationValue(String recommendationValue) {
        this.recommendationValue = recommendationValue;
    }

    @Override
    public String toString() {
        return "Interview_Result{" +
                "interviewer_id=" + interviewerId +
                ", candidate_id=" + candidateId +
                ", mark=" + mark +
                ", response=" + response +
                ", recommendation_id=" + recommendationId +
                ", interview_days_details_id=" + daysDetailsId +
                "}";
    }
}
