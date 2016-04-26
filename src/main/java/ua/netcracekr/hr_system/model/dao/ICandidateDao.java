package ua.netcracekr.hr_system.model.dao;

import ua.netcracekr.hr_system.model.entity.Candidate;

import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface ICandidateDao extends IDao<Integer,Candidate>{



    Candidate findById(Integer id);
    Candidate findByName(String name);


}
