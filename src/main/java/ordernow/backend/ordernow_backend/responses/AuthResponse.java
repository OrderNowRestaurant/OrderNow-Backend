package ordernow.backend.ordernow_backend.responses;

public class AuthResponse extends BaseResponse {
    private String token;
    private String username;
    private String roleName;

    public AuthResponse(String token, String username, String roleName, String message) {
        super(message);
        this.token = token;
        this.username = username;
        this.roleName = roleName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}