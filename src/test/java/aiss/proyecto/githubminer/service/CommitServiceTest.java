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
    void findCommits() {
        List<Commit> commits = commitService.findCommits("spring-projects", "spring-framework");
        assertTrue(commits.size()>0, "Wrong size");
        System.out.println(commits);
    }

    @Test
    @DisplayName("Get one commit")
    void findOneCommit() {
        Commit commit = commitService.findOneCommitById(
                "spring-projects",
                "spring-framework",
                "a91effcd86eae8ff5e9e47b88c7ef82fd40b341c");
        assertTrue(commit.getCommit().getCommentCount().equals(0), "Wrong commit");
        System.out.println(commit);
    }

}
