package pl.filipwlodarczyk.worktrackerv2.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepistory extends CrudRepository<UserDB, Integer> {
    Optional<UserDB> findByUsername(String username);
}
