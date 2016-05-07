package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.impl.QuestionDAOImpl;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bohda on 05.05.2016.
 */
@Service()
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDAOImpl questionDao;

    @Override
    public Collection<Question> getAllMandatory(int courseId) {
        return questionDao.findAllMandatory(courseId);
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

    @Override
    public void setQuestion(Question question) {
        questionDao.insert(question);
    }

    @Override
    public int getCourseId() {
        return questionDao.findCourseId();
    }

    @Override
    public int getQuantityQuestions() {
        return  questionDao.findQuantityQuestions();
    }

    @Override
    public List<Question> getTypeOfQuestion() {
        return questionDao.findType();
    }

    @Override
    public List<String> parseListJson(List<String> list) {
        List<String> parseList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {

            parseList.add(list.get(i).replaceAll("[ \" \\]\\[  ]", ""));

        }

        return parseList;
    }
}
