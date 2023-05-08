package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    // /repos/{owner}/{repo}/issues/comments
    public List<Comment> getAllRepositoryComments(String owner, String repo) {
        List<Comment> comments = new ArrayList<>();
        String uri = "https://api.github.com/" + owner + "/" + repo + "/issues/comments";

        HttpHeaders headers = new HttpHeaders();
        // Setting token header
        if (token!="")
            headers.set("Authorization", "Bearer " + token);

        comments = restTemplate.getForObject(uri, List.class);

        return comments;
    }
}
