package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.AnswersDAO;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.dao.InterviewResultDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.InterviewResult;
import ua.netcracker.model.service.CandidateService;

import java.util.Collection;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */
@Service("Candidate Service")
public class CandidateServiceImpl implements CandidateService {

    private static final Logger LOGGER = Logger.getLogger(CandidateServiceImpl.class);

    @Override
    public void saveInterviewResult(Candidate candidate, InterviewResult interviewResult) {

    }

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private AnswersDAO answersDAO;

    @Autowired
    private InterviewResultDAO interviewResultDAO;

    @Override
    public Candidate getCandidateById(Integer id) {
        return candidateDAO.findByCandidateId(id);
    }

    @Override
    public Candidate getCandidateByUserId(Integer userId) {
        return candidateDAO.findByUserId(userId);
    }

    @Override
    public Collection<Candidate> getAllCandidates() {
        return candidateDAO.findAll();
    }

    @Override
    public String getStatusById(Integer statusId) {
        return candidateDAO.findStatusById(statusId);
    }

    @Override
    public Map<Integer, Integer> getMarks(Integer candidateId) {
        return interviewResultDAO.findMarks(candidateId);
    }

    @Override
    public Map<Integer, String> getRecommendations(Integer candidateId) {
        return interviewResultDAO.findRecommendations(candidateId);
    }

    @Override
    public Map<Integer, String> getComments(Integer candidateId) {
        return interviewResultDAO.findComments(candidateId);
    }

    @Override
    public int getInterviewDayDetailsById(Integer candidateId) {
        return candidateDAO.findInterviewDetailsByCandidateId(candidateId);
    }

    @Override
    public Collection<Answer> getAllCandidateAnswers(Candidate candidate) {
        return answersDAO.findAll(candidate.getId());
    }

    @Override
    public boolean saveCandidate(Candidate candidate) {
        return candidateDAO.saveCandidate(candidate);
    }

    @Override
    public void saveAnswers(Candidate candidate) {
        answersDAO.saveAll(candidate);
    }

    @Override
    public void deleteAnswers(Candidate candidate) {
        answersDAO.deleteAnswers(candidate.getId());
    }

    @Override
    public void saveOrUpdateAnswers(Candidate candidate) {
        try {
            answersDAO.update(candidate);
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
    }
}

