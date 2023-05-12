package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.exportmodel.CommitExport;
import aiss.proyecto.githubminer.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utilsPagination.UtilPag.getNextPageUrl;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProjectService projectService;

    @Value("${githubminer.token}")
    private String token;

    // GET https://api.github.com/repos/:owner/:repo/commits
    public List<Commit> findCommits(String owner, String repo) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";

        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        headers.set("Authorization", "Bearer " + token);

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

    //conjunto de commits paginados
    public List<Commit> groupAllCommits(String owner, String repo, Integer since, Integer maxPages) throws HttpClientErrorException {
        List<Commit> commits = new ArrayList<>();
        Integer defaultPages;
        String finalUri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";

        if (since != null) {
            finalUri += "?since=" + LocalDateTime.now().minusDays(since);
        } else {
            //añadimos 5 por defecto porque nos lo indica la uri
            finalUri += "?since=" + LocalDateTime.now().minusDays(5);
        }

        ResponseEntity<Commit[]> response = getResponseEntity(finalUri, Commit[].class);
        //paginamos commits
        List<Commit> pageCommits = Arrays.stream(response.getBody()).toList();
        commits.addAll(pageCommits);

        //2..n pages
        String nextPageURL = getNextPageUrl(response.getHeaders());

        if(maxPages!=null){
            defaultPages=maxPages;
        }
        else{
            defaultPages=2;
        }

        int page = 2;
        while (nextPageURL != null && page <= defaultPages) {
            response = getResponseEntity(nextPageURL,Commit[].class);
            pageCommits = Arrays.stream(response.getBody()).toList();
            commits.addAll(pageCommits);

            nextPageURL = getNextPageUrl(response.getHeaders());
            page++;

        }
        return commits;

    }
    public CommitExport parseoCommit(Commit commit){
        String[] url = new String[]{commit.getHtmlUrl(), "/"};

        return new CommitExport(commit.getSha(), parseMessage(commit.getCommit().getMessage())[0], parseMessage(commit.getCommit().getMessage())[1],
                commit.getCommit().getAuthor().getName(), commit.getCommit().getAuthor().getEmail(), commit.getCommit().getAuthor().getDate(),
                commit.getCommit().getCommitter().getName(), commit.getCommit().getCommitter().getEmail(), commit.getCommit().getCommitter().getDate(),
                commit.getHtmlUrl(),"");
    }
    private static String[] parseMessage(String message){
        String[] tittleMessage = new String[]{message,""};
        if(message.contains("\n\n")){
            tittleMessage = message.split("\\n\\n");
        }
        return tittleMessage;
    }

    public ResponseEntity<Commit[]> getResponseEntity(String uri, Class<Commit[]> clase) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Commit[]> request = new HttpEntity<>(null,headers);

        return restTemplate.exchange(uri,
                HttpMethod.GET,
                request,
                clase);
    }


}
