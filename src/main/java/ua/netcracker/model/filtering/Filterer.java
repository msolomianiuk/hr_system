package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;

/**
 * Main filter - to run and apply all chosen filters
 */
public class Filterer {

    private ArrayList<Filter> filters;
    private ArrayList<Candidate> list;

    public Filterer() {
    }

    public Filterer(ArrayList<Filter> filters) {
        this.filters = filters;
    }

    public ArrayList<Candidate> filter() {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
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
