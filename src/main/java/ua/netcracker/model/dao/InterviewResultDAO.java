package ua.netcracker.model.dao;

import ua.netcracker.model.entity.InterviewResult;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Alex on 07.05.2016.
 * Updated by Alyona on 08.05.2016.
 */
public interface InterviewResultDAO extends DAO {

    Collection<InterviewResult> findResultsByCandidateId(Integer candidateId);

    Map<Integer, Integer> findMarks(Integer candidateId);

    Map<Integer, String> findRecommendations(Integer candidateId);

    Map<Integer, String> findComments(Integer candidateId);

    boolean createInterviewResult(Integer candidate_id, InterviewResult interviewResult);

    boolean updateInterviewResult(Integer candidate_id, InterviewResult interviewResult);

}
