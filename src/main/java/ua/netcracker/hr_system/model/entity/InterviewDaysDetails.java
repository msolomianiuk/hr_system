package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class InterviewDaysDetails {

    private int id;

    private int courseId;

    private String interviewDate;

    private int addressId;

    private int employeesMaxCount;

    private int candidateMaxCount;

    private String interviewAddress;

    private int roomCapacity;

    public InterviewDaysDetails() {

    }

    public InterviewDaysDetails(int id, int courseId, String interviewDate, int addressId, int employeesMaxCount,
                                int candidateMaxCount) {
        this.id = id;
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.addressId = addressId;
        this.employeesMaxCount = employeesMaxCount;
        this.candidateMaxCount = candidateMaxCount;
    }

    public InterviewDaysDetails(int courseId, String interviewDate, int addressId, int employeesMaxCount,
                                int candidateMaxCount) {
        this.courseId = courseId;
        this.interviewDate = interviewDate;
        this.addressId = addressId;
        this.employeesMaxCount = employeesMaxCount;
        this.candidateMaxCount = candidateMaxCount;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getEmployeesMaxCount() {
        return employeesMaxCount;
    }

    public void setEmployeesMaxCount(int employeesMaxCount) {
        this.employeesMaxCount = employeesMaxCount;
    }

    public int getCandidateMaxCount() {
        return candidateMaxCount;
    }

    public void setCandidateMaxCount(int candidateMaxCount) {
        this.candidateMaxCount = candidateMaxCount;
    }

    public String getInterviewAddress() {
        return interviewAddress;
    }

    public void setInterviewAddress(String interviewAddress) {
        this.interviewAddress = interviewAddress;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
    @Override
    public String toString(){
        return "Interview_Days_Details{" +
                "id=" + id +
                ", course_id=" + courseId +
                ", interview_date=" + interviewDate +
                ", address_id=" + addressId +
                ", employees_max_count=" + employeesMaxCount +
                ", candidate_max_count=" + candidateMaxCount +
                "}";
    }
}
