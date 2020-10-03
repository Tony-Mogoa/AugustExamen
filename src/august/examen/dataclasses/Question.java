package august.examen.dataclasses;

import javafx.scene.layout.VBox;

import java.io.File;

public class Question extends VBox {
    private int questionId;
    private int examId;
    private int parentId;
    private int order;
    private String content;
    private String label;
    private boolean acceptImages;
    private File[] photosAttached;
    private boolean hasChildren;
    private boolean hasParent;

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

    public Question(int questionId, int examId, int parentId, String content, String label, boolean hasChildren, boolean hasParent, boolean acceptImages) {
        this.questionId = questionId;
        this.examId = examId;
        this.parentId = parentId;
        this.content = content;
        this.label = label;
        this.acceptImages = acceptImages;
        this.hasChildren = hasChildren;
        this.hasParent = hasParent;
    }
    public  Question(){

    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
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

    public File[] getPhotosAttached() {
        return photosAttached;
    }

    public void setPhotosAttached(File[] photosAttached) {
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
}
