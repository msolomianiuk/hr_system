package ua.netcracker.model.dao;

import ua.netcracker.model.entity.InterviewDaysDetails;

/**
 * Created by MaXim on 01.05.2016.
 */
public interface InterviewDaysDetailsDAO extends DAO<InterviewDaysDetails> {
    boolean remove(int id);

    boolean insertDate(InterviewDaysDetails interviewDaysDetails);

    InterviewDaysDetails findByDate(String Date);

    int getIdbyDate(String date);
}
