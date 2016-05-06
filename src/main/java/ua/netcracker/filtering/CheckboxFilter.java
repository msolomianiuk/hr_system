package ua.netcracker.filtering;

import ua.netcracker.model.entity.AnswerList;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;

public class CheckboxFilter implements Filter{

    private int q_id;

    private ArrayList<String> answers;

    public CheckboxFilter(int q_id, ArrayList<String> answers) {
        this.q_id = q_id;
        this.answers = answers;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        AnswerList answerList = new AnswerList();
        answerList.setValue(answers);
        for (Candidate candidate : list) {
            if (!(candidate.getAnswers().contains(answerList))) {
                list.remove(candidate);
            }
        }
        return list;
    }
}
