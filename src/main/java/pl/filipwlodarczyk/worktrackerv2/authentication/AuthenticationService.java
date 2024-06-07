package pl.filipwlodarczyk.worktrackerv2.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.UserRepistory;
import pl.filipwlodarczyk.worktrackerv2.user.authorities.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepistory userRepistory;
    private final JwtService jwtService;
    @Lazy
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = new UserDB(registerRequest.username(), registerRequest.password(), Role.USER);


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

    private UsernamePasswordAuthenticationToken getAuthenticationToken(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
    }
}
