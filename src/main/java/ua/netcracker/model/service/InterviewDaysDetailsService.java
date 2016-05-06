package ua.netcracker.model.service;

import ua.netcracker.model.entity.InterviewDaysDetails;

/**
 * Created by MaXim on 05.05.2016.
 */
public interface InterviewDaysDetailsService extends EntityService<InterviewDaysDetails>{
    public String getStartTimeofInterview();
    public String getEndTimeofInterview();
    public String getDateofInterview();
    public boolean insert(InterviewDaysDetails interviewDaysDetails);
}
