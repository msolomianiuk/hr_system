package ua.netcracker.filtering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.CandidateService;

import java.util.ArrayList;

@Service
public class OrderedNumberFilter implements Filter{

    @Autowired
    CandidateService candidateService;

    private int q_id;

    /**
     * asc or desc order
     */
    private boolean ascendingOrder;

    public OrderedNumberFilter(int q_id, boolean ascendingOrder) {
        this.q_id = q_id;
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    public ArrayList<Candidate> filter(ArrayList<Candidate> list) {
   /*     Collections.sort(list, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                int answer1 = 0;
                int answer2 = 0;
                Collection<Answer> answers = o1.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        Answer answerInt = (Answer) answer;
                        answer1 = answer.getValue();
                    }
                }
                answers = o2.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getQuestionId().equals(q_id)) {
                        Answer answerInt = (Answer) answer;
                        answer2 = answerInt.getValue();
                    }
                }
                return answer1 - answer2;
            }
        });

        if (ascendingOrder) {
            Collections.reverse(list);
        }*/

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
