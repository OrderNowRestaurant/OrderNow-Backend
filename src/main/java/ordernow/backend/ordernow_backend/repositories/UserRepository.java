package ordernow.backend.ordernow_backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        Optional<User> findByUsername(String username);

}
