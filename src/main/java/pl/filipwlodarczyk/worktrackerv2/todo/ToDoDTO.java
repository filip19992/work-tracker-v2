package pl.filipwlodarczyk.worktrackerv2.todo;

import jakarta.persistence.*;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;

@Entity
@Table(name = "todo")
public class ToDoDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDB user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDB getUser() {
        return user;
    }

    public void setUser(UserDB user) {
        this.user = user;
    }
}
