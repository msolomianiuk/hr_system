package ua.netcracekr.hr_system.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Candidate {
    /**
     * Table "candidate"
     * ID entry in tables "interview_result", "question_course_maps" and "candidate_answer"
     */
    private int id;
    /**
     * Table "candidate"
     * ID entry in table "users"
     */
    private int userId;
    /**
     * Table "candidate"
     * ID entry in table "course_setting"
     */
    private int courseId;
    /**
     * Tables "candidate" and "status"
     * ID entry in table "status"
     */
    private int statusId;
    /**
     * Table "status"
     * Value status by status_id
     */
    private String statusValue;

    /**
     * Table "candidate_answer"
     * ID entry in table "question"
     */
    private int questionId;

    /**
     * Table "candidate_answer"
     * Value answer by question_id and candidate_id
     */
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

}
