package com.jwt.tutorial.service;

import com.jwt.tutorial.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserService register(String firstName, String lastName, String username, String email);

    List<User> getUsers();

    User findByUsername(String username);

    User findUserByEmail(String email);


}
