package ordernow.backend.ordernow_backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Role;
import ordernow.backend.ordernow_backend.enums.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
    
    Role findByRoleName(String roleName);

    List<Role> findAll();
}
