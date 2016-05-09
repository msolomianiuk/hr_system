package ua.netcracker.model.service;

import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.entity.SubjectQuestionForInterview;

import java.util.Collection;

/**
 * Created by bohda on 09.05.2016.
 */
@Service
public interface QuestionForInterviewService {
    Collection getAllSubjectAndQuestionByRole(int roleId);

    QuestionForInterview get(int id);

    Collection getAllSubject();

    boolean setQuestion(QuestionForInterview questionForInterview);

    boolean updateQuestion(QuestionForInterview questionForInterview);

    Collection getAllQuestionBySubject(int subjectId);

    boolean setSubject(SubjectQuestionForInterview subject);

    boolean removeSubject(SubjectQuestionForInterview subject);

    boolean remove(QuestionForInterview questionForInterview);

    boolean updateSubject(SubjectQuestionForInterview subject);
}
