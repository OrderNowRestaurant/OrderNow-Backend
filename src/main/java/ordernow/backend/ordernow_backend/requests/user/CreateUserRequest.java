package ordernow.backend.ordernow_backend.requests.user;

import ordernow.backend.ordernow_backend.entities.Role;

public class CreateUserRequest {
    private String username;
    private String password;
    private String roleName;

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
}
