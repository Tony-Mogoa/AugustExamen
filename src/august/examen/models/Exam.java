package august.examen.models;

import august.examen.db.DatabaseWrapper;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
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
        UUID uniqueKey = UUID.randomUUID();
        this.examId = uniqueKey.toString();
    }

    public Exam(DatabaseWrapper databaseWrapper, boolean empty){
        this.databaseWrapper = databaseWrapper;
    }

    public boolean getExam(String examId){
        String querySQL = "SELECT * FROM exam WHERE exam_id = ?";
        try {
            PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(querySQL);
            pstmt.setString(1, examId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                setExamId(rs.getString("exam_id"));
                setFaculty(rs.getString("exam_faculty"));
                setCourse(rs.getString("exam_unit"));
                setUnit(rs.getString("exam_unit"));
                setDurationMembers(rs.getInt("exam_duration"));
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean saveExam(){
        String addSQL = "INSERT INTO exam (exam_faculty, exam_course, exam_unit, exam_type, exam_duration, exam_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = databaseWrapper.getConnection().prepareStatement(addSQL);
            pstmt.setString(1, faculty);
            pstmt.setString(2, course);
            pstmt.setString(3, unit);
            pstmt.setString(4, examType);
            pstmt.setInt(5, getDurationInMinutes());
            pstmt.setString(6, examId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Vector<Question> getExamQuestions(){
        String examSQL = "SELECT * FROM question WHERE exam_id = ? AND q_has_parent = ? ORDER BY q_order";
        try {
            PreparedStatement examStmt = databaseWrapper.getConnection().prepareStatement(examSQL);
            examStmt.setString(1, examId);
            examStmt.setBoolean(2, false);
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
            return questions;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        //System.out.println("Getting questions");

    }

    public void getChildQuestions(Question question){
        if(!question.isHasChildren()){
            return;
        }
        else {
            String examSQL = "SELECT * FROM question WHERE exam_id = ? AND q_parent_id = ? ORDER BY q_order";
            try {
                PreparedStatement examStmt = databaseWrapper.getConnection().prepareStatement(examSQL);
                examStmt.setString(1, question.getExamId());
                examStmt.setString(2, question.getQuestionId());
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

    public void setDurationMembers(int duration){
        setHours(duration / 60);
        setMinutes(duration % 60);
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
