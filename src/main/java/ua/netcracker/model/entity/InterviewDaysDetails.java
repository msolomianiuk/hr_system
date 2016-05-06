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

    public InterviewDaysDetails() {}

    public InterviewDaysDetails(int id, int courseId, String interviewDate, String startTime, String endTime, int addressId) {
        this.id = id;
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressId = addressId;
    }

    public InterviewDaysDetails(int courseId, String interviewDate, String startTime, String endTime, int addressId) {
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressId = addressId;
    }

    public InterviewDaysDetails(int id, String interviewDate, String startTime, int addressId, String endTime) {
        this.id = id;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.addressId = addressId;
        this.endTime = endTime;
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


    @Override
    public String toString() {
        return "InterviewDaysDetails{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", interviewDate='" + interviewDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", addressId=" + addressId +
                '}';
    }
}
