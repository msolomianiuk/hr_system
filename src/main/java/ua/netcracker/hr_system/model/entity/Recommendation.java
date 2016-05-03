package ua.netcracker.hr_system.model.entity;

public enum Recommendation {
        TOWORK(1, "TOWORK"), TOCOURSE(2, "TOCOURSE"), REFUSE(3, "REFUSE");

        int id;
        String recommendation;

        Recommendation(int id, String recommendation) {
            this.id = id;
            this.recommendation = recommendation;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return recommendation;
        }
}
