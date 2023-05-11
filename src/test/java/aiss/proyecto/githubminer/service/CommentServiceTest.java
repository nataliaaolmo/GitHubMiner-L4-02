package aiss.proyecto.githubminer.service;

import aiss.proyecto.githubminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("Get all comments of an issue")
    void findAllIssueComments(){
        List<Comment> comment = commentService.getAllRepositoryIssueComments("mouredev", "Hello-Python", "12");
        assertTrue(!comment.isEmpty(), "The list of comments is empty!");
        System.out.println(comment);
    }

    @Test
    @DisplayName("Get one comment of an issue")
    void findOneIssueComment() {
        Comment comment = commentService.getOneRepositoryIssueComment("mouredev", "Hello-Python", "1531747732");
        assertTrue(comment != null, "This comment does not exist");
        System.out.println(comment);
    }

}
