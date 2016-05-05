package ua.netcracker.hr_system.model.dao.daoInterface;


import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Integer, Candidate> {
    Candidate getCandidateByID(Integer candidateID);
    List<Candidate> getAllProfiles();
    String getStatusById(Integer statusID);
    HashMap<Integer, Integer> getMarks(Integer candidateID);
    HashMap<Integer, String> getRecommendations(Integer ID);
    HashMap<Integer, String> getResponses(Integer ID);
    int getInterviewDaysDetailsByID(Integer ID);
    Candidate getCandidateByUserID(Integer userID);
    Map<Integer, String> getAllCandidateAnswers(Candidate candidate);

    List<User> getInterviewers(Candidate candidate);
    String getCandidateAnswer(Integer candidateID,Integer questionID);


    boolean insertCandidate(Candidate candidate);
    void insertAnswers(Candidate candidate);

    void deleteAnswers(Candidate candidate);
}
