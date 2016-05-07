package ua.netcracker.model.filtering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.CandidateService;

import java.util.ArrayList;

/**
 * Main filter - to run and apply all chosen filters
 */
@Service
public class Filterer {

    @Autowired
    CandidateService candidateService;

    private ArrayList<Filter> filters;
    private ArrayList<Candidate> list;

    public Filterer() {
    }

    public Filterer(ArrayList<Filter> filters) {
        this.filters = filters;
    }

    public ArrayList<Candidate> filter() {
        if (list == null || list.isEmpty()) {
            list = (ArrayList<Candidate>) candidateService.getAllCandidates();
        }
        for (Filter filter : filters) {
            list = filter.filter(list);
        }

        return list;
    }

    public ArrayList<Candidate> getList() {
        return list;
    }

    public void setList(ArrayList<Candidate> list) {
        this.list = list;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }
}
