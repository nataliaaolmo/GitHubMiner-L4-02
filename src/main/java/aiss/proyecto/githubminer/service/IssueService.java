package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${githubminer.token}")
    private String token;

    public List<Issue> getAllRepositoryIssues(String owner, String repo) {
        Issue[] issues;
        String uri = "https://api.github.com/" + owner + "/" + repo + "/issues";

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        issues = restTemplate.getForObject(uri, Issue[].class);

        return Arrays.stream(issues).toList();
    }

    public Issue getRepositoryIssueById(String owner, String repo, Integer id) {
        Issue issue = null;
        String uri = "https://api.github.com/" + owner + "/" + repo + "/issues/" + id.toString();

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        issue = restTemplate.getForObject(uri, Issue.class);

        return issue;
    }
}
