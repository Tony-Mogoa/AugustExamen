package august.examen.models;

import august.examen.db.DatabaseWrapper;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Question{
    private String questionId;
    private String examId;
    private String parentId;
    private int order;
    private String content;
    private String label;
    private boolean acceptImages;
    private ArrayList<File> photosAttached = new ArrayList<>();
    private boolean hasChildren;
    private boolean hasParent;
    private String parentLabel;

    private DatabaseWrapper databaseWrapper;

    public Question(String questionId, String examId, String parentId, String content, String label, boolean hasChildren, boolean hasParent, boolean acceptImages) {
        this.questionId = questionId;
        this.examId = examId;
        this.parentId = parentId;
        this.content = content;
        this.label = label;
        this.acceptImages = acceptImages;
        this.hasChildren = hasChildren;
        this.hasParent = hasParent;
    }

    public Question(DatabaseWrapper databaseWrapper){
        UUID uniqueKey = UUID.randomUUID();
        this.questionId = uniqueKey.toString();
        this.databaseWrapper = databaseWrapper;
    }

    public Question(boolean empty){

    }

    public boolean saveQuestion(){
        String sqlSave = "INSERT INTO question (exam_id, q_parent_id, q_content, q_label, q_accepts_images, q_has_children, q_has_parent, q_order, q_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pStmt = databaseWrapper.getConnection().prepareStatement(sqlSave);
            pStmt.setString(1, examId);
            pStmt.setString(2, parentId);
            pStmt.setString(3, content);
            pStmt.setString(4, label);
            pStmt.setBoolean(5, acceptImages);
            pStmt.setBoolean(6, hasChildren);
            pStmt.setBoolean(7, hasParent);
            pStmt.setInt(8, order);
            pStmt.setString(9, questionId);
            return pStmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<File> getPhotosAttached() {
        return photosAttached;
    }

    public void setPhotosAttached(ArrayList<File> photosAttached) {
        this.photosAttached = photosAttached;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isAcceptImages() {
        return acceptImages;
    }

    public void setAcceptImages(boolean acceptImages) {
        this.acceptImages = acceptImages;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.parentLabel = parentLabel;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", examId='" + examId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", order=" + order +
                ", content='" + content + '\'' +
                ", label='" + label + '\'' +
                ", acceptImages=" + acceptImages +
                ", hasChildren=" + hasChildren +
                ", hasParent=" + hasParent +
                '}';
    }
}
