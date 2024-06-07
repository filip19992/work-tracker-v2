package pl.filipwlodarczyk.worktrackerv2.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.UserRepistory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepistory userRepistory;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = UserFactory.createUser(registerRequest.username(), registerRequest.password());


        if (isUserRegistered(user))
            return new AuthenticationResponse(false, "Cannot register user!");

        userRepistory.save(user);

        var jwtToken = generateJwtToken(user);

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

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .authenticated(true)
                    .build();
        }

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
}
