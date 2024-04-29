package pl.filipwlodarczyk.worktrackerv2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestRepository testRepository;

    @GetMapping(path = "/test")
    public Iterable<Test> test() {
        testRepository.save(new Test("value"));

        return testRepository.findAll();
    }


    @GetMapping()
    public String hello() {
        return "Kocham Cie <3";
    }

}
