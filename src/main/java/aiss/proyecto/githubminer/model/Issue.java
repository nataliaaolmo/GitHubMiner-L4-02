package aiss.proyecto.githubminer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String description;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("closed_at")
    private String closedAt;
    @JsonProperty("labels")
    private List<Label> labelList;
    @JsonProperty("number")
    private String ref_id;
    @JsonProperty("reactions")
    private Reactions reactions;

    private List<Comment> comments;

    private User author; // user
    private User asignee; // asignee

    private List<String> labels = labelList.stream().map(l -> l.getName()).toList();
    private Integer upvotes = reactions.getUpvotes();
    private Integer downvotes = reactions.getDownvotes();

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

//    private String projectId; // ¿repository_url?

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAsignee() {
        return asignee;
    }

    public void setAsignee(User asignee) {
        this.asignee = asignee;
    }

//    public String getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(String projectId) {
//        this.projectId = projectId;
//    }

    public Reactions getReactions() {
        return reactions;
    }

    public void setReactions(Reactions reactions) {
        this.reactions = reactions;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", closedAt='" + closedAt + '\'' +
                ", labels=" + labels +
                ", ref_id='" + ref_id + '\'' +
                ", comments=" + comments +
                ", author=" + author +
                ", asignee=" + asignee +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
//                ", projectId='" + projectId + '\'' +
                '}';
    }

}
