package ordernow.backend.ordernow_backend.requests.user;

public class EditUserRequest {
    private String username;
    private String password;
    private String roleName;
    private String originalUsername;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOriginalUsername() {
        return originalUsername;
    }

    public void setOriginalUsername(String originalUsername) {
        this.originalUsername = originalUsername;
    }
}
