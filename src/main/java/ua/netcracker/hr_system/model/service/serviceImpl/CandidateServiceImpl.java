package ua.netcracker.hr_system.model.service.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoImpl.CandidateDAOImpl;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.service.serviceInterface.CandidateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */
@Service("Candidate Service")
public class CandidateServiceImpl implements CandidateService{

    private static final Logger LOGGER = Logger.getLogger(CandidateServiceImpl.class);

    @Autowired
    private CandidateDAOImpl candidateDAO;

    @Override
    public Candidate getCandidateByID(Integer id){
        return candidateDAO.getCandidateByID(id);
    }

    @Override
    public Candidate getCandidateByUserID(Integer userID) {
        return candidateDAO.getCandidateByUserID(userID);
    }

    @Override
    public List<Candidate> getAllProfiles() {
        return candidateDAO.getAllProfiles();
    }

    @Override
    public String getStatusByID(Integer statusID) {
        return candidateDAO.getStatusById(statusID);
    }

    @Override
    public HashMap<Integer, Integer> getMarks(Integer candidateID) {
        return candidateDAO.getMarks(candidateID);
    }

    @Override
    public HashMap<Integer, String> getRecommendations(Integer id) {
        return candidateDAO.getRecommendations(id);
    }

    @Override
    public HashMap<Integer, String> getResponses(Integer id) {
        return candidateDAO.getResponses(id);
    }

    @Override
    public int getInterviewDayDetailsByID(Integer id) {
        return candidateDAO.getInterviewDayDetailsByID(id);
    }

    @Override
    public Map<Integer, String> getAllCandidateAnswers(Candidate candidate) {
        return candidateDAO.getAllCandidateAnswers(candidate);
    }

    @Override
    public List<Map<String, Object>> getInterviewers(Candidate candidate) {
        return candidateDAO.getInterviewers(candidate);
    }

    @Override
    public String getCandidateAnswer(Integer candidateID, Integer questionID) {
        return candidateDAO.getCandidateAnswer(candidateID, questionID);
    }

    @Override
    public boolean saveCandidate(Candidate candidate) {
        return candidateDAO.insertCandidate(candidate);
    }

    @Override
    public void saveAnswers(Candidate candidate) {
        candidateDAO.insertAnswers(candidate);
    }

    @Override
    public void deleteAnswers(Candidate candidate) {
        candidateDAO.deleteAnswers(candidate);
    }

    @Override
    public void saveOrUpdate(Candidate candidate) {
        try{
            candidateDAO.deleteAnswers(candidate);
            candidateDAO.insertAnswers(candidate);
        } catch (Exception e){
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
    }
}
