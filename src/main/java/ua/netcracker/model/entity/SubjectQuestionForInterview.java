package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by bohda on 09.05.2016.
 */
@Component
public class SubjectQuestionForInterview {

    private int id;
    private String value;
    private int roleId;
    private Collection<QuestionForInterview> questionForInterviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Collection<QuestionForInterview> getQuestionForInterviews() {
        return questionForInterviews;
    }

    public void setQuestionForInterviews(Collection<QuestionForInterview> questionForInterviews) {
        this.questionForInterviews = questionForInterviews;
    }

    public SubjectQuestionForInterview() {
    }

    @Override
    public String toString() {
        return "SubjectQuestionForInterview{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", roleId=" + roleId +
                ", questionForInterviews=" + questionForInterviews +
                '}';
    }
}
