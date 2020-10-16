package august.examen.models;

import java.io.File;
import java.util.UUID;

public class Question{
    private String questionId;
    private String examId;
    private String parentId;
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
    public  Question(){
        UUID uniqueKey = UUID.randomUUID();
        this.questionId = uniqueKey.toString();
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
