package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Candidate;

import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Candidate> {
    Candidate findCandidateById(Integer candidateId);
    List<Candidate> findAll();
    String findStatusById(Integer statusId);

    Candidate findCandidateByUserId(Integer userId);

    boolean saveCandidate(Candidate candidate);
}
