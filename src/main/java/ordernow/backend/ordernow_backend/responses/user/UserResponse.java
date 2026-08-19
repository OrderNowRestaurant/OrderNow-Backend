package ordernow.backend.ordernow_backend.responses.user;

import ordernow.backend.ordernow_backend.dtos.UserResponseDTO;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class UserResponse extends BaseResponse {
    private UserResponseDTO user;

    public UserResponse(String message, UserResponseDTO user) {
        super(message);
        this.user = user;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
    
}
