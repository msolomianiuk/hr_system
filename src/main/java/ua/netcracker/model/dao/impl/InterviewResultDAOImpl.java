package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import ua.netcracker.model.dao.InterviewResultDAO;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Alex on 07.05.2016.
 */
public class InterviewResultDAOImpl implements InterviewResultDAO {
    private static final Logger LOGGER = Logger.getLogger(InterviewResultDAOImpl.class);
    private static final String FIND_MARK = "Select mark, id_interviewer from \"hr_system\".interview_result " +
            "Where candidate_id = ";

    @Override
    public Map<Integer, Integer> findMarks(Integer candidateId) {
        return null;
    }

    @Override
    public Map<Integer, String> getRecommendations(Integer candidateId) {
        return null;
    }

    @Override
    public Map<Integer, String> getResponses(Integer candidateId) {
        return null;
    }

    @Override
    public int getInterviewDayDetailsById(Integer candidateId) {
        return 0;
    }



    @Override
    public Collection findAll() {
        return null;
    }

    @Override
    public Object find(int id) {
        return null;
    }

    @Override
    public boolean insert(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }
}
