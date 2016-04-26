package ua.netcracekr.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Alex on 24.04.2016.
 */
@Component
public class CourseSetting {
    /**
     * Table "course_setting"
     * ID entry in tables "interview_days_details", "question_course_maps" and "candidate"
     */
    private int id;
    /**
     * Table "course_setting"
     * Count student for interview
     */
    private int interviewCount;
    /**
     * Table "course_setting"
     * Count student for courses
     */
    private int courseCount;
    /**
     * Table "course_setting"
     * Time interview for student
     */
    private int interviewTime;
    /**
     * Table "course_setting"
     * Date start registration
     */
    private Date registrationStartDate;
    /**
     * Table "course_setting"
     * Date end registration
     */
    private Date registrationEndDate;
    /**
     * Table "course_setting"
     * Date start course
     */
    private Date courseStartDate;

    public CourseSetting() {

    }

    public CourseSetting(int id, int interviewCount, int courseCount, int interviewTime,
                         Date registrationStartDate, Date registrationEndDate, Date courseStartDate) {
        this.id = id;
        this.interviewCount = interviewCount;
        this.courseCount = courseCount;
        this.interviewTime = interviewTime;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.courseStartDate = courseStartDate;
    }

    public CourseSetting(int interviewCount, int courseCount, int interviewTime,
                         Date registrationStartDate, Date registrationEndDate, Date courseStartDate) {
        this.interviewCount = interviewCount;
        this.courseCount = courseCount;
        this.interviewTime = interviewTime;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.courseStartDate = courseStartDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void setInterviewCount(int interviewCount) {
        this.interviewCount = interviewCount;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public int getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(int interviewTime) {
        this.interviewTime = interviewTime;
    }

    public Date getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(Date registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public Date getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(Date registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }
    @Override
    public String toString(){
        return "Course_Setting{" +
                "id=" +id +
                ", student_for_interview_count=" + interviewCount +
                ", student_for_course_count=" + courseCount +
                ", interview_time_for_student=" + interviewTime +
                ", registration_start_date=" + registrationStartDate +
                ", registration_end_date=" + registrationEndDate +
                ", course_start_date=" + courseStartDate +
                "}";
    }
}
