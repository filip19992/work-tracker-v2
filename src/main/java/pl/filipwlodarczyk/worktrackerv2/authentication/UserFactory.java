package pl.filipwlodarczyk.worktrackerv2.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.authorities.Role;

public class UserFactory {
    public static UserDB createUser(String username, String password) {
        return new UserDB(username, password, Role.USER);
    }

    public static UserDB createAdminUser(String username, String password) {
        return new UserDB(username, password, Role.ADMIN);
    }
}
