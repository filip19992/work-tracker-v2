package pl.filipwlodarczyk.worktrackerv2.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public List<ToDoDTO> getToDoByUserId(Integer userId) {
        return toDoRepository.findByUserId(userId).orElse(List.of());
    }

    public ToDoDTO addToDo(String content, UserDB user) {
        var saved = toDoRepository.save(new ToDoDTO(user, content));

        return saved;
    }
}
