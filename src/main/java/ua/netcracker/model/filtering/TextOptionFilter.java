package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * filter for questions with one or multiple text options (checkbox, combobox, text)
 */
public class TextOptionFilter implements Filter{

    private int q_id;

    private ArrayList<String> stringAnswers;

    public TextOptionFilter() {
    }

    public TextOptionFilter(int q_id, ArrayList<String> stringAnswers) {
        this.q_id = q_id;
        this.stringAnswers = stringAnswers;
    }

    public void addOptionToAnswers(String option) {
        stringAnswers.add(option);
    }

    @Override
    public ArrayList<Candidate> filter(List<Candidate> list) {
        if (stringAnswers.size() == 1) {
            String value = stringAnswers.get(0);
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

        ArrayList<Candidate> result = new ArrayList<>();
        for (Candidate candidate : list) {
            Collection<Answer> allAnswers = candidate.getAnswers();
            ArrayList<Answer> neededAnswers = new ArrayList<>(); //answers to check if
            // they are the same as specified in filter

            ArrayList<String> specifiedAnswers = stringAnswers;
            for (Answer answer : allAnswers) {
                if (answer.getQuestionId() == q_id) {
                    neededAnswers.add(answer);
                }
            }
            if (neededAnswers.size() == stringAnswers.size()) {
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

    public ArrayList<String> getStringAnswers() {
        return stringAnswers;
    }

    public void setStringAnswers(ArrayList<String> stringAnswers) {
        this.stringAnswers = stringAnswers;
    }
}
