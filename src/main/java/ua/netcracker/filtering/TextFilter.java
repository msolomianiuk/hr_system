package ua.netcracker.filtering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TextFilter implements Filter{

    @Autowired
    QuestionService questionService;

    private int q_id;

    private String value;

    public TextFilter(int q_id, String value) {
        this.q_id = q_id;
        this.value = value;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        for (Candidate candidate : list) {
            Collection<Answer> answers = candidate.getAnswers();
            for (Answer answer : answers) {
                if (answer.getQuestionId().equals(q_id)) {
                    if (!(answer.getValue().toString().toLowerCase().contains(value.toLowerCase()))) {
                        list.remove(candidate);
                    }
                }
            }
        }
        return list;
    }
}
