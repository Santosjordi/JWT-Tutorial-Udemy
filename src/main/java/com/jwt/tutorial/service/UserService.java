package com.jwt.tutorial.service;

import com.jwt.tutorial.domain.User;
import com.jwt.tutorial.exception.domain.EmailExistException;
import com.jwt.tutorial.exception.domain.UserNotFoundException;
import com.jwt.tutorial.exception.domain.UsernameExistException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String firstName, String lastName, String username, String email, String role,
                    boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role,
                    boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

    void deleteUser(long id);

    void resetPassword(String email) throws UserNotFoundException;

    User updateProfileImage(String username, MultipartFile  profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;
}
