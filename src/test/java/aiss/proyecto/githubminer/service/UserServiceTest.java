package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
import aiss.proyecto.githubminer.model.Project;
import aiss.proyecto.githubminer.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("Get user")
    void findUser(){
        User user = userService.findUser("1");
        assertEquals(user.getLogin(), "octocat", "Wrong name");
    }
}
