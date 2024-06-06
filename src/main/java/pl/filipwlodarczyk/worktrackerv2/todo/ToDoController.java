package pl.filipwlodarczyk.worktrackerv2.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService todoService;


    @GetMapping("/{userId}")
    public ResponseEntity<ToDoDTO> getToDo(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(todoService.getToDoByUserId(userId));
    }

    @PostMapping("/add")
    public void addTodo() {

    }
}

