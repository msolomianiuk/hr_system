package ua.netcracker.hr_system.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.impl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;
import ua.netcracker.hr_system.model.service.InterviewDaysDetailsService;

import java.util.List;

/**
 * Created by MaXim on 05.05.2016.
 */
@Service("Interview Service")
public class InterviewDaysDetailsServiceImpl implements InterviewDaysDetailsService {
    @Autowired
    InterviewDaysDetailsDAOImpl interviewDaysDetailsDAO;

    InterviewDaysDetails interviewDaysDetails;

    @Autowired(required = false)
    private void setInterviewDaysDetails(InterviewDaysDetails interviewDaysDetails){
        this.interviewDaysDetails = interviewDaysDetails;
    }

    @Autowired(required = false)
    private void setInterviewDaysDetailsDAO(InterviewDaysDetailsDAOImpl interviewDaysDetailsDAO){
        this.interviewDaysDetailsDAO = interviewDaysDetailsDAO;
    }

    @Override
    public boolean insert(InterviewDaysDetails interviewDaysDetails){
        return interviewDaysDetailsDAO.insert(interviewDaysDetails);
    }

    @Override
    public InterviewDaysDetails findById(int id) {
        return null;
    }

    @Override
    public List<InterviewDaysDetails> findAllSetting() {
        return interviewDaysDetailsDAO.findAll();
    }

    @Override
    public void saveOrUpdate(InterviewDaysDetails interviewDaysDetails) {
//        if (interviewDaysDetails.getId() > 0) {
//            interviewDaysDetailsDAO.insert(interviewDaysDetails);
//        } else {
            interviewDaysDetailsDAO.update(interviewDaysDetails);
//        }
    }

    @Override
    public void delete(long id) {
        interviewDaysDetailsDAO.remove(id);
    }

    @Override
    public String getStartTimeofInterview() {
        return interviewDaysDetailsDAO.getStartTimeInterview(this.interviewDaysDetails);
    }

    @Override
    public String getEndTimeofInterview() {
        return interviewDaysDetailsDAO.getEndTimeInterview(this.interviewDaysDetails);
    }

    @Override
    public String getDateofInterview() {
        return interviewDaysDetailsDAO.getStartTimeInterview(this.interviewDaysDetails);
    }
}
