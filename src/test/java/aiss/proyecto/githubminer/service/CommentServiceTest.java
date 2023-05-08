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
    @DisplayName("Get commit")
    void findComment(){
        List<Comment> comment = commentService.getAllRepositoryComments("spring-projects", "spring-framework");
        assertEquals(comment.size()>0, "Wrong size");
    }

}
