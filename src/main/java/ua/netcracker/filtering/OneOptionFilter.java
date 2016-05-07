package ua.netcracker.filtering;

import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * filter for answers with one option
 */
@Service
public class OneOptionFilter implements Filter{

    private int q_id;

    private String value;

    public OneOptionFilter(int q_id, String value) {
        this.q_id = q_id;
        this.value = value;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        ArrayList<Candidate> result = new ArrayList<>();
        for (Candidate candidate : list) {
            Collection<Answer> answers = candidate.getAnswers();
            for (Answer answer : answers) {
                if (answer.getQuestionId().equals(q_id)) {
                    if (answer.getValue().toLowerCase().contains(value.toLowerCase())) {
                        result.add(candidate);
                    }
                }
            }
        }
        return result;
    }

    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
