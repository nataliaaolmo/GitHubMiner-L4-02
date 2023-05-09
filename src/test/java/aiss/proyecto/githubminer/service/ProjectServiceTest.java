package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    ProjectService projectService;
    @Test
    @DisplayName("Get project")
    void findProject(){
        Project project = projectService.findProjects("spring-projects", "spring-framework");
        assertEquals(project.getName(), "Projects Documentation", "Wrong name");
    }

}
