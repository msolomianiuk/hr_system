package ua.netcracekr.hr_system.model.entity;

import org.springframework.stereotype.Component;

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
     * Tables "question" and "type"
     * ID entry in tables "type"
     */
    private int typeId;
    /**
     * Table "type"
     * Value type by type_id
     */
    private String typeValue;
    /**
     * Table "question_addition"
     * ID with question_addition
     */
    private int additionId;
    /**
     * Table "question_addition"
     * Value addition by question_id and addition_id
     */
    private String additionValue;
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

    public Question() {
    }

    public Question(String caption, int typeId, boolean isMandatory) {
        this.caption = caption;
        this.typeId = typeId;
        this.isMandatory = isMandatory;
    }

    public Question(int id, String caption, int typeId, boolean isMandatory) {
        this.id = id;
        this.caption = caption;
        this.typeId = typeId;
        this.isMandatory = isMandatory;
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

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public int getAdditionId() {
        return additionId;
    }

    public void setAdditionId(int additionId) {
        this.additionId = additionId;
    }

    public String getAdditionValue() {
        return additionValue;
    }

    public void setAdditionValue(String additionValue) {
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
    @Override
    public String toString(){
        return "Question{" +
                "id=" + id +
                ", caption=" + caption +
                ", type_id=" + typeId +
                ", is_mandatory=" + isMandatory +
                "}";
    }
}
