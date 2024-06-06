package pl.filipwlodarczyk.worktrackerv2.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.filipwlodarczyk.worktrackerv2.user.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService todoService;
    private final UserServiceImpl userService;


    @GetMapping()
    public ResponseEntity<List<ToDoResponse>> getToDos(Principal principal) {
        var name = principal.getName();
        var user = userService.findUserByUsername(name);
        return ResponseEntity.ok(todoService.getToDoByUserId(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@RequestBody AddToDoRequest addToDoRequest, Principal principal) {
        var name = principal.getName();
        var user = userService.findUserByUsername(name);
        var toDoDTO = todoService.addToDo(addToDoRequest.content(), user);

        return ResponseEntity.ok("added todo" + toDoDTO.getContent());
    }
}

