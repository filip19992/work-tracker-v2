package pl.filipwlodarczyk.worktrackerv2.user;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pl.filipwlodarczyk.worktrackerv2.user.authorities.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class UserDB implements UserDetails {
    @GeneratedValue
    @Id
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserDB(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDB() {
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
