package ordernow.backend.ordernow_backend.responses.user;

import java.util.List;

import ordernow.backend.ordernow_backend.dtos.UserResponseDTO;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class UserListResponse extends BaseResponse {
    private List<UserResponseDTO> userList;

    public UserListResponse(List<UserResponseDTO> userList, String message) {
        super(message);
        this.userList = userList;
    }

    public List<UserResponseDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserResponseDTO> userList) {
        this.userList = userList;
    }
}
