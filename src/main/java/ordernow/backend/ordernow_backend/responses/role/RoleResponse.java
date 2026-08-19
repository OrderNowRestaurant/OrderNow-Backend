package ordernow.backend.ordernow_backend.responses.role;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.Role;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class RoleResponse extends BaseResponse {
    private List<Role> roleList;

    public RoleResponse(String message, List<Role> roleList) {
        super(message);
        this.roleList = roleList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
