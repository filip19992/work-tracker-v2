package pl.filipwlodarczyk.worktrackerv2.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoDTO getToDoByUserId(Integer userId) {
        return toDoRepository.findByUserId(userId).orElse(null);
    }
}
