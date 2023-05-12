package aiss.proyecto.githubminer.exportmodel;

import aiss.proyecto.githubminer.model.Comment;
import aiss.proyecto.githubminer.model.User;

import java.util.List;

public class IssueExport {

    private String id; // id
    private String ref_id; // number -> este es el que va en la uri https://api.github.com/repos/:OWNER/:REPO/issues/:id
    private String title;
    private String description;
    private String state;
    private String createdAt;
    private String updatedAt;
    private String closedAt;
    private List<String> labels;
    private Integer upvotes;
    private Integer downvotes;

    private List<CommentExport> comments;

    private UserExport asignee;
    private UserExport author;
    private String projectId;


    public IssueExport(String id, String ref_id, String title, String description,
                            String state, String createdAt, String updatedAt, String closedAt,
                            List<String> labels, UserExport author, UserExport asignee, Integer upvotes,
                            Integer downvotes, String projectId, List<CommentExport> comments) {
        this.id = id;
        this.ref_id = ref_id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.labels = labels;
        this.author = author;
        this.asignee = asignee;
        this.upvotes = upvotes;
        this.downvotes = downvotes;

        this.comments = comments;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public List<CommentExport> getComments() {
        return comments;
    }

    public void setComments(List<CommentExport> comments) {
        this.comments = comments;
    }

    public UserExport getAsignee() {
        return asignee;
    }

    public void setAsignee(UserExport asignee) {
        this.asignee = asignee;
    }

    public UserExport getAuthor() {
        return author;
    }

    public void setAuthor(UserExport author) {
        this.author = author;
    }
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
