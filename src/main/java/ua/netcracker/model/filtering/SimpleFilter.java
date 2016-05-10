package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleFilter implements Filter {

    private List<Answer> answerList;

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public ArrayList<Candidate> filter(List<Candidate> list) {
        ArrayList<Candidate> result = new ArrayList<>();
        for (Candidate candidate : list) {
            Collection<Answer> answers = candidate.getAnswers();

            if (answers.containsAll(answerList)) {
                result.add(candidate);
            }
        }
        return result;
    }

}
