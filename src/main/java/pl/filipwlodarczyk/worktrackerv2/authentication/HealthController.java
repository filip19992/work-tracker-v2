package pl.filipwlodarczyk.worktrackerv2.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public String healthCheck() {
        return "UP";
    }
}
