package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;

/**
 * Created by MaXim on 01.05.2016.
 */
public interface InterviewDaysDetailsDAO extends DAO<Integer,InterviewDaysDetails> {
    @Override
    boolean insert(InterviewDaysDetails entity);

    @Override
    boolean update(InterviewDaysDetails entity);
}
