package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Question {
    private int id;

    private String caption;

    private boolean isMandatory;

    private String type;

    private List<String> answerVariants;

    private int courseID;

    private int orderNumber;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAnswerVariants() {
        return answerVariants;
    }

    public void setAnswerVariants(List<String> answerVariants) {
        this.answerVariants = answerVariants;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", isMandatory=" + isMandatory +
                ", type='" + type + '\'' +
                ", answerVariants=" + answerVariants +
                ", courseID=" + courseID +
                ", orderNumber=" + orderNumber +
                '}';
    }
}