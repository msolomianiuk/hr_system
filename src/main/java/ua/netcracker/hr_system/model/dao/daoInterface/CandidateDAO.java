package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.Candidate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDAO extends DAO<Integer, Candidate> {

    List<Candidate> getAllProfileById(int roleId);

    HashMap getProfileById();

    Candidate findById(Integer id);

    Candidate findByName(String name);

    String findStatusById(Integer statusId) throws SQLException;

    HashMap<Integer, Integer> getMark(Integer candidateId);

    HashMap<Integer, String> getRecommendation(Integer id) throws SQLException;

    HashMap<Integer, String> getResponse(Integer id);

    int getInterviewDaysDetailsById(Integer id);

    List<Candidate> getAllAnketsCandidates();


    void insertAnswer(Candidate candidate);

    void updateAnswer(Candidate candidate);

    void deleteAnswer(Candidate candidate);
}
