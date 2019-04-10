package ch.heigvd.smtp.client;

import java.util.Base64;

public class Credential {
    private String username;
    private String password;

    public Credential(String username, String password) {
        this.username = Base64.getEncoder().encodeToString(username.getBytes());
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
