
package aiss.proyecto.githubminer.model;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequest {

    @JsonProperty("url")
    private String url;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("diff_url")
    private String diffUrl;
    @JsonProperty("patch_url")
    private String patchUrl;
    @JsonProperty("merged_at")
    private Object mergedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("html_url")
    public String getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    @JsonProperty("diff_url")
    public String getDiffUrl() {
        return diffUrl;
    }

    @JsonProperty("diff_url")
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    @JsonProperty("patch_url")
    public String getPatchUrl() {
        return patchUrl;
    }

    @JsonProperty("patch_url")
    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    @JsonProperty("merged_at")
    public Object getMergedAt() {
        return mergedAt;
    }

    @JsonProperty("merged_at")
    public void setMergedAt(Object mergedAt) {
        this.mergedAt = mergedAt;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
