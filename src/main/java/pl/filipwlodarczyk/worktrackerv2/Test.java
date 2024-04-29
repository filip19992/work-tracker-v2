package pl.filipwlodarczyk.worktrackerv2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue
    public Integer Id;
    public String testValue;

    public Test(String testValue) {
        this.testValue = testValue;
    }
}
