package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class OrderedNumberFilter implements Filter{

    private int q_id;

    /**
     * ascending or descending order
     */
    private boolean ascendingOrder;

    public OrderedNumberFilter(int q_id, boolean ascendingOrder) {
        this.q_id = q_id;
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
        Collections.sort(list, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                int answer1 = 0;
                int answer2 = 0;
                Collection<Answer> answers = o1.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        answer1 = Integer.parseInt(answer.getValue());
                    }
                }
                answers = o2.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        answer2 = Integer.parseInt(answer.getValue());
                    }
                }
                return answer1 - answer2;
            }
        });

        if (ascendingOrder) {
            Collections.reverse(list);
        }

        return list;
    }


    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public boolean isAscendingOrder() {
        return ascendingOrder;
    }

    public void setAscendingOrder(boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }
}
