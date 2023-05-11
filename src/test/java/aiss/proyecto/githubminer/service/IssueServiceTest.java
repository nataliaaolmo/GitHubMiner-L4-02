package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IssueServiceTest {

    @Autowired
    IssueService service;

    @DisplayName("Get all repository issues")
    @Test
    void getAllIssues() {
        List<Issue> issues = service.getAllRepositoryIssues("mouredev",
                "Hello-Python");
        assertTrue(!issues.isEmpty(), "The list of issues is empty!");
        System.out.println(issues);
    }

    @DisplayName("Get one repository issue by ID")
    @Test
    void getIssueById() {
        Issue issue = service.getRepositoryIssueById("mouredev",
                "Hello-Python",
                1);
        assertTrue(issue != null, "This issue does not exist!");
        System.out.println(issue);
    }

}
