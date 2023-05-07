package aiss.proyecto.githubminer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reactions {

    @JsonProperty("+1")
    private Integer upvotes;
    @JsonProperty("-1")
    private Integer downvotes;

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

}
