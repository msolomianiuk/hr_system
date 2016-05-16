package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.*;

public class OrderedFilter implements Filter{

    private int q_id;

    /**
     * ascending or descending order
     */
    private boolean ascendingOrder;

    public OrderedFilter() {
    }

    @Override
    public ArrayList<Candidate> filter(List<Candidate> list) {

        Collections.sort(list, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                String answer1 = "";
                String answer2 = "";
                Collection<Answer> answers = o1.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        answer1 = answer.getValue();
                    }
                }
                answers = o2.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        answer2 = answer.getValue();
                    }
                }
                try {
                    int a = Integer.parseInt(answer1);
                    int b = Integer.parseInt(answer2);
                    return a - b;
                } catch (NumberFormatException e) {
                    return answer1.compareTo(answer2);
                }
            }
        });

        if (ascendingOrder) {
            Collections.reverse(list);
        }

        return (ArrayList<Candidate>) list;
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
