package ua.netcracker.filtering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ComboboxFilter implements Filter {

    @Autowired
    QuestionService questionService;

    private int q_id;

    private int q_addition_id;

    public ComboboxFilter(int q_id, int q_addition_id) {
        this.q_id = q_id;
        this.q_addition_id = q_addition_id;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        for (Candidate candidate : list) {
            Collection<Answer> answers = candidate.getAnswers();//key - question id, value - question addition id
            for (Answer answer : answers) {
                if (answer.getQuestionId().equals(q_id)) {
                    if (!(answer.getValue().equals(q_addition_id))) {
                        list.remove(candidate);
                    }
                }
            }
        }
        return list;
    }
}
