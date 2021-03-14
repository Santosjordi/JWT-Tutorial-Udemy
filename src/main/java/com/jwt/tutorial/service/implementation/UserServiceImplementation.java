package com.jwt.tutorial.service.implementation;

import com.jwt.tutorial.domain.User;
import com.jwt.tutorial.domain.UserPrincipal;
import com.jwt.tutorial.enumeration.Role;
import com.jwt.tutorial.exception.domain.EmailExistException;
import com.jwt.tutorial.exception.domain.UserNotFoundException;
import com.jwt.tutorial.exception.domain.UsernameExistException;
import com.jwt.tutorial.repository.UserRepository;
import com.jwt.tutorial.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.jwt.tutorial.enumeration.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImplementation implements UserService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            LOGGER.error(user.toString() + " not found");
            throw new UsernameNotFoundException(user.toString() + " not found");
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info("Returning found user by username: " + username.toString());
            return userPrincipal;
        }
    }

    @Override
    public UserService register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
        User user = new User();
        user.setUserId(generatedUserId());
        String password = generatedPassword();
        String encodedPassword = encodedPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRoles(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl());
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        return null;
    }

    private String getTemporaryProfileImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("user/image/profile/temp").toUriString(); //http://localhost:8081
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatedPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generatedUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String email)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        if (StringUtils.isNotBlank(currentUsername)){
            User currentUser = findByUsername(currentUsername);
            if (currentUser == null){
                throw new UserNotFoundException("No user found with the username: " + currentUsername);
            }
            User userByNewUsername = findByUsername(newUsername);
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())){
                throw new UsernameExistException("This username is taken!");
            }
            User userByNewEmail = findUserByEmail(email);
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())){
                throw new EmailExistException("This username is taken!");
            }
            return currentUser;
        } else {
            User userByUsername = findByUsername(newUsername);
            if (userByUsername != null){
                throw new UsernameExistException("This username is taken!");
            }
            User userByEmail = findUserByEmail(email);
            if (userByEmail != null){
                throw new EmailExistException("This username is taken!");
            }
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
