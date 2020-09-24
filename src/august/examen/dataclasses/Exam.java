package august.examen.dataclasses;

public class Exam {
    private int id;
    private int durationInSeconds;
    private String faculty;
    private String school;
    private String course;
    private String unit;
    private String examType;

    public Exam(int id, int durationInSeconds, String faculty, String school, String course, String unit, String examType) {
        this.id = id;
        this.durationInSeconds = durationInSeconds;
        this.faculty = faculty;
        this.school = school;
        this.course = course;
        this.unit = unit;
        this.examType = examType;
    }

    public Exam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
