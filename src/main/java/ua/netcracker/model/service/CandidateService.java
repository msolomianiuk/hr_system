package ua.netcracker.model.service;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.Collection;
import java.util.List;

/**
 * @author Alyona Bilous 05/05/2016
 */

public interface CandidateService {


    Candidate getCandidateById(Integer id);


    List<Candidate> getAll();

    String getStatusById(Integer statusID);


    Collection<Answer> getAllCandidateAnswers(Candidate candidate);


    boolean saveCandidate(Candidate candidate);


    void saveAnswers(Candidate candidate);

    void deleteAnswers(Candidate candidate);

    void saveOrUpdate(Candidate candidate);
}
