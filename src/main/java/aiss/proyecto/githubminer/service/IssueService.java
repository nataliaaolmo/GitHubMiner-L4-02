package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.IssueExport;
import aiss.proyecto.githubminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${githubminer.token}")
    private String token;

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
}
