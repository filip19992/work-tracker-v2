package pl.filipwlodarczyk.worktrackerv2.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepistory extends CrudRepository<UserDB, Integer> {
    UserDB findByUsername(String username);
}
