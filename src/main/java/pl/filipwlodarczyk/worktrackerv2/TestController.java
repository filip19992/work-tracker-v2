package pl.filipwlodarczyk.worktrackerv2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    TestRepository testRepository;

    @GetMapping(path = "/test")
    public List<Test> test() {
        testRepository.save(new Test("value"));

        return getTests();
    }

    private ArrayList<Test> getTests() {
        var allTest = testRepository.findAll();
        var returnList = new ArrayList<Test>();
        allTest.forEach(c -> returnList.add(c));

        return returnList;
    }


    @GetMapping()
    public String hello() {
        return "Kocham Cie <3";
    }

}
