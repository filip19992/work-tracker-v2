package pl.filipwlodarczyk.worktrackerv2.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.filipwlodarczyk.worktrackerv2.user.UserServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserServiceImpl userDetailsService;
    public static final int JWT_BEGIN_INDEX = 7;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        final String jwtToken;
        final String username;
        if (isInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = getJwtToken(authHeader);
        username = extractUsername(jwtToken);

        if (username != null && getAuthentication() == null) {
            var userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                var authenticationToken = getAuthenticationToken(userDetails);
                authenticationToken.setDetails(
                        builDetails(request)
                );
                setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(UsernamePasswordAuthenticationToken authenticationToken) {
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private WebAuthenticationDetails builDetails(HttpServletRequest request) {
        return new WebAuthenticationDetailsSource().buildDetails(request);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private String extractUsername(String jwtToken) {
        return jwtService.extractUsernameFromToken(jwtToken);
    }

    private boolean isInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith(BEARER_PREFIX);
    }

    private String getJwtToken(String authHeader) {
        return authHeader.substring(JWT_BEGIN_INDEX);
    }
}
