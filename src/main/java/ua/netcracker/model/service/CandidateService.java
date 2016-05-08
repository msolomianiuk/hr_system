package ua.netcracker.model.service;


import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.InterviewResult;

import java.util.Collection;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */

public interface CandidateService {
    Collection<Candidate> getCandidateByStatus(String status);

    //
    void saveInterviewResult(Candidate candidate, InterviewResult interviewResult);


//
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

    Candidate saveAnswers(String answersJsonString);

    Candidate getCurrentCandidate();

    void deleteAnswers(Candidate candidate);

    void saveOrUpdateAnswers(Candidate candidate);
}
