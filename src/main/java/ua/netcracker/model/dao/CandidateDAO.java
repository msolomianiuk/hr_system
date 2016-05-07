package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Candidate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Candidate> {
    Candidate findCandidateById(Integer candidateID);
    List<Candidate> findAll();
    String findStatusById(Integer statusID);
    HashMap<Integer, Integer> getMarks(Integer candidateID);
    HashMap<Integer, String> getRecommendations(Integer ID);
    HashMap<Integer, String> getResponses(Integer ID);
    int getInterviewDayDetailsById(Integer ID);
    Candidate getCandidateByUserId(Integer userID);

    boolean insertCandidate(Candidate candidate);
}
