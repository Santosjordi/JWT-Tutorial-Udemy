package com.jwt.tutorial.listener;

import com.jwt.tutorial.domain.User;
import com.jwt.tutorial.domain.UserPrincipal;
import com.jwt.tutorial.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event){
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal){
            UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttempt(user.getUsername());
        }
    }
}
// username: BuckRogers password: $2a$10$38w6VfwYIvNMQtXJgnVq.uUDhcpKaieC6VUKChT8R9rLHcECWBc0S