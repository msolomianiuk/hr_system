package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    Map<Integer, String> findAllStatus();

    boolean updateCandidateStatus(Integer candidateID, Integer newStatusID);

    public Collection<Candidate> pagination(Integer elementPage, Integer fromElement);

    Long getRows(List<Answer> expected);

    public Collection<Candidate> findForSerach (Integer elementPage, Integer fromElement, String find);

    public long rowsFind(String find);

    Collection<Candidate> getAllMarked(User user);

    Collection<Candidate> findPart(Integer with, Integer to);

    Collection<Candidate> findPartByCourse(Integer courseId, Integer with, Integer to);

    Integer getCandidateCount(int courseId);

    Integer getCandidateCountByInterviewId(int courseId);

    Collection<Candidate> filterCandidates(List<Answer> expected, Integer limit, Integer offset);


}
