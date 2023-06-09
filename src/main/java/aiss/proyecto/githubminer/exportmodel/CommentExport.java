package aiss.proyecto.githubminer.exportmodel;

import aiss.proyecto.githubminer.model.Comment;
import aiss.proyecto.githubminer.model.User;
import aiss.proyecto.githubminer.service.CommentService;

public class CommentExport {

    private String id;
    private String body;
    private String createdAt;
    private String updatedAt;
    private UserExport author;

    public CommentExport(String id, String body,UserExport author, String createdAt, String updatedAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public UserExport getAuthor() {
        return author;
    }

    public void setAuthor(UserExport author) {
        this.author = author;
    }
}
