package ua.netcracker.model.filtering;

import ua.netcracker.model.entity.Candidate;

import java.util.ArrayList;
import java.util.List;

public interface Filter {
    ArrayList<Candidate> filter(List<Candidate> list);
}
