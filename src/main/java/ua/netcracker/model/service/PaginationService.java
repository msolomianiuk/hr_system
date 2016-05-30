package ua.netcracker.model.service;

import org.springframework.beans.support.PagedListHolder;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 30.05.2016.
 */
public interface PaginationService {
    Long getRows(List<Answer> expected) throws NullPointerException;

    long rowsFind(String find);

    Collection<Candidate> findForSearch(Integer elementPage, Integer fromElement, String find);

    Collection<Candidate> filterCandidates(List<Answer> expected, Integer limit, Integer element);

    Collection<Candidate> pagination(Integer limitRows, Integer fromElement);

    Collection paginationList(List entity, String nextPage, PagedListHolder pagedListHolder);
}
