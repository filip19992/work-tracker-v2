package pl.filipwlodarczyk.worktrackerv2.todo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import pl.filipwlodarczyk.worktrackerv2.user.UserDB;

@Entity
@Table(name = "todo")
@NoArgsConstructor
public class ToDoDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDB user;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ToDoDTO(UserDB user, String content) {
        this.user = user;
        this.content = content;
    }
}
