package ordernow.backend.ordernow_backend.responses;

public class AuthResponse extends BaseResponse {
    private String token;
    private String username;

    public AuthResponse(String token, String username, String message) {
        super(message);
        this.token = token;
        this.username = username;
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
}