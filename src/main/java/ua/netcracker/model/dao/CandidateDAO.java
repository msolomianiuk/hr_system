package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Candidate> {
    Candidate getCandidateByID(Integer candidateID);
    List<Candidate> getAllProfiles();
    String getStatusById(Integer statusID);
    HashMap<Integer, Integer> getMarks(Integer candidateID);
    HashMap<Integer, String> getRecommendations(Integer ID);
    HashMap<Integer, String> getResponses(Integer ID);
    int getInterviewDayDetailsByID(Integer ID);
    Candidate getCandidateByUserID(Integer userID);
    Map<Integer, String> getAllCandidateAnswers(Candidate candidate);

    List<Map<String, Object>> getInterviewers(Candidate candidate);
    String getCandidateAnswer(Integer candidateID,Integer questionID);


    boolean insertCandidate(Candidate candidate);
    void insertAnswers(Candidate candidate);

    void deleteAnswers(Candidate candidate);
}
