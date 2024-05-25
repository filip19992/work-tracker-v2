package pl.filipwlodarczyk.worktrackerv2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/blocked")
@RestController
public class TestController {
    @GetMapping
    public String shouldBeBlocekd() {
        return "You are loggedi in";
    }
}

