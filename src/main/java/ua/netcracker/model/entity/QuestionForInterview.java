package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by bohda on 09.05.2016.
 */
@Component
public class QuestionForInterview {
    private int id;
    private String value;
    private int subjectId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public QuestionForInterview() {
    }

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
}
