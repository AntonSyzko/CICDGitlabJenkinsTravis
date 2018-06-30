package com.cicd.example1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public @ResponseBody
    String accessDefault(){
        return "Hello from Boot";
    }
}
