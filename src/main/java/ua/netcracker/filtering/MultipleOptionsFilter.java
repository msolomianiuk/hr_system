package ua.netcracker.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class MultipleOptionsFilter implements Filter{

    private int q_id;

    private ArrayList<String> answers;

    public MultipleOptionsFilter(int q_id, ArrayList<String> answers) {
        this.q_id = q_id;
        this.answers = answers;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        ArrayList<Candidate> result = new ArrayList<>();
        for (Candidate candidate : list) {
            Collection<Answer> allAnswers = candidate.getAnswers();
            ArrayList<Answer> neededAnswers = new ArrayList<>();
            ArrayList<String> specifiedAnswers = answers;
            for (Answer answer : allAnswers) {
                if (answer.getQuestionId() == q_id) {
                    neededAnswers.add(answer);
                }
            }
            if (neededAnswers.size() == answers.size()) {
                for (Answer neededAnswer : neededAnswers) {
                    for (String answer : specifiedAnswers) {
                        if (neededAnswer.getValue().equals(answer)) {
                            neededAnswers.remove(neededAnswer);
                            specifiedAnswers.remove(answer);
                        }
                    }
                }
                if ((neededAnswers.size() + specifiedAnswers.size()) == 0) {
                    result.add(candidate);
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

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
