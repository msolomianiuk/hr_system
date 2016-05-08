package ua.netcracker.model.dao;


import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.Collection;

/**
 * Created by Alex on 06.05.2016.
 */
public interface AnswersDAO extends DAO<Candidate> {

    Collection<Answer> findAll(int candidateId);

    void saveAll(Candidate candidate);

    void deleteAnswers(int candidateId);
}
