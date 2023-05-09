package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
import aiss.proyecto.githubminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${githubminer.token}")
    private String token;
    public Project  findProjects(String owner, String repo){
        String uri = "https://api.github.com/repos/"+owner+"/"+repo+"/projects";
        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }
        //Send request
        HttpEntity<Project> request = new HttpEntity<>(null, headers);
        ResponseEntity<Project> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, Project.class);
        return response.getBody();
    }
}
