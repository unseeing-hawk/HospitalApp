package main.entity;

public class User {
    private Long id;
    private String login;
    private String token;

    public User() {
    }

    public User(long id, String login, String token) {
        this.id = id;
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
