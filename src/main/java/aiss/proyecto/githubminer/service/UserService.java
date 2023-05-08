package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
import aiss.proyecto.githubminer.model.User;
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
public class UserService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${githubminer.token}")
    private String token;

    public User findUser(String id){
        String uri = "https://api.github.com/users/"+id;
        HttpHeaders headers = new HttpHeaders();
        //Setting token header
        if(token!=""){
            headers.set("Authorization", "Bearer " + token);
        }
        //Send request
        HttpEntity<User> request = new HttpEntity<>(null, headers);
        ResponseEntity<User> response = restTemplate
                .exchange(uri, HttpMethod.GET, request, User.class);
        return response.getBody();
    }

}
