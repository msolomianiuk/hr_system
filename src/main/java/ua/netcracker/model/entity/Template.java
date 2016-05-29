package ua.netcracker.model.entity;

public enum Template {

    TEMPLATE_SUCCESS_REGISTRATION(1, "TEMPLATE_SUCCESS_REGISTRATION"),
    TEMPLATE_COMING_INTERVIEW(2, "TEMPLATE_COMING_INTERVIEW"),
    TEMPLATE_INVITE_ON_INTERVIEW(3, "TEMPLATE_INVITE_ON_INTERVIEW"),
    TEMPLATE_INTERVIEW_PASSED(4, "TEMPLATE_INTERVIEW_PASSED"),
    TEMPLATE_JOB_ACCEPTED(5, "TEMPLATE_JOB_ACCEPTED"),
    TEMPLATE_NO_INTERVIEW(6, "TEMPLATE_NO_INTERVIEW"),
    TEMPLATE_REJECTED(7, "TEMPLATE_REJECTED"),
    TEMPLATE_RESTORE_PASSWORD(8, "TEMPLATE_RESTORE_PASSWORD");

    int id;
    String template;

    Template(int id, String template) {
        this.id = id;
        this.template = template;
    }

    public int getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return template;
    }
}
