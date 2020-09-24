package august.examen.dataclasses;

import java.io.File;

public class Question {
    private int id;
    private int examId;
    private int parentId;
    private String content;
    private String label;
    private File[] photosAttached;
    private boolean hasChildren;
    private boolean hasParent;

    public Question(int id, int examId, int parentId, String content, String label, File[] photosAttached, boolean hasChildren, boolean hasParent) {
        this.id = id;
        this.examId = examId;
        this.parentId = parentId;
        this.content = content;
        this.label = label;
        this.photosAttached = photosAttached;
        this.hasChildren = hasChildren;
        this.hasParent = hasParent;
    }
    public  Question(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
