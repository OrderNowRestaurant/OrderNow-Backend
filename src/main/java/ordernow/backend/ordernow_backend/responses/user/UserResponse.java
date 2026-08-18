package ordernow.backend.ordernow_backend.responses.user;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class UserResponse extends BaseResponse {
    private User user;

    public UserResponse(String message, User user) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
