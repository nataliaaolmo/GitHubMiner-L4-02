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
    CommentService commentService;

    @Autowired
    RestTemplate restTemplate;

    // GET http://localhost:8080/github/{owner}/{repo}?since={since}&maxPages={maxPages}
    @GetMapping("/{owner}/{repo}")
    public ProjectExport findOneProject(@PathVariable String owner,
                                        @PathVariable String repo,
                                        @RequestParam(required = false, name = "sinceCommmits") Integer sinceCommits,
                                        @RequestParam(required = false, name = "sinceIssues") Integer sinceIssues,
                                        @RequestParam(required = false) Integer maxPages) {

        Project project = projectService.findProjects(owner, repo);
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getHtmlUrl();
        List<Commit> commits = commitService.groupAllCommits(owner, repo, sinceCommits, maxPages).stream().map(x->formatCommit(x)).toList();
        List<Issue> issues = issueService.groupAllIssues(owner, repo, sinceIssues, maxPages).stream().map(x->formatIssue(x,owner,repo,maxPages)).toList();

        return new ProjectExport(projectId,projectName,project_webUrl,commits,issues);
    }

    private String[] parseMessage (String s){
        String[] string = new String[]{s,""};
        if(s.contains("\n\n")){
            string = s.split("\\n\\n");
        }
        return string;
    }

    private CommitExport formatCommit(Commit commit){
        return new CommitExport(commit.getSha(), parseMessage(commit.getCommit().getMessage())[0], parseMessage(commit.getCommit().getMessage())[1],
                commit.getCommit().getAuthor().getName(), commit.getCommit().getAuthor().getEmail(), commit.getCommit().getAuthor().getDate(),
                commit.getCommit().getCommitter().getName(), commit.getCommit().getCommitter().getEmail(), commit.getCommit().getCommitter().getDate(),
                commit.getHtmlUrl());
    }

    private IssueExport formatIssue(Issue issue, String owner, String repo, Integer maxPages){
        String issueId = issue.getId().toString();
        String ref_id = issue.getNumber().toString();
        String title = issue.getTitle();
        String description = issue.getBody();
        String state = issue.getState();
        String created_at = issue.getCreatedAt();
        String updated_at = issue.getUpdatedAt();
        String closed_at = issue.getClosedAt();
        List<String> labels = issue.getLabels().stream().map(x->x.getName()).toList();

        FullAuthor fullAuthor = issueService.getFullAuthor(issue.getUser().getLogin());
        UserExport author = new UserExport(fullAuthor.getId().toString(), fullAuthor.getLogin(), fullAuthor.getName(), fullAuthor.getAvatarUrl(),fullAuthor.getHtmlUrl());

        FullAuthor fullAssignee=  issue.getAssignee()==null?null:issueService.getFullAuthor(issue.getAssignee().getLogin());
        UserExport assignee = issue.getAssignee()==null?null:new UserExport(fullAssignee.getId().toString(), fullAssignee.getLogin(), fullAssignee.getName(), fullAssignee.getAvatarUrl(),fullAssignee.getHtmlUrl());

        Integer upvotes = issue.getReactions().getPositive();
        Integer downvotes = issue.getReactions().getNegative();
        String web_url = issue.getHtmlUrl();
        List<CommentExport> comments= commentService.groupIssueComments(owner,repo,issue.getNumber().toString(),maxPages).stream().map(x->formatComment(x)).toList();


        return new IssueExport(issueId, ref_id, title, description, state, created_at, updated_at, closed_at, labels,
                author,assignee,upvotes,downvotes,web_url,comments);
    }

    private CommentExport formatComment(Comment comment){
        String id = comment.getId().toString();
        String body = comment.getBody();
        FullAuthor fullAuthor = commentService.getFullAuthor(comment.getUser().getLogin());
        UserExport author = new UserExport(fullAuthor.getId().toString(), fullAuthor.getLogin(), fullAuthor.getName(), fullAuthor.getAvatarUrl(),fullAuthor.getHtmlUrl());
        String created_at = comment.getCreatedAt();
        String updated_at = comment.getUpdatedAt();
        return new CommentExport(id, body, author, created_at, updated_at);
    }

    @PostMapping("/{owner}/{repo}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectExport ExportProject(@PathVariable String owner,
                                       @PathVariable String repo,
                                       @RequestParam(required = false,name="sinceCommits") Integer since,
                                       @RequestParam(required = false, name = "sinceIssues") Integer updatedAfter,
                                       @RequestParam(required = false, name = "max_pages") Integer maxPages){

        Project project = projectService.getProject(owner,repo).getBody();
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getHtmlUrl();
        List<CommitExport> commits = commitService.groupAllCommits(owner, repo, since, maxPages).stream().map(x->formatCommit(x)).toList();
        List<IssueExport> issues = issueService.groupAllIssues(owner, repo, since, maxPages).stream().map(x->formatIssue(x,owner,repo,maxPages)).toList();

        ProjectExport proyectoFormateado = new ProjectExport(projectId, projectName, project_webUrl, commits, issues);
        ProjectExport sentProject = restTemplate.postForObject("http://localhost:8080/gitminer/projects", proyectoFormateado,ProjectExport.class);

        return sentProject;
    }
}
