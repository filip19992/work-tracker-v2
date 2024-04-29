package pl.filipwlodarczyk.worktrackerv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepistory userRepistory;
}
