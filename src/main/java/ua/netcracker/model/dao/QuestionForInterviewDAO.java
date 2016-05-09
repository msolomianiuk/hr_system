package ua.netcracker.model.dao;


import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.entity.SubjectQuestionForInterview;

import java.util.Collection;

/**
 * Created by bohda on 09.05.2016.
 */
public interface QuestionForInterviewDAO extends DAO<QuestionForInterview> {


    Collection findAllQuestionBySubject(int subjectId);


    Collection findAll(int roleId);
    Collection findAllSubject();

    boolean insertSubject(SubjectQuestionForInterview subject);

    boolean deleteSubject(SubjectQuestionForInterview subject);

    boolean delete(QuestionForInterview questionForInterview);

    boolean updateSubject(SubjectQuestionForInterview subjectQuestionForInterview);


}
