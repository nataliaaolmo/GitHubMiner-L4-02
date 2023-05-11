package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Comment;
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
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    // https://api.github.com/repos/:OWNER/:REPO/issues/:issue_number/comments
    public List<Comment> getAllRepositoryIssueComments(String owner, String repo, String issueId) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueId + "/comments";

        HttpHeaders headers = new HttpHeaders();
        // Setting token header
        if (token!="") {
            headers.set("Authorization", "Bearer " + token);
        }

        // Send request
        HttpEntity<List> request = new HttpEntity<>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, request, List.class);

        return response.getBody();
    }

    // https://api.github.com/repos/:OWNER/:REPO/issues/comments/:comment_id
    public Comment getOneRepositoryIssueComment(String owner, String repo, String commentId) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/comments/" + commentId;

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        // Send request
        HttpEntity<Comment> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Comment.class);

        return response.getBody();
    }

}
