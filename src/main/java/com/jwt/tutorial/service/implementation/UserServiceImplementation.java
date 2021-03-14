package com.jwt.tutorial.service.implementation;

import com.jwt.tutorial.domain.User;
import com.jwt.tutorial.domain.UserPrincipal;
import com.jwt.tutorial.exception.domain.UserNotFoundException;
import com.jwt.tutorial.repository.UserRepository;
import com.jwt.tutorial.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImplementation implements UserService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);
    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserService register(String firstName, String lastName, String username, String email) {
        validateNewUsernameAndEmail();
        return null;
    }

    private void validateNewUsernameAndEmail(String currentUsername, String newUsername, String email) throws UserNotFoundException {
        if (StringUtils.isNotBlank(currentUsername)){
            User currentUser = findByUsername(currentUsername);
            if (currentUser == null){
                throw new UserNotFoundException("No user found with the username: " + currentUsername);
            }
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
