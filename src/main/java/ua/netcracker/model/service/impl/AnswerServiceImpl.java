package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.impl.AnswersDAOImpl;

import java.util.Collection;

/**
 * Created by Legion on 11.05.2016.
 */
@Service
public class AnswerServiceImpl{

    @Autowired
    public AnswersDAOImpl answersDAO;

    public Collection getAnswerCandidate (int id){
        return   answersDAO.getCandidateAnswer(id);
    }

}