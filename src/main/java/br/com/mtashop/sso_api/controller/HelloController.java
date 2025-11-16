package br.com.mtashop.sso_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping
    @RequestMapping("/hello")
    public String helloController(){
        return "hello";
    }

}
