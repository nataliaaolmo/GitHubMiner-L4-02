package aiss.proyecto.githubminer.exportmodel;

public class CommitExport {

    private String id;
    private String title;
    private String message;
    private String author_name;
    private String author_email;
    private String authored_date;
    private String commiter_name;
    private String commiter_email;
    private String commited_date;
    private String web_url;

    private String projectId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getCommiter_name() {
        return commiter_name;
    }

    public void setCommiter_name(String commiter_name) {
        this.commiter_name = commiter_name;
    }

    public String getCommiter_email() {
        return commiter_email;
    }

    public void setCommiter_email(String commiter_email) {
        this.commiter_email = commiter_email;
    }

    public String getCommited_date() {
        return commited_date;
    }

    public void setCommited_date(String commited_date) {
        this.commited_date = commited_date;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
