package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Candidate;

import java.util.Collection;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Candidate> {
    int findInterviewDetailsByCandidateId(Integer candidateId);

    Candidate findByCandidateId(Integer candidateId);
    Collection<Candidate> findAll();
    String findStatusById(Integer statusId);

    Candidate findByUserId(Integer userId);

    boolean saveCandidate(Candidate candidate);
}
