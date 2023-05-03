package aiss.proyecto.githubminer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Generated;
import java.util.List;

@SpringBootApplication
public class Project {

    // DUA: id debe ser String pero desde la API viene como Integer. ¿Cómo/cuándo/dónde lo parseamos?
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("web_url")
    private String web_url;

    // DUDA: ¿Esto hay que ponerlo?
    private List<Commit> commits;
    private List<Issue> issues;

    // DUDA: ¿Necesitamos constructor?

    @JsonProperty("web_url")
    public String getWeb_url() {
        return web_url;
    }

    @JsonProperty("url")
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", web_url='" + web_url + '\'' +
                '}';
    }

}
