package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Get a project")
    void findProject() {
        Project project = projectService.findOneProject("spring-projects", "spring-framework");
        System.out.println(project);
        assertTrue(project.getName().equals("spring-framework"), "Wrong repository");
    }

}
