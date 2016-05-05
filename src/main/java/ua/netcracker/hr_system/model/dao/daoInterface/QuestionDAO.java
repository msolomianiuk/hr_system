package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.Question;

import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
public interface QuestionDAO extends DAO<Question> {
    List<String> findVariantsAnswer(int questionId);
}
