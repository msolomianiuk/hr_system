package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.QuestionForInterviewDAO;
import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.entity.SubjectQuestionForInterview;
import ua.netcracker.model.service.QuestionForInterviewService;

import java.util.Collection;

/**
 * Created by bohda on 09.05.2016.
 */
@Service
public class QuestionForInterviewServiceImpl implements QuestionForInterviewService {
    @Autowired
    QuestionForInterviewDAO questionForInterviewDAO;
    @Override
    public Collection getAllSubjectAndQuestion() {
        return questionForInterviewDAO.findAll();
    }

    @Override
    public QuestionForInterview get(int id) {
        return questionForInterviewDAO.find(id);
    }

    @Override
    public Collection getAllSubject() {
        return questionForInterviewDAO.findAllSubject();
    }

    @Override
    public boolean setQuestion(Integer subjectId,
                               String questionValue
    ) {
        QuestionForInterview questionForInterview = new QuestionForInterview();
        questionForInterview.setSubjectId(subjectId);
        questionForInterview.setValue(questionValue);
        return questionForInterviewDAO.insert(questionForInterview);
    }

    @Override
    public boolean updateQuestion( Integer questionId,
                                   String questionValue,
                                   Integer subjectId
    ){
        QuestionForInterview questionForInterview = new QuestionForInterview();
        questionForInterview.setSubjectId(subjectId);
        questionForInterview.setValue(questionValue);
        questionForInterview.setId(questionId);
        return questionForInterviewDAO.update(questionForInterview);
    }

    @Override
    public Collection getAllQuestionBySubject(int subjectId) {
        return questionForInterviewDAO.findAllQuestionBySubject(subjectId);
    }

    @Override
    public boolean setSubject(SubjectQuestionForInterview subject) {
        return questionForInterviewDAO.insertSubject(subject);
    }

    @Override
    public boolean removeSubject(SubjectQuestionForInterview subject) {
        return questionForInterviewDAO.deleteSubject(subject);
    }

    @Override
    public boolean remove(Integer questionId) {
        return questionForInterviewDAO.delete(questionId);
    }

    @Override
    public boolean updateSubject(SubjectQuestionForInterview subject) {
        return questionForInterviewDAO.updateSubject(subject);
    }
}
