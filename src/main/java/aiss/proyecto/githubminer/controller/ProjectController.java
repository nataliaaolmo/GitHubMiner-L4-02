package aiss.proyecto.githubminer.controller;

import aiss.proyecto.githubminer.exportmodel.*;
import aiss.proyecto.githubminer.model.Comment;
import aiss.proyecto.githubminer.model.Commit;
import aiss.proyecto.githubminer.model.Issue;
import aiss.proyecto.githubminer.model.Project;
import aiss.proyecto.githubminer.service.CommentService;
import aiss.proyecto.githubminer.service.CommitService;
import aiss.proyecto.githubminer.service.IssueService;
import aiss.proyecto.githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/github")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    CommitService commitService;
    @Autowired
    IssueService issueService;
    @Autowired
    RestTemplate restTemplate;

    // GET http://localhost:8080/github/{owner}/{repo}?sinceCommits={sinceCommits}&maxPages={maxPages}
    @GetMapping("/{owner}/{repo}")
    public ProjectExport findOneProject(@PathVariable String owner,
                                        @PathVariable String repo,
                                        @RequestParam(required = false, name = "sinceCommmits") Integer sinceCommits,
                                        @RequestParam(required = false, name = "sinceIssues") Integer sinceIssues,
                                        @RequestParam(required = false) Integer maxPages) {

        Project project = projectService.findOneProject(owner, repo);
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getHtmlUrl();
        List<CommitExport> listCommits = commitService.groupAllCommits(owner, repo, sinceCommits, maxPages).stream().map(x -> commitService.parseoCommit(x)).toList();
        List<IssueExport> listIssues = issueService.groupAllIssues(owner, repo, sinceIssues, maxPages).stream().map(x -> issueService.parseoIssue(x, owner, repo, maxPages)).toList();

        return new ProjectExport(projectId, projectName, project_webUrl, listCommits, listIssues);
    }

    @PostMapping("/{owner}/{repo}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectExport ExportProject(@PathVariable String owner,
                                       @PathVariable String repo,
                                       @RequestParam(required = false,name="sinceCommits") Integer sinceCommits,
                                       @RequestParam(required = false, name = "sinceIssues") Integer sinceIssues,
                                       @RequestParam(required = false, name = "max_pages") Integer maxPages){

        Project project = projectService.findOneProject(owner,repo);
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getHtmlUrl();
        List<CommitExport> listCommits = commitService.groupAllCommits(owner, repo, sinceCommits, maxPages).stream().map(x->commitService.parseoCommit(x)).toList();
        List<IssueExport> listIssues = issueService.groupAllIssues(owner, repo, sinceIssues, maxPages).stream().map(x->issueService.parseoIssue(x,owner,repo,maxPages)).toList();

        ProjectExport proyectoParseado = new ProjectExport(projectId, projectName, project_webUrl, listCommits, listIssues);
        ProjectExport toGitMinerProject  = restTemplate.postForObject("http://localhost:8080/gitminer/projects",proyectoParseado,ProjectExport.class);

        return toGitMinerProject;
    }
}
