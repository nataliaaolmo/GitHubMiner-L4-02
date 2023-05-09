package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.CommentExport;

import aiss.proyecto.githubminer.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    // https://api.github.com/repos/:OWNER/:REPO/issues/:issue_number/comments
    public List<Comment> getAllRepositoryIssueComments(String owner, String repo, String issueId) {
        Comment[] comments;
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueId + "/comments";

        HttpHeaders headers = new HttpHeaders();
        // Setting token header
        if (token!="")
            headers.set("Authorization", "Bearer " + token);

        comments = restTemplate.getForObject(uri, Comment[].class);

        return Arrays.stream(comments).toList();
    }

    public Comment getOneRepositoryIssueComment(String owner, String repo, String issueId, String commentId) {
        Comment comment = null;
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueId + "/comments/" + commentId;

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }

        comment = restTemplate.getForObject(uri, Comment.class);

        return comment;
    }

}
