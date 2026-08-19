package ordernow.backend.ordernow_backend.dtos;

import ordernow.backend.ordernow_backend.entities.User;

public record UserResponseDTO(String username, String roleName) {

    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
            user.getUsername(),
            user.getRole() != null ? user.getRole().getRoleName().name() : ""
        );
    }
}
