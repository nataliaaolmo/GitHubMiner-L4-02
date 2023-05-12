package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.CommentExport;
import aiss.proyecto.githubminer.exportmodel.UserExport;
import aiss.proyecto.githubminer.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utilsPagination.UtilPag.getNextPageUrl;
import static utilsPagination.UtilPag.getResponseEntity;

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
    public List<Comment> groupIssueComments(String owner, String repo, String number, Integer maxPages) throws HttpClientErrorException{
        List<Comment> comments = new ArrayList<>();
        Integer defaultPages;
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + number + "/comments";
        ResponseEntity<Comment[]> response = getResponseEntity(uri, Comment[].class);
        List<Comment> pageComments = Arrays.stream(response.getBody()).toList();
        comments.addAll(pageComments);

        //2..n pages
        String nextPageURL = getNextPageUrl(response.getHeaders());

        if(maxPages!=null){
            defaultPages=maxPages;
        }
        else{
            defaultPages=2;
        }

        int page = 2;
        while(nextPageURL != null && page <= defaultPages){
            response = getResponseEntity(nextPageURL,Comment[].class);
            pageComments = Arrays.stream(response.getBody()).toList();
            comments.addAll(pageComments);

            nextPageURL = getNextPageUrl(response.getHeaders());
            page++;
        }

        return comments;
    }

    /*public static CommentExport parseoComment(Comment comment){
        String id = comment.getId().toString();
        String body = comment.getBody();
        UserExport author = new UserExport(comment.getUser().getId().toString(), comment.getUser().getUsername().toString(),comment.getUser().getName().toString(),comment.getUser().getAvatarUrl().toString(),comment.getUser().getWeb_url().toString());
        String created_at = comment.getCreatedAt();
        String updated_at = comment.getUpdatedAt();
        return new CommentExport(id, body, author, created_at, updated_at);
    }*/

    public static CommentExport parseoComment(Comment comment) {
        String id = comment.getId().toString();
        String body = comment.getBody();
        String createdAt = comment.getCreatedAt();
        String updatedAt = comment.getCreatedAt();
        String issueId = CommentService.parseIssueId(comment.getIssueUrl());
        UserExport author = UserExport.of(comment.getUser());

        return new CommentExport(id,body,createdAt,updatedAt,issueId,author);
    }
    // https://api.github.com/repos/mouredev/Hello-Python/issues/12
    public static String parseIssueId(String issueURL) {
        String[] trozos = issueURL.split("/");
        String id = trozos[7].trim();
        return id;
    }

}
