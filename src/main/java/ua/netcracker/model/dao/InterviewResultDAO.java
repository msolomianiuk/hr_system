package ua.netcracker.model.dao;

import java.util.Map;

/**
 * Created by Alex on 07.05.2016.
 */
public interface InterviewResultDAO extends DAO {

    Map<Integer, Integer> findMarks(Integer candidateId);
    Map<Integer, String> findRecommendations(Integer candidateId);
    Map<Integer, String> findComments(Integer candidateId);
    int getInterviewDayDetailsById(Integer candidateId);

}