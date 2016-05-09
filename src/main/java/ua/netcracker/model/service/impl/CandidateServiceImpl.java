package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.AnswersDAO;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.dao.InterviewResultDAO;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */
@Service("Candidate Service")
public class CandidateServiceImpl implements CandidateService {

    private static final Logger LOGGER = Logger.getLogger(CandidateServiceImpl.class);
    private int userId;


    @Override
    public Collection<Candidate> getCandidateByStatus(String status) {
        return candidateDAO.findCandidateByStatus(status);
    }

    @Override
    public void saveInterviewResult(Candidate candidate, InterviewResult interviewResult) {

    }

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CandidateDAO candidateDAO;
    @Autowired
    private CourseSettingService courseSettingService;
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
    public Status getStatusById(Integer statusId) {
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

    private Collection<Answer> parseJsonString(String answersJsonString) {
        Collection<Answer> listAnswers = new ArrayList<>();
        JSONObject obj = new JSONObject(answersJsonString);
        Iterator<?> keys = obj.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (obj.get(key) instanceof JSONArray) {
                JSONArray array = (JSONArray) obj.get(key);
                for (int i = 0; i < array.length(); i++) {
                    Answer answer = new Answer();
                    answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
                    answer.setValue(array.getString(i));
                    listAnswers.add(answer);
                }
                continue;
            }
            Answer answer = new Answer();
            answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
            answer.setValue((String) obj.get(key));
            listAnswers.add(answer);
        }
        return listAnswers;
    }

    @Override
    public Candidate saveAnswers(String answersJsonString) {
        Collection<Answer> listAnswers = parseJsonString(answersJsonString);
        Candidate candidate = getCurrentCandidate();
        if (candidate.getId() == 0) {
            candidate.setUserId(userId);
            candidate.setUser(userDAO.find(candidate.getUserId()));
            candidate.setStatusId(Status.NEW.getId());
            candidate.setCourseId(1);
            saveCandidate(candidate);
            candidate = getCandidateById(userId);
        }
        candidate.setAnswers(listAnswers);
        saveOrUpdateAnswers(candidate);
        return candidate;
    }

    @Override
    public Candidate getCurrentCandidate() {
        userId = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = userDetails.getUserId();
        }

        return getCandidateByUserId(userId);
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

    @Override
    public Collection<Candidate> getAllCandidates() {
        Collection<Candidate> listCandidates = candidateDAO.findAll();
        for (Candidate candidate : listCandidates) {
            if (candidate.getUser() == null) {
                candidate.setUser(userDAO.find(candidate.getUserId()));
            }
            if (candidate.getAnswers() == null) {
                candidate.setAnswers(answersDAO.findAll(candidate.getId()));
            }
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> getAllCandidatesIsView() {
        Collection<Candidate> listCandidates = new ArrayList<>();
        for (Candidate candidate : getAllCandidates()) {
            Collection<Answer> listAnswers = answersDAO.findAllIsView(candidate, questionService.
                    getAllIsView(courseSettingService.getLastSetting().getId()));

            candidate.setAnswers(listAnswers);
            listCandidates.add(candidate);

        }
        return listCandidates;
    }

    @Override
    public Collection<Answer> getAnswersIsView(Candidate candidate, Collection<Question> listQuestions) {
        return answersDAO.findAllIsView(candidate, listQuestions);
    }
}

