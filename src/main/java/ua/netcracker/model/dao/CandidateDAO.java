package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;

import java.util.Collection;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Candidate> {
    int findInterviewDetailsByCandidateId(Integer candidateId);

    Candidate findByCandidateId(Integer candidateId);

    Collection<Candidate> findAll();

    Status findStatusById(Integer statusId);

    Collection<Candidate> findCandidateByStatus(String status);

    Candidate findByUserId(Integer userId);

    Collection<Candidate> findAllByCourse(Integer courseId);

    boolean saveCandidate(Candidate candidate);

}
