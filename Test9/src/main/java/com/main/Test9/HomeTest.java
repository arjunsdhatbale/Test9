package com.main.Test9;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeTest {

    @GetMapping("/")
    public String home(){
        System.out.println("This is test home... ");
        return "wellcome";
    }
}
