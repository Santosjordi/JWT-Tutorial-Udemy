package com.jwt.tutorial.resource;

import com.jwt.tutorial.exception.domain.EmailExistException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @GetMapping("/home")
    public String showUser() throws EmailExistException {
        return "<body><h1>Application works</h1></body>";
    }
}
