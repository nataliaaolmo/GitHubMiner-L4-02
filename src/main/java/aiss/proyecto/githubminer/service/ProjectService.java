package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.ProjectExport;
import aiss.proyecto.githubminer.model.Comment;
import aiss.proyecto.githubminer.model.Commit;
import aiss.proyecto.githubminer.model.Issue;
import aiss.proyecto.githubminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;
    CommentService commentService;
    CommitService commitService;
    IssueService issueService;

    @Value("${githubminer.token}")
    private String token;

    public Project findOneProject(String owner, String repo) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo;

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!="") {
            headers.set("Authorization", "Bearer " + token);
        }

        //Send request
        HttpEntity<Project> request = new HttpEntity<>(null, headers);
        ResponseEntity<Project> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Project.class);
        return response.getBody();
    }

    /*// los paramatreos van con path variable request param=?
    // Comprobar si hay que usar las clases de model o las de modelexport
    public Project getProjectService(String owner, String repo, List<Commit> commitsList, List<Issue> issuesList,
                                     List<Comment> commentsList) {
        Project project= null;
        commitsList = commitService.findCommits(owner,repo);
        issuesList = issueService.getAllRepositoryIssues(owner,repo);
        for(Issue issue: issuesList){
            commentsList = commentService.getAllRepositoryIssueComments(owner, repo, issue.getId().toString());
        }

        return project;
    }*/

    public static ProjectExport parseListas(Project project) {
        ProjectExport projectExport = ProjectExport.of(project);
        projectExport.setIssues(IssueService
                .getAllRepositoryIssues(project.getOwner().getLogin(), project.getName()).stream()
                .map(i -> IssueService.parseoIssue(i, project.getOwner().getLogin(), project.getName(), 2))
                .toList());
        projectExport.setCommits(CommitService
                .findCommits(project.getOwner().getLogin(), project.getName()).stream()
                .map(c -> CommitService.parseoCommit(c)).toList());

        return projectExport;
    }

}
