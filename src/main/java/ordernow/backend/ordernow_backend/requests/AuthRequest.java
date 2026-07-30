package ordernow.backend.ordernow_backend.requests;

public abstract class AuthRequest {
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
