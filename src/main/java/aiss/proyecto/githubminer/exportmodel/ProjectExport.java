package aiss.proyecto.githubminer.exportmodel;

import aiss.proyecto.githubminer.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectExport {

    private String id;
    private String name;
    private String web_url;
    private List<CommitExport> commits;
    private List<IssueExport> issues;

    public ProjectExport(String id, String name, String web_url, List<CommitExport> commits, List<IssueExport> issues) {
        this.id = id;
        this.name = name;
        this.web_url = web_url;
        this.commits = commits;
        this.issues = issues;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public List<CommitExport> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitExport> commits) {
        this.commits = commits;
    }

    public List<IssueExport> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueExport> issues) {
        this.issues = issues;
    }
}
