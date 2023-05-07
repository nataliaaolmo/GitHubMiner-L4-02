package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CommitService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${githubminer.token}")
    private String token;


    public List<Commit> findCommits(String id){
        String uri = "https://api.github.com/repos/"+id+"/REPO/commits";
        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }
        //Send request
        HttpEntity<Commit> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Commit.class);
        return Arrays.asList(response.getBody());
    }
}
