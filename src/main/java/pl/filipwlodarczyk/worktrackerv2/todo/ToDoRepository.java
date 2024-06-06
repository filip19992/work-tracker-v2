package pl.filipwlodarczyk.worktrackerv2.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends CrudRepository<ToDoDTO, Long> {
    Optional<List<ToDoDTO>> findByUserId(Integer userId);
}
