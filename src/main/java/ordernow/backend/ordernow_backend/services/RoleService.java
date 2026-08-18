package ordernow.backend.ordernow_backend.services;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.repositories.RoleRepository;
import ordernow.backend.ordernow_backend.responses.role.RoleResponse;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public RoleResponse getRoles() {
        return new RoleResponse("Los roles se han conseguido correctamente.", this.roleRepository.findAll());
    }
}
