package aiss.proyecto.githubminer.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Commit {

    @JsonProperty("node_id")
    private String id;
    private String title;
    private String message;
    private String author_name;
    private String author_email;
    private String authored_date;
    private String committer_name;
    private String committer_email;
    private String committed_date;
    @JsonProperty("url")
    private String web_url;
    @JsonProperty("sha")
    private String sha;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("comments_url")
    private String commentsUrl;
    @JsonProperty("commit")
    private Commit_1 commit;
    @JsonProperty("author")
    private Author__1 author;
    @JsonProperty("committer")
    private Committer__1 committer;
    @JsonProperty("parents")
    private List<Parent> parents;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();


    @JsonProperty("node_id")
    public String getId() {
        return id;
    }

    @JsonProperty("node_id")
    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty("url")
    public String getWeb_url() {
        return web_url;
    }

    @JsonProperty("url")
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    @JsonProperty("sha")
    public String getSha() {
        return sha;
    }

    @JsonProperty("sha")
    public void setSha(String sha) {
        this.sha = sha;
    }

    @JsonProperty("html_url")
    public String getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    @JsonProperty("comments_url")
    public String getCommentsUrl() {
        return commentsUrl;
    }

    @JsonProperty("comments_url")
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    @JsonProperty("commit")
    public Commit_1 getCommit() {
        return commit;
    }

    @JsonProperty("commit")
    public void setCommit(Commit_1 commit) {
        this.commit = commit;
    }

    @JsonProperty("author")
    public Author__1 getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author__1 author) {
        this.author = author;
    }

    @JsonProperty("committer")
    public Committer__1 getCommitter() {
        return committer;
    }

    @JsonProperty("committer")
    public void setCommitter(Committer__1 committer) {
        this.committer = committer;
    }

    @JsonProperty("parents")
    public List<Parent> getParents() {
        return parents;
    }

    @JsonProperty("parents")
    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public String getTitle() {
        return commit.getMessage();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return commit.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor_name() {
        return commit.getAuthor().getName();
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return commit.getAuthor().getEmail();
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthored_date() {
        return commit.getAuthor().getDate();
    }

    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getCommitter_name() {
        return commit.getCommitter().getName();
    }

    public void setCommitter_name(String committer_name) {
        this.committer_name = committer_name;
    }

    public String getCommitter_email() {
        return commit.getCommitter().getEmail();
    }

    public void setCommitter_email(String committer_email) {
        this.committer_email = committer_email;
    }

    public String getCommitted_date() {
        return commit.getCommitter().getDate();
    }

    public void setCommitted_date(String committed_date) {
        this.committed_date = committed_date;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
