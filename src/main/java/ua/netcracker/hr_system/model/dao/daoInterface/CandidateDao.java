package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.Candidate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface CandidateDao extends Dao<Integer, Candidate> {


    List<Candidate> getAllProfileById(int roleId);

    HashMap getProfileById();

    Candidate findById(Integer id);

    Candidate findByName(String name);

    public int findByStatusId(Integer statusId);


}
