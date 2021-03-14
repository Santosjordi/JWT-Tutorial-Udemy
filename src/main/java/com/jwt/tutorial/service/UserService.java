package com.jwt.tutorial.service;

import com.jwt.tutorial.domain.User;
import com.jwt.tutorial.exception.domain.EmailExistException;
import com.jwt.tutorial.exception.domain.UserNotFoundException;
import com.jwt.tutorial.exception.domain.UsernameExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserService register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findByUsername(String username);

    User findUserByEmail(String email);


}
