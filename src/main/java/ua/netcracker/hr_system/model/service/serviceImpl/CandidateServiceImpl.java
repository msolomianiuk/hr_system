package ua.netcracker.hr_system.model.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoImpl.CandidateDAOImpl;
import ua.netcracker.hr_system.model.entity.Candidate;

/**
 * Created by Legion on 03.05.2016.
 */
@Service()
public class CandidateServiceImpl {

    @Autowired(required = false)
    private CandidateDAOImpl candidateDAO;

    public Candidate getCandidate (int id){
        return candidateDAO.findById(id);
    }

}
