package spring.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.domain.Hello;
import spring.study.repository.HelloRepository;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloRepository helloRepository;

    @GetMapping("/hello")
    public String hello() {
        helloRepository.save(new Hello("hello"));

        return "hello";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/cd")
    public String cd() {
        helloRepository.save(new Hello("CD success!!!"));
        return "CD success!!!";
    }

    @GetMapping("/github-actions")
    public String githubActions() {
        helloRepository.save(new Hello("github-actions Clear!"));
        return "github-actions Clear!";
    }
}
