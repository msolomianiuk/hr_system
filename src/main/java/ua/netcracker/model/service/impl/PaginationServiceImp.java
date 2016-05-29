package ua.netcracker.model.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.*;

/**
 * Created by Legion on 08.05.2016.
 */
@Service
public class PaginationServiceImp {

    @Autowired
    private CandidateDAO candidateDAO;


    public Long getRows(List<Answer> expected) {
        return candidateDAO.getRows(expected);
    }

    public long rowsFind(String find) {
        return candidateDAO.rowsFind(find);
    }

    public Collection<Candidate> findForSearch(Integer elementPage, Integer fromElement, String find) {
        return candidateDAO.findForSearch(elementPage, fromElement, find);
    }

    public Collection<Candidate> filterCandidates(List<Answer> expected, Integer limit, Integer element) {

        int offset = 0;
        if ((element - 1) != 0) {
            offset = (element - 1) * limit;
        }

        return candidateDAO.filtration(expected, limit, offset);
    }

    public Collection<Candidate> pagination(Integer limitRows, Integer fromElement) {
        int element = 0;
        if ((fromElement - 1) != 0) {
            element = (fromElement - 1) * limitRows;
        }
        return candidateDAO.paginationCandidates(limitRows, element);
    }

    public List paginationList(List entity, String nextPage, PagedListHolder pagedListHolder) {

        ArrayList list = (ArrayList) entity;

        Comparator<Candidate> comp = new Comparator<Candidate>() {
            @Override
            public int compare(Candidate p1, Candidate p2) {
                return p1.getUser().getName().compareTo(p2.getUser().getName());
            }
        };

        Collections.sort(list, comp);

        pagedListHolder.setSource(list);
        pagedListHolder.getSort();
        if (nextPage.equals("next")) {
            pagedListHolder.nextPage();
        } else if (nextPage.equals("previous")) {
            pagedListHolder.previousPage();
        } else {
            pagedListHolder.setPage(Integer.parseInt(nextPage));
        }

        return pagedListHolder.getPageList();

    }


}