package ua.netcracker.model.service.impl;


import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Legion on 08.05.2016.
 */
@Service
@Scope("prototype")
public class Pagination {

    private PagedListHolder pagedListHolder = new PagedListHolder();
    private List pageList=null;

    public List paginationList(List entity, int size, String nextPage) {

        pageList = entity;

        if (Integer.valueOf(size) == 0) {
            size = 10;
        }

        pagedListHolder.setPageSize(size);
        pagedListHolder.setSource(pageList);

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
