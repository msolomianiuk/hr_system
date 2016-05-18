package ua.netcracker.model.service;

import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.InterviewDaysDetails;

import javax.naming.NamingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by MaXim on 05.05.2016.
 */
public interface InterviewDaysDetailsService {
    InterviewDaysDetails findById(int id);

    List<InterviewDaysDetails> findAll();

    boolean add(InterviewDaysDetails interviewDaysDetails);

    void addDate(InterviewDaysDetails interviewDaysDetails);

    void addDateList(CourseSetting courseSetting);

    void update(InterviewDaysDetails interviewDaysDetails);

    void removeByCourseId(int course_id);

    void remove(int id);

    String getStartTimeofInterview(int id);

    String getEndTimeofInterview(int id);

    String getDateofInterview(int id);

    int getIdbyDate(String date);

    InterviewDaysDetails setInterviewDateDetails(String id,String startTime, String endTime,int addressId);

    List<Map<String, Object>> findAllInterviewDetailsAddress();
}
