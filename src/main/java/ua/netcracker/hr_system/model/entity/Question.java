package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class Question {
    /**
     * Table "question"
     * ID entry in tables "candidate_answer", "question_addition" and "question_course_maps"
     */
    private int id;
    /**
     * Table "question"
     * Description question
     */
    private String caption;
    /**
     * Table "question"
     * Mandatory question
     */
    private boolean isMandatory;

    /**
     * Table "type"
     * Value type by type_id
     */
    private String typeValue;
    /**
     * Table "question_addition"
     * ID with question_addition
     */



    private List<String> additionValue;
    /**
     * Table "question_course_maps"
     * ID entry in tables "course_setting"
     */
    private int courseId;
    /**
     * Table "question_course_maps"
     * Number order with question_id and course_id in profile
     */
    private int orderNumber;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", isMandatory=" + isMandatory +
                ", typeValue='" + typeValue + '\'' +
                ", additionValue=" + additionValue +
                ", courseId=" + courseId +
                ", orderNumber=" + orderNumber +
                '}';
    }







    public Question() {
    }

    public Question(int id, String caption, boolean isMandatory, String typeValue, int courseId, int orderNumber) {
        this.id = id;
        this.caption = caption;
        this.isMandatory = isMandatory;
        this.typeValue = typeValue;
        this.courseId = courseId;
        this.orderNumber = orderNumber;
    }


    public Question(int id, String caption, boolean isMandatory, String typeValue, int courseId, int orderNumber, List<String> additionValue) {
        this.id = id;
        this.caption = caption;
        this.isMandatory = isMandatory;
        this.typeValue = typeValue;
        this.additionValue = additionValue;
        this.courseId = courseId;
        this.orderNumber = orderNumber;
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

    public void setMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }


    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }


    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public List<String> getAdditionValue() {
        return additionValue;
    }

    public void setAdditionValue(List<String> additionValue) {
        this.additionValue = additionValue;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

}
