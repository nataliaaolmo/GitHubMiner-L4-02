package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Commit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest
public class CommitServiceTest {
    @Autowired
    CommitService commitService;

    @Test
    @DisplayName("Get commits")
    void findCommit(){
        List<Commit> commits = commitService.findCommits("MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==");
        assertTrue(commits.size()>0, "Wrong size");
    }
}
