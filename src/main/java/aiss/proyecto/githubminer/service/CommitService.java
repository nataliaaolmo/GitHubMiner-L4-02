package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
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
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    // GET https://api.github.com/repos/:owner/:repo/commits
    public List<Commit> findCommits(String owner, String repo) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!="") {
            headers.set("Authorization", "Bearer " + token);
        }

        //Send request
        HttpEntity<List> request = new HttpEntity<>(null, headers);
        ResponseEntity<List> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, List.class);
        return response.getBody();
    }

    // GET https://api.github.com/repos/:owner/:repo/commits/:ref
    public Commit findOneCommitById(String owner, String repo, String ref) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits/" + ref;

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!="") {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Commit> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Commit.class);
        return response.getBody();
    }

}
