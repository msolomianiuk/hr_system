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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (questionId != null ? !questionId.equals(answer.questionId) : answer.questionId != null) return false;
        return value != null ? value.equals(answer.value) : answer.value == null;

    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}