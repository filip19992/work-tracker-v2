package pl.filipwlodarczyk.worktrackerv2.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;
import pl.filipwlodarczyk.worktrackerv2.user.UserRepistory;
import pl.filipwlodarczyk.worktrackerv2.user.authorities.Role;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepistory userRepistory;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void givenUserThatIsAlreadyRegisterWhenRegisterinThenReturnNotAuthenticatedResponse() {
        var username = "aoo";
        var password = "boo";
        var user = new UserDB(username, password, Role.USER);
        var registrationRequest = new RegisterRequest(username, password);
        when(userRepistory.findByUsername(username)).thenReturn(Optional.of(user));

        var result = authenticationService.register(registrationRequest);

        assertFalse(result.isAuthenticated());
    }

    @Test
    void givenUserThatIsNotRegisteredWhenRegisteringThenReturnAuthenticatedResponse() {
        var username = "aoo";
        var password = "boo";
        var token = "coo";
        var user = new UserDB(username, password, Role.USER);
        var registrationRequest = new RegisterRequest(username, password);
        when(userRepistory.findByUsername(username)).thenReturn(Optional.empty());
        when(jwtService.generateToken(any())).thenReturn(token);


        var result = authenticationService.register(registrationRequest);

        assertTrue(result.isAuthenticated());
    }

    @Test
    void givenUserThatIsRegisteredWhenLoginInThenReturnPositiveAuthenticationResponse() {
        var username = "aoo";
        var password = "boo";
        var token = "coo";
        var loginRequest = new LoginRequest(username, password);
        var user = new UserDB(username, password, Role.USER);
        when(userRepistory.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn(token);

        var result= authenticationService.login(loginRequest);

        assertTrue(result.isAuthenticated());
        assertEquals(token, result.getToken());
    }

    @Test
    void givenUserThatIsNotRegisteredWhenLoginInThenReturnNegativeAuthenticationResponse() {
        var username = "aoo";
        var password = "boo";
        var loginRequest = new LoginRequest(username, password);
        when(userRepistory.findByUsername(username)).thenReturn(Optional.empty());

        var result = authenticationService.login(loginRequest);

        assertFalse(result.isAuthenticated());
    }
}