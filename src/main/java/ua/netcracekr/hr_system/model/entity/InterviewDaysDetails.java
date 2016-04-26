package ua.netcracekr.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by user on 24.04.2016.
 */
@Component
public class InterviewDaysDetails {
    /**
     * Table "interview_days_details"
     * ID entry in table "interview_result"
     */
    private int id;
    /**
     * Table "interview_days_details"
     * ID entry in table "course_setting"
     */
    private int course_id;
    /**
     * Table "interview_days_details"
     * Date interview
     */
    private Date interviewDate;
    /**
     * Tables "interview_days_details" and "address"
     * ID entry in tables "address"
     */
    private int address_id;
    /**
     * Table "interview_days_details"
     * Max count employees on interview
     */
    private int employeesMaxCount;
    /**
     * Table "interview_days_details"
     * Max count candidates on interview
     */
    private int candidateMaxCount;
    /**
     * Table "address"
     * Address interview
     */
    private String interviewAddress;
    /**
     * Table "address"
     * Capacity room
     */
    private int roomCapacity;

}
