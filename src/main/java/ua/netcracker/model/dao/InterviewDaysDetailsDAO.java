package ua.netcracker.model.dao;

import ua.netcracker.model.entity.InterviewDaysDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by MaXim on 01.05.2016.
 */
public interface InterviewDaysDetailsDAO extends DAO<InterviewDaysDetails> {
    boolean remove(int id);

    boolean insertDate(InterviewDaysDetails interviewDaysDetails);

    InterviewDaysDetails findByDate(String Date);

    List<Map<String, Object>> findAllInterviewDetailsAddress();

    Map<String, Object> findInterviewDetailsAddressById(Integer id);

    public void removeByCourseId(int course_id);
}
