package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("Get all comments of an issue")
    void findAllIssueComments(){
        List<Comment> comment = commentService.getAllRepositoryIssueComments("spring-projects", "spring-framework", "2");
        assertEquals(comment.size()>0, "Wrong size");
    }

    @Test
    @DisplayName("Get one comment of an issue")
    void findOneIssueComment() {
        Comment comment = commentService.getOneRepositoryIssueComment("spring-projects", "spring-framework", "2", "1");
        assertEquals(comment != null, "This comment does not exist");
    }

}
