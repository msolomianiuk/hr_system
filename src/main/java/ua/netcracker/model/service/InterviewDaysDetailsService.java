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

    InterviewDaysDetails findByDate(String date);

    List<InterviewDaysDetails> findAll();

    boolean add(InterviewDaysDetails interviewDaysDetails);

    void addDate(InterviewDaysDetails interviewDaysDetails);

    void addDateList(CourseSetting courseSetting);

    void update(InterviewDaysDetails interviewDaysDetails);

    void removeByCourseId(int course_id);

    void remove(int id);

    String sortCandidateToDays(CourseSetting courseSetting);

    boolean timeIsFree(InterviewDaysDetails interviewDaysDetails);

    InterviewDaysDetails setInterviewDateDetails(String id,String startTime, String endTime,int addressId);

    List<Map<String, Object>> findAllInterviewDetailsAddress();

    Map<String, Object> findInterviewDetailsAddressById(Integer id);
}
