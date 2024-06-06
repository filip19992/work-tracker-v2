package pl.filipwlodarczyk.worktrackerv2.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public List<ToDoResponse> getToDoByUserId(Integer userId) {
        return toDoRepository.findByUserId(userId).orElse(List.of())
                .stream()
                .map(mapToResponse())
                .toList();
    }

    private Function<ToDoDTO, ToDoResponse> mapToResponse() {
        return c -> new ToDoResponse(c.getId(), c.getContent());
    }

    public ToDoDTO addToDo(String content, UserDB user) {
        return toDoRepository.save(new ToDoDTO(user, content));
    }
}
