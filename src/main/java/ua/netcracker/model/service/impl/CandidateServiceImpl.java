package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import ua.netcracker.model.utils.JsonParsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 */
@Service("Candidate Service")
public class CandidateServiceImpl implements CandidateService {

    private static final Logger LOGGER = Logger.getLogger(CandidateServiceImpl.class);

    private int userId;

    @Autowired
    private PaginationServiceImp paginationServiceImp;
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
    public Collection<Candidate> getCandidateByStatus(String status) {
        return candidateDAO.findCandidateByStatus(status);
    }

    @Override
    public Collection<Candidate> getAllCandidates() {
        return getPartCandidatesWithAnswer(null, null);
    }

    @Override
    public Collection<Candidate> getAllCandidatesIsView() {
        return getPartCandidatesIsViewWithAnswer(null, null);
    }

    @Override
    public Collection<Candidate> getPartCandidatesWithAnswer(Integer with, Integer to) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            listCandidates.addAll(candidateDAO.findPartByCourse(courseSettingService.
                    getLastSetting().getId(), with, to));
            for (Candidate candidate : listCandidates) {
                if (candidate.getUser() == null) {
                    candidate.setUser(userDAO.find(candidate.getUserId()));
                }
                if (candidate.getAnswers() == null) {
                    candidate.setAnswers(answersDAO.findAll(candidate.getId()));
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: getPartCandidatesWithAnswer" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> getPartCandidatesIsViewWithAnswer(Integer with, Integer to) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            for (Candidate candidate : getPartCandidatesWithAnswer(with, to)) {
                Collection<Answer> listAnswers = answersDAO.findAllIsView(candidate, questionService.
                        getAllIsView(courseSettingService.getLastSetting().getId()));

                candidate.setAnswers(listAnswers);
                listCandidates.add(candidate);

            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: getPartCandidatesIsViewWithAnswer" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> getAllByCourse(Integer courseId) {
        return candidateDAO.findAllByCourse(courseId);
    }

    @Override
    public Collection<Answer> getAnswersIsView(Candidate candidate, Collection<Question> listQuestions) {
        return answersDAO.findAllIsView(candidate, listQuestions);
    }

    @Override
    public Collection<Candidate> getPartByCourse(Integer courseId, Integer with, Integer to) {
        return candidateDAO.findPartByCourse(courseId, with, to);
    }

    @Override
    public Candidate getCandidateById(Integer id) throws NullPointerException {
        Candidate candidate = candidateDAO.findByCandidateId(id);
        candidate.setUser(userDAO.find(candidate.getUserId()));
        candidate.setInterviewResults(interviewResultDAO.findResultsByCandidateId(id));
        candidate.setAnswers(answersDAO.findAll(candidate.getId()));
        return candidate;
    }

    @Override
    public Integer getCandidateCount() {
        return candidateDAO.getCandidateCount(courseSettingService.getLastSetting().getId());
    }

    @Override
    public Integer getCandidateCountByInterviewId(Integer interviewId) {
        return candidateDAO.getCandidateCountByInterviewId(interviewId);
    }

    @Override
    public Candidate getCandidateByUserId(Integer userId) {
        return candidateDAO.findByUserId(userId);
    }


    @Override
    public Status getStatusById(Integer statusId) throws IllegalArgumentException {
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
    public boolean updateCandidate(Candidate candidate) {
        return candidateDAO.update(candidate);
    }

    @Override
    public Candidate saveAnswers(String answersJsonString) {
        Collection<Answer> listAnswers = JsonParsing.parseJsonString(answersJsonString);
        Candidate candidate = getCurrentCandidate();
        if (candidate.getId() == 0) {
            candidate.setUserId(userId);
            candidate.setUser(userDAO.find(candidate.getUserId()));
            candidate.setStatusId(Status.Ready.getId());
            candidate.setCourseId(courseSettingService.getLastSetting().getId());
            saveCandidate(candidate);
            try {
                candidate = getCandidateById(userId);
            } catch (NullPointerException e) {
                LOGGER.info("Method: saveAnswers " + e.getStackTrace()
                        + " Message: " + e.getMessage());
                LOGGER.debug(e.getStackTrace(), e);
            }
        }
        candidate.setAnswers(listAnswers);
        saveOrUpdateAnswers(candidate);
        return candidate;
    }

    @Override
    public Candidate getCurrentCandidate() {
        userId = 0;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserAuthenticationDetails userDetails =
                        (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                userId = userDetails.getUserId();
            }
        } catch (Exception e) {
            LOGGER.error("Method: getCurrentCandidate" + " Error: " + e);
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
        } catch (DataAccessException e) {
            LOGGER.error("Method: saveOrUpdateAnswers" + " Error: " + e);
        }
    }

    @Override
    public Collection<Answer> getAnswerByQuestionId(Candidate candidate, int questionId) {
        ArrayList<Answer> resultAnswer = new ArrayList<>();
        try {
            ArrayList<Answer> answers = (ArrayList<Answer>) getAllCandidateAnswers(candidate);
            for (Answer answer : answers) {
                if (answer.getQuestionId() == questionId) {
                    resultAnswer.add(answer);
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: getAnswerByQuestionId" + " Error: " + e);
        }
        return resultAnswer;
    }

    @Override
    public Map<Integer, String> getAllStatus() {
        Map<Integer, String> statusMap = new HashMap<>();
        try {
            statusMap = candidateDAO.findAllStatus();
        } catch (DataAccessException e) {
            LOGGER.error("Method: getAllStatus" + " Error: " + e);
        }
        return statusMap;
    }

    @Override
    public boolean updateCandidateStatus(Integer candidateID, Integer newStatusID) {
        return candidateDAO.updateCandidateStatus(candidateID, newStatusID);
    }

    @Override
    public Collection<Candidate> getCandidate(Integer limitRows, Integer fromElement, String find) {
        int element = 0;
        if ((fromElement - 1) != 0) {
            element = (fromElement - 1) * limitRows;
        }
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            listCandidates.addAll(paginationServiceImp.findForSearch(limitRows, element, find));
        } catch (DataAccessException e) {
            LOGGER.error("Method: getCandidate" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> getAllMarkedByCurrentInterviewer(User user) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            listCandidates.addAll(candidateDAO.getAllMarked(user));
        } catch (DataAccessException e) {
            LOGGER.error("Method: getAllMarkedByCurrentInterviewer" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> getPartCandidates(Integer with, Integer to) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            listCandidates.addAll(candidateDAO.findPart(with, to));
        } catch (DataAccessException e) {
            LOGGER.error("Method: getPartCandidates" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public boolean updateInterviewResult(Integer candidateId, InterviewResult interviewResult) {
        return interviewResultDAO.updateInterviewResult(candidateId, interviewResult);
    }


    @Override
    public boolean saveInterviewResult(Integer candidateId,
                                       Integer interviewerId,
                                       Integer mark,
                                       String recommendation,
                                       String comment
    ) {
        boolean flag = true;
        try {
            ArrayList<InterviewResult> interviewResults = (ArrayList<InterviewResult>) interviewResultDAO.
                    findResultsByCandidateId(candidateId);
            if (interviewResults.size() != 0) {
                for (int i = 0; i < interviewResults.size(); i++) {
                    flag = isPostInterviewResult(interviewResults.get(i).getInterviewer().getRoles(),
                            userDAO.find(interviewerId).getRoles());
                }
            }
            if (flag == true) {
                InterviewResult interviewResult = new InterviewResult();
                interviewResult.setComment(comment);
                interviewResult.setInterviewerId(interviewerId);
                interviewResult.setMark(mark);
                interviewResult.setRecommendation(Recommendation.valueOf(recommendation));
                return interviewResultDAO.createInterviewResult(candidateId, interviewResult);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: saveInterviewResult" + " Error: " + e);
        }
        return flag;
    }

    private boolean isPostInterviewResult(Collection<Role> rolesInterviewerFirst, Collection<Role> rolesInterviewerLast) {
        boolean flag = true;
        for (Role role : rolesInterviewerFirst) {
            switch (role) {
                case ROLE_BA: {
                    if (!rolesInterviewerLast.contains(Role.ROLE_BA)) {
                        if (!rolesInterviewerLast.contains(Role.ROLE_HR)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                    break;
                }
                case ROLE_HR: {
                    if (!rolesInterviewerLast.contains(Role.ROLE_HR)) {
                        if (!rolesInterviewerLast.contains(Role.ROLE_BA)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                    break;
                }
                case ROLE_DEV: {
                    if (!rolesInterviewerLast.contains(Role.ROLE_BA)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                    break;
                }
            }
        }
        return flag;
    }
}

