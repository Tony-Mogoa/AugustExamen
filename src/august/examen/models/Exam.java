package august.examen.models;

import august.examen.db.DatabaseWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Exam {
    private String examId;
    private int hours;
    private int minutes;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    private String faculty;
    private String course;
    private String unit;
    private String examType;
    private DatabaseWrapper databaseWrapper;
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Exam(String examId, int durationInSeconds, String faculty, String course, String unit, String examType, DatabaseWrapper databaseWrapper) {
        this.examId = examId;
        this.hours = durationInSeconds;
        this.faculty = faculty;
        this.course = course;
        this.unit = unit;
        this.examType = examType;
        this.databaseWrapper = databaseWrapper;
    }

    public Exam() {

    }

    public boolean addNewExam(){
        String addSQL = "INSERT INTO exam (exam_faculty, exam_course, exam_unit, exam_type, exam_duration) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(addSQL);
            pstmt.setString(1, faculty);
            pstmt.setString(2, course);
            pstmt.setString(3, unit);
            pstmt.setString(4, examType);
            pstmt.setInt(5, getDurationInMinutes());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public int getDurationInMinutes(){
        return hours * 60 + minutes;
    }
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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
