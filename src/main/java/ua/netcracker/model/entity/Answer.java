package ua.netcracker.model.entity;

/**
 * Created by Alyona on 06.05.2016.
 */
public class Answer {
    private Integer questionId;
    private String value;

    public Answer() {
    }


    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}