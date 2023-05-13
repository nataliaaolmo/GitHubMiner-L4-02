package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.CommitExport;
import aiss.proyecto.githubminer.exportmodel.IssueExport;
import aiss.proyecto.githubminer.exportmodel.ProjectExport;
import aiss.proyecto.githubminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommentService commentService;

    @Autowired
    CommitService commitService;

    @Autowired
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
//    public ProjectExport parseListas(Project project) {
//        ProjectExport projectExport = of(project);
//        projectExport.setIssues(issueService
//                .getAllRepositoryIssues(project.getOwner().getLogin(), project.getName()).stream()
//                .map(i -> issueService.parseoIssue(i, project.getOwner().getLogin(), project.getName(), 2))
//                .toList());
//        projectExport.setCommits(commitService
//                .findCommits(project.getOwner().getLogin(), project.getName()).stream()
//                .map(c -> commitService.parseoCommit(c)).toList());
//
//        return projectExport;
//    }
//    public ProjectExport of(Project project) {
//        String id = project.getId().toString();
//        String name = project.getName();
//        String web_url = project.getUrl();
//        List<CommitExport> commits = new ArrayList<>();
//        List<IssueExport> issues = new ArrayList<>();
//
//        return new ProjectExport(id,name,web_url,commits,issues);
//    }

    public ResponseEntity<Project[]> getResponseEntity(String uri, Class<Project[]> clase) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Project[]> request = new HttpEntity<>(null,headers);

        return restTemplate.exchange(uri,
                HttpMethod.GET,
                request,
                clase);
    }

}
