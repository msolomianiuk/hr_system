package ua.netcracker.hr_system.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.impl.QuestionDAOImpl;
import ua.netcracker.hr_system.model.entity.Question;
import ua.netcracker.hr_system.model.service.QuestionService;

import java.util.Collection;

/**
 * Created by bohda on 05.05.2016.
 */
@Service()
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDAOImpl questionDao;

    @Override
    public Collection<Question> getAllMandatory() {
        return questionDao.findAllMandatory();
    }

    @Override
    public Collection<Question> getAll() {
        return questionDao.findAll();
    }

    @Override
    public Question get(int id) {
        return questionDao.find(id);
    }

    @Override
    public boolean save(Question question) {
        return questionDao.insert(question);
    }

    @Override
    public boolean update(Question question) {
        return questionDao.update(question);
    }

    @Override
    public boolean remove(Question question) {
        return questionDao.delete(question);
    }
}
