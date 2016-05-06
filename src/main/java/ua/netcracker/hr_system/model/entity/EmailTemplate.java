package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Владимир on 28.04.2016.
 */
@Component
public class EmailTemplate implements Serializable {
    private int id;
    private String description;
    private String template;

    public EmailTemplate() {
    }

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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", template='" + template + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailTemplate that = (EmailTemplate) o;

        if (id != that.id) return false;
        if (!description.equals(that.description)) return false;
        if (!template.equals(that.template)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + description.hashCode();
        result = 31 * result + template.hashCode();
        return result;
    }
}
