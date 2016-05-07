package ua.netcracker.model.service;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */

public interface CandidateService {


    Candidate getCandidateByID(Integer id);

    Candidate getCandidateByUserID(Integer userID);

    List<Candidate> getAllProfiles();

    String getStatusByID(Integer statusID);

    HashMap<Integer, Integer> getMarks(Integer candidateID);

    HashMap<Integer, String> getRecommendations(Integer id);

    HashMap<Integer, String> getResponses(Integer id);

    int getInterviewDayDetailsByID(Integer id);

    Collection<Answer> getAllCandidateAnswers(Candidate candidate);

    Map<Integer, Object> convert(Collection<Answer> listAnswers);

    Collection<Answer> convertBack(Map<Integer, Object> mapAnswers);

    boolean saveCandidate(Candidate candidate);

    void saveAnswers(Candidate candidate);

    void deleteAnswers(Candidate candidate);

    void saveOrUpdate(Candidate candidate);
}
