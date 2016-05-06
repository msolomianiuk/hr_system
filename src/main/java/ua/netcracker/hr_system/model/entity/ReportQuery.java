package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by Владимир on 28.04.2016.
 */
@Component
public class ReportQuery {
    private int id;
    private String description;
    private String query;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportQuery that = (ReportQuery) o;

        if (id != that.id) return false;
        if (!description.equals(that.description)) return false;
        if (!query.equals(that.query)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + description.hashCode();
        result = 31 * result + query.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReportQuery{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}
