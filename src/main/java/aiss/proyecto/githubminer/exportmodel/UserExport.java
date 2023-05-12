package aiss.proyecto.githubminer.exportmodel;

import aiss.proyecto.githubminer.model.User;

public class UserExport {

    private String id;
    private String username;
    private String name;
    private String avatarUrl;
    private String web_url;

    public UserExport(String id, String username, String name, String avatarUrl, String web_url) {
        this.id = id;
        this.username=username;
        this.avatarUrl=avatarUrl;
        this.web_url=web_url;
    }

    public static UserExport of(User user) {
        String id = user.getId().toString();
        String username = user.getLogin();
        String name = "";
        String avatarUrl = user.getAvatarUrl();
        String web_url = user.getUrl();

        return new UserExport(id,username,name,avatarUrl,web_url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

}
