package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectServiceTest {
    @Autowired
    ProjectService projectService;
    @Test
    @DisplayName("Get project")
    void findProject(){
        Project project = projectService.findProjects("1002603");
        assertEquals(project.getName(), "My Projects", "Wrong name");
    }

}
