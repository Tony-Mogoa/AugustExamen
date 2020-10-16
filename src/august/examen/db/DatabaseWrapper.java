package august.examen.db;

import august.examen.models.Question;

import java.sql.*;
import java.util.Vector;

public class DatabaseWrapper extends Thread{
    private final String connectionUrl = "jdbc:mysql://localhost:3306/august_examen?zeroDateTimeBehavior=convertToNull";
    private Connection connection;
    Vector<Question> questions = new Vector<>();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseWrapper() {
        try {
            connection = DriverManager.getConnection(connectionUrl, "root", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Vector<Question> getExam(String examId){
        String examSQL = "SELECT * FROM question WHERE exam_id = ? ORDER BY order";
        try {
            PreparedStatement examStmt = connection.prepareStatement(examSQL);
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
                PreparedStatement examStmt = connection.prepareStatement(examSQL);
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
}
