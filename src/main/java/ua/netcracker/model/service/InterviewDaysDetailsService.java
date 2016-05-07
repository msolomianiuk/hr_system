package ua.netcracker.model.service;

import ua.netcracker.model.entity.InterviewDaysDetails;

import javax.naming.NamingException;
import java.util.Collection;
import java.util.List;

/**
 * Created by MaXim on 05.05.2016.
 */
public interface InterviewDaysDetailsService {
    public InterviewDaysDetails findById(int id);
    public List<InterviewDaysDetails> findAll();
    public boolean add(InterviewDaysDetails interviewDaysDetails);
    public void update(InterviewDaysDetails interviewDaysDetails);
    public void remove(int id);
    public String getStartTimeofInterview(int id);
    public String getEndTimeofInterview(int id);
    public String getDateofInterview(int id);
    public int getIdbyDate(String date);
}
