package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.AnswersDAO;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.dao.QuestionDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.AnswerString;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.CandidateService;

import java.util.*;

/**
 * @author Alyona Bilous 05/05/2016
 */
@Service("Candidate Service")
public class CandidateServiceImpl implements CandidateService{

    private static final Logger LOGGER = Logger.getLogger(CandidateServiceImpl.class);
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private CandidateDAO candidateDAO;
    @Autowired
    private AnswersDAO answersDAO;

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
    public Collection<Answer> getAllCandidateAnswers(Candidate candidate) {
        return answersDAO.findAll(candidate.getId(), questionDAO.findAllMandatory());
    }
    @Override
    public Map<Integer,Object> convert(Collection<Answer> listAnswers){
        Map<Integer, Object> mapElements = new HashMap<>();
        for(Answer answer : listAnswers){
            mapElements.put(answer.getQuestionId(),answer.getValue());
        }
        return mapElements;
    }
    @Override
    public Collection<Answer> convertBack(Map<Integer,Object> mapAnswers){
        ArrayList<Answer> listAnswers = new ArrayList<>();
        for(Map.Entry<Integer,Object> entry: mapAnswers.entrySet()){
            Answer answer = new AnswerString();
            answer.setQuestionId(entry.getKey());
            answer.setValue(entry.getValue());
            listAnswers.add(answer);
        }
        return listAnswers;
    }



    @Override
    public boolean saveCandidate(Candidate candidate) {
        return candidateDAO.insertCandidate(candidate);
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
    public void saveOrUpdate(Candidate candidate) {
        try{
            answersDAO.deleteAnswers(candidate.getId());
            answersDAO.saveAll(candidate);
        } catch (Exception e){
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
    }
}

