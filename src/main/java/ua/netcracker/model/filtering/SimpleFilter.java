package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.List;

public class SimpleFilter implements Filter {

    private List<Answer> expected;

    public List<Answer> getExpected() {
        return expected;
    }

    public void setExpected(List<Answer> expected) {
        this.expected = expected;
    }

    @Override
    public ArrayList<Candidate> filter(List<Candidate> list) {
        ArrayList<Candidate> result = new ArrayList<>();
        for (Candidate candidate : list) {
            List<Answer> answers = (List<Answer>) candidate.getAnswers();

            List<Answer> filledAnswers = new ArrayList<>();

            for (Answer answer : answers) {
                if (!answer.getValue().isEmpty()) {
                    filledAnswers.add(answer);
                }
            }

            if (filledAnswers.containsAll(expected)) {
                result.add(candidate);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("" != null);
    }
}
