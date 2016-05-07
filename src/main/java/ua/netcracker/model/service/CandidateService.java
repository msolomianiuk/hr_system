package ua.netcracker.model.service;


import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.Collection;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */

public interface CandidateService {


    Candidate getCandidateById(Integer id);

    Candidate getCandidateByUserId(Integer userId);

    Collection<Candidate> getAllCandidates();

    String getStatusById(Integer statusId);

    Map<Integer, Integer> getMarks(Integer candidateId);

    Map<Integer, String> getRecommendations(Integer id);

    Map<Integer, String> getComments(Integer id);

    int getInterviewDayDetailsById(Integer id);

    Collection<Answer> getAllCandidateAnswers(Candidate candidate);

    boolean saveCandidate(Candidate candidate);

    void saveAnswers(Candidate candidate);

    void deleteAnswers(Candidate candidate);

    void saveOrUpdate(Candidate candidate);
}
