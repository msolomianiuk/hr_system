package ua.netcracker.hr_system.model.service.serviceInterface;

import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alyona Bilous 05/05/2016
 */
@Service
public interface CandidateService {


    Candidate getCandidateByID (Integer id);
    Candidate getCandidateByUserID(Integer userID);
    List<Candidate> getAllProfiles();

    String getStatusByID(Integer statusID);
    HashMap<Integer, Integer> getMarks(Integer candidateID);
    HashMap<Integer, String> getRecommendations(Integer id);
    HashMap<Integer, String> getResponses(Integer id);
    int getInterviewDayDetailsByID(Integer id);

    List<User> getInterviewers(Candidate candidate);
    String getCandidateAnswer(Integer candidateID,Integer questionID);
    Map<Integer, String> getAllCandidateAnswers(Candidate candidate);
    
    boolean saveCandidate(Candidate candidate);
    void saveAnswers(Candidate candidate);
    void deleteAnswers(Candidate candidate);
    void saveOrUpdate(Candidate candidate);
}
