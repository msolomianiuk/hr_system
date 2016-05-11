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
    Collection getAllSubjectAndQuestion();

    QuestionForInterview get(int id);

    Collection getAllSubject();

    boolean setQuestion(Integer subjectId, String questionValue);

    boolean updateQuestion(Integer questionId, String questionValue, Integer subjectId);

    Collection getAllQuestionBySubject(int subjectId);

    boolean setSubject(SubjectQuestionForInterview subject);

    boolean removeSubject(SubjectQuestionForInterview subject);

    boolean remove(Integer questionId);

    boolean updateSubject(SubjectQuestionForInterview subject);
}
