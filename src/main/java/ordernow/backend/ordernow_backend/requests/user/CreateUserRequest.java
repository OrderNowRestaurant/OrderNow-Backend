package ordernow.backend.ordernow_backend.requests.user;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.Role;

public class CreateUserRequest {
    private String username;
    private String password;
    private Role role;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public Role getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
