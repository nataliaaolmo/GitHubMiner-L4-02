package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.CommentExport;
import aiss.proyecto.githubminer.exportmodel.IssueExport;
import aiss.proyecto.githubminer.exportmodel.UserExport;
import aiss.proyecto.githubminer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utilsPagination.UtilPag.getNextPageUrl;

@Service
public class IssueService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommentService commentService;
    @Autowired
    ProjectService projectService;

    @Value("${githubminer.token}")
    private String token;

    @GetMapping("/{owner}/{repo}")

    public List<Issue> getAllRepositoryIssues(String owner, String repo) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        // Send request
        HttpEntity<List> request = new HttpEntity<>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, request, List.class);

        return response.getBody();
    }

    public Issue getRepositoryIssueById(String owner, String repo, Integer id) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + id.toString();

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        // Send request
        HttpEntity<Issue> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Issue.class);

        return response.getBody();
    }

    //conjunto de issues paginado
    public List<Issue> groupAllIssues(String owner, String repo, Integer sinceIssues,
                                             Integer maxPages) throws HttpClientErrorException {
        List<Issue> issues = new ArrayList<>();
        Integer defaultPages;
        String finalUri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";


        if (sinceIssues != null) {
            finalUri += "?since=" + LocalDateTime.now().minusDays(sinceIssues);
        } else {
            //añadimos 30 por defecto porque nos lo indica la uri
            finalUri += "?since=" + LocalDateTime.now().minusDays(30);
        }
        ResponseEntity<Issue[]> response = getResponseEntity(finalUri, Issue[].class);
        //paginamos issues
        List<Issue> pageIssues = Arrays.stream(response.getBody()).toList();
        issues.addAll(pageIssues);

        //2..n pages
        String nextPageURL = getNextPageUrl(response.getHeaders());

        if (maxPages != null) {
            defaultPages = maxPages;
        } else {
            defaultPages = 2;
        }

        int page = 2;
        while (nextPageURL != null && page <= defaultPages) {
            response = getResponseEntity(nextPageURL, Issue[].class);
            pageIssues = Arrays.stream(response.getBody()).toList();
            issues.addAll(pageIssues);

            nextPageURL = getNextPageUrl(response.getHeaders());
            page++;

        }
        return issues;
    }

    //parseamos issues para añadirle los comentarios de esas issues. Además, a partir de la propiedad Author,
    // de Issue encontraremos los parámetros que necesitamos para User según el modelo de datos
    public IssueExport parseoIssue(Issue issue, String owner, String repo, Integer maxPages){
        String issueId = issue.getId().toString();
        String issueIid = issue.getNodeId().toString();
        String title = issue.getTitle();
        String description = issue.getBody();
        String state = issue.getState();
        String created_at = issue.getCreatedAt();
        String updated_at = issue.getUpdatedAt();
        String closed_at = issue.getClosedAt();
        List<String> labels = issue.getLabels().stream().map(x->x.getName()).toList();
        //cogemos las propiedades que nos interesan según el modelo de User de una issue
        UserExport author = new UserExport(issue.getUser().getId().toString(), issue.getUser().getLogin(),"",issue.getUser().getAvatarUrl(),issue.getUser().getUrl());

        //coger el primer assigne que tenemos en issue
        UserExport assignee = issue.getAssignee()==null?null:new UserExport(issue.getAssignee().getId().toString(), issue.getAssignee().getLogin(), "", issue.getAssignee().getAvatarUrl(), issue.getAssignee().getUrl());

        Integer upvotes = issue.getReactions().getPlus1();
        Integer downvotes = issue.getReactions().getMinus1();
        List<CommentExport> comments = commentService.groupIssueComments(owner,repo,issue.getNumber().toString(),maxPages).stream().map(x-> CommentService.parseoComment(x)).toList();

        String projectId = projectService.findOneProject(owner, repo).getId().toString();

        return new IssueExport(issueId, issueIid, title, description, state, created_at, updated_at, closed_at, labels, author, assignee, upvotes, downvotes, projectId, comments);
    }

    public static String parseProjectId(String projectURL) {
        String[] trozos = projectURL.split("/");
        String id = trozos[7].trim();
        return id;
    }


    public ResponseEntity<Issue[]> getResponseEntity(String uri, Class<Issue[]> clase) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Issue[]> request = new HttpEntity<>(null,headers);

        return restTemplate.exchange(uri,
                HttpMethod.GET,
                request,
                clase);
    }


}
