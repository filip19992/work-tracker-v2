package pl.filipwlodarczyk.worktrackerv2.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.UserRepistory;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepistory userRepistory;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = UserFactory.createUser(registerRequest.username(), registerRequest.password());

        if (isUserRegistered(user)) {
            logUserInfo("Registration Failed", user);
            return new AuthenticationResponse(false, "Cannot register user!");
        }

        userRepistory.save(user);

        var jwtToken = generateJwtToken(user);
        logUserInfo("User Registered", user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        var user = findUser(loginRequest);

        if (user.isPresent()) {
            var foundUser = user.get();
            var jwtToken = generateJwtToken(foundUser);
            logUserInfo("User Logged In", foundUser);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .authenticated(true)
                    .build();
        }

        logUserInfo("Login Failed", null);
        return AuthenticationResponse.builder()
                .authenticated(false)
                .build();
    }

    private boolean isUserRegistered(UserDB user) {
        return userRepistory.findByUsername(user.getUsername()).isPresent();
    }

    private String generateJwtToken(UserDB user) {
        return jwtService.generateToken(user);
    }

    private Optional<UserDB> findUser(LoginRequest loginRequest) {
        return userRepistory.findByUsername(loginRequest.username());
    }

    private void logUserInfo(String message, UserDB user) {
        if (user != null) {
            System.out.println(message);
            for (Field field : user.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(user);
                    System.out.println(field.getName() + ": " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("User not found");
        }
    }
}
