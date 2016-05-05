package ua.netcracker.hr_system.model.entity;


public class CourseSetting {

    private int id;

    private int studentInterviewCount;

    private int studentCourseCount;

    private int interviewTime;

    private String registrationStartDate;

    private String registrationEndDate;

    private String interviewStartDate;

    private String interviewEndDate;

    private String courseStartDate;

    public CourseSetting() {

    }

    public CourseSetting(int id, int studentInterviewCount, int studentCourseCount, int interviewTime,
                         String interviewStartDate, String interviewEndDate, String registrationStartDate,
                         String registrationEndDate, String courseStartDate) {
        this.id = id;
        this.studentInterviewCount = studentInterviewCount;
        this.studentCourseCount = studentCourseCount;
        this.interviewTime = interviewTime;
        this.interviewStartDate = interviewStartDate;
        this.interviewEndDate = interviewEndDate;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.courseStartDate = courseStartDate;
    }

    public CourseSetting(int studentInterviewCount, int studentCourseCount, int interviewTime, String interviewStartDate,
                         String interviewEndDate, String registrationStartDate, String registrationEndDate,
                         String courseStartDate) {
        this.studentInterviewCount = studentInterviewCount;
        this.studentCourseCount = studentCourseCount;
        this.interviewTime = interviewTime;
        this.interviewStartDate = interviewStartDate;
        this.interviewEndDate = interviewEndDate;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.courseStartDate = courseStartDate;
    }

    public String getInterviewStartDate() {
        return interviewStartDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentInterviewCount() {
        return studentInterviewCount;
    }

    public void setStudentInterviewCount(int studentInterviewCount) {
        this.studentInterviewCount = studentInterviewCount;
    }

    public int getStudentCourseCount() {
        return studentCourseCount;
    }

    public void setStudentCourseCount(int studentCourseCount) {
        this.studentCourseCount = studentCourseCount;
    }

    public int getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(int interviewTime) {
        this.interviewTime = interviewTime;
    }

    public String getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(String registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public String getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(String registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public void setInterviewStartDate(String interviewStartDate) {
        this.interviewStartDate = interviewStartDate;
    }

    public String getInterviewEndDate() {
        return interviewEndDate;
    }

    public void setInterviewEndDate(String interviewEndDate) {
        this.interviewEndDate = interviewEndDate;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseSetting courseSetting = (CourseSetting) o;

        if (studentCourseCount != courseSetting.studentCourseCount) return false;
        if (studentInterviewCount != courseSetting.studentInterviewCount) return false;
        if (id != courseSetting.id) return false;
        if (interviewTime != courseSetting.interviewTime) return false;
        if (courseStartDate != null ? !courseStartDate.equals(courseSetting.courseStartDate) :
                courseSetting.courseStartDate != null)
            return false;
        if (interviewEndDate != null ? !interviewEndDate.equals(courseSetting.interviewEndDate) :
                courseSetting.interviewEndDate != null)
            return false;
        if (interviewStartDate != null ? !interviewStartDate.equals(courseSetting.interviewStartDate) :
                courseSetting.interviewStartDate != null)
            return false;
        if (registrationEndDate != null ? !registrationEndDate.equals(courseSetting.registrationEndDate) :
                courseSetting.registrationEndDate != null)
            return false;
        if (registrationStartDate != null ? !registrationStartDate.equals(courseSetting.registrationStartDate) :
                courseSetting.registrationStartDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + studentInterviewCount;
        result = 31 * result + studentCourseCount;
        result = 31 * result + (registrationStartDate != null ? registrationStartDate.hashCode() : 0);
        result = 31 * result + (registrationEndDate != null ? registrationEndDate.hashCode() : 0);
        result = 31 * result + (interviewStartDate != null ? interviewStartDate.hashCode() : 0);
        result = 31 * result + (interviewEndDate != null ? interviewEndDate.hashCode() : 0);
        result = 31 * result + (courseStartDate != null ? courseStartDate.hashCode() : 0);
        result = 31 * result + interviewTime;
        return result;
    }

}
