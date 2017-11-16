package ohtu;

public class Submission {
    private int week;
    private int hours;
    private String[] exercises;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String[] getExercises() {
        return exercises;
    }

    public void setExercises(String[] exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return ""+week;
    }
    
}