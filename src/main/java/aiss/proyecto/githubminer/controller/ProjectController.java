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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Tag(name = "Project", description="Project management API")
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


    @Operation(summary= "Retrieve one project",
            description= "Get a project ",
            tags= { "projects", "get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content= { @Content(schema = @Schema(implementation = ProjectExport.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "404", description="Project no encontrado",
            content= { @Content(schema = @Schema()) })})

    // GET http://localhost:8080/github/{owner}/{repo}?sinceCommits={sinceCommits}&maxPages={maxPages}
    @GetMapping("/{owner}/{repo}")
    public ProjectExport findOneProject(@Parameter(description="owner and repo of the project to be searched") @PathVariable String owner,
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

    @Operation(summary= "Export one project",
            description= "Export a project ",
            tags= { "projects", "post" })

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content= { @Content(schema = @Schema(implementation = ProjectExport.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "400", content= { @Content(schema = @Schema()) })})


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
