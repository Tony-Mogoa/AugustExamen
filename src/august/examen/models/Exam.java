package august.examen.models;

import august.examen.db.DatabaseWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Exam {
    private String examId;
    private int hours;
    private int minutes;
    private String faculty;
    private String course;
    private String unit;
    private String examType;
    public DatabaseWrapper databaseWrapper;
    Vector<Question> questions = new Vector<>();

    public Exam(String examId, int durationInSeconds, String faculty, String course, String unit, String examType, DatabaseWrapper databaseWrapper) {
        this.examId = examId;
        this.hours = durationInSeconds;
        this.faculty = faculty;
        this.course = course;
        this.unit = unit;
        this.examType = examType;
        this.databaseWrapper = databaseWrapper;
    }

    public Exam(DatabaseWrapper databaseWrapper) {
        this.databaseWrapper = databaseWrapper;
    }

    public boolean saveExam(){
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

    public Vector<Question> getExam(String examId){
        String examSQL = "SELECT * FROM question WHERE exam_id = ? ORDER BY order";
        try {
            PreparedStatement examStmt = databaseWrapper.getConnection().prepareStatement(examSQL);
            examStmt.setString(1, examId);
            ResultSet rs = examStmt.executeQuery();
            while (rs.next()){

                Question question = new Question(true);//Populate question object
                question.setQuestionId(rs.getString("q_id"));
                question.setExamId(rs.getString("exam_id"));
                question.setParentId(rs.getString("q_parent_id"));
                question.setContent(rs.getString("q_content"));
                question.setLabel(rs.getString("q_label"));
                question.setAcceptImages(rs.getBoolean("q_accepts_images"));
                question.setHasChildren(rs.getBoolean("q_has_children"));
                question.setHasParent(rs.getBoolean("q_has_parent"));

                questions.add(question);
                getChildQuestions(question);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }

    public void getChildQuestions(Question question){
        if(!question.isHasChildren()){
            return;
        }
        else {
            String examSQL = "SELECT * FROM question WHERE exam_id = ? AND parent_id = ?";
            try {
                PreparedStatement examStmt = databaseWrapper.getConnection().prepareStatement(examSQL);
                examStmt.setString(1, question.getExamId());
                examStmt.setString(2, question.getParentId());
                ResultSet rs = examStmt.executeQuery();
                while (rs.next()){

                    Question subQuestion = new Question(true);//Populate question object
                    subQuestion.setQuestionId(rs.getString("q_id"));
                    subQuestion.setExamId(rs.getString("exam_id"));
                    subQuestion.setParentId(rs.getString("q_parent_id"));
                    subQuestion.setContent(rs.getString("q_content"));
                    subQuestion.setLabel(rs.getString("q_label"));
                    subQuestion.setAcceptImages(rs.getBoolean("q_accepts_images"));
                    subQuestion.setHasChildren(rs.getBoolean("q_has_children"));
                    subQuestion.setHasParent(rs.getBoolean("q_has_parent"));

                    questions.add(subQuestion);
                    getChildQuestions(subQuestion);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }
}
