package spring.study.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.response.BaseResponse;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder().message("hello").build());
    }
}
