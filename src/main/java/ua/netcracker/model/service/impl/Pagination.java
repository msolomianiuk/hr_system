package ua.netcracker.model.service.impl;


import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Candidate;

import java.util.*;

/**
 * Created by Legion on 08.05.2016.
 */
@Service
public class Pagination {

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