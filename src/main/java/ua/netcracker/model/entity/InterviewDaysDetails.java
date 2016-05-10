package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author MaXim
 *
 */

@Component
public class InterviewDaysDetails {

    private int id;

    private int courseId;

    private String interviewDate;

    private String startTime;

    private String endTime;

    private int addressId;

    private int countStudents;

    private int countPersonal;

    public InterviewDaysDetails() {}

    public InterviewDaysDetails(int id, int courseId, String interviewDate, String startTime, String endTime, int addressId) {
        this.id = id;
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressId = addressId;
    }

    public InterviewDaysDetails(int id, int courseId, String interviewDate, String startTime, String endTime, int addressId, int countStudents, int countPersonal) {
        this.id = id;
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressId = addressId;
        this.countStudents = countStudents;
        this.countPersonal = countPersonal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCountStudents() { return countStudents; }

    public void setCountStudents(int countStudents) { this.countStudents = countStudents; }

    public int getCountPersonal() { return countPersonal; }

    public void setCountPersonal(int countPersonal) { this.countPersonal = countPersonal; }

    @Override
    public String toString() {
        return "InterviewDaysDetails{" +
                "countPersonal=" + countPersonal +
                ", countStudents=" + countStudents +
                ", addressId=" + addressId +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", interviewDate='" + interviewDate + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
