package pl.filipwlodarczyk.worktrackerv2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepistory userRepistory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepistory.findByUsername(username).orElseThrow();
    }

    public List<String> getUsers() {
        var usernames = new ArrayList<String>();
        var allUsers = userRepistory.findAll();

        allUsers.forEach(c -> usernames.add(c.getUsername()));

        return usernames;

    }
}
