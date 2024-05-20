package pl.filipwlodarczyk.worktrackerv2.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.UserRepistory;
import pl.filipwlodarczyk.worktrackerv2.user.authorities.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepistory userRepistory;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = new UserDB(registerRequest.username(), registerRequest.password(), Role.USER);

        userRepistory.save(user);

        var jwtToken = generateJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticate(loginRequest);

        var user = findUser(loginRequest);

        var jwtToken = generateJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String generateJwtToken(UserDB user) {
        return jwtService.generateToken(user);
    }

    private UserDB findUser(LoginRequest loginRequest) {
        return userRepistory.findByUsername(loginRequest.username()).orElseThrow();
    }

    private void authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(getAuthenticationToken(loginRequest));
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
