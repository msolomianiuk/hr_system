package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.Question;

import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface QuestionDAO extends DAO<Question> {
    Collection<Question> findQuestions(String sql);

    Collection<Question> findAllMandatory();

    int findTypeIdByValue(String value);

    boolean delete(Question question);

    List<String> findAnswerVariants(Question question);


}
