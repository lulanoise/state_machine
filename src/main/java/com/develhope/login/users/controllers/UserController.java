package com.develhope.login.users.controllers;

import com.develhope.login.auth.entities.LoginRTO;
import com.develhope.login.auth.services.LoginService;
import com.develhope.login.users.entities.User;
import com.develhope.login.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public LoginRTO getProfile(Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        LoginRTO loginRTO = new LoginRTO();
        loginRTO.setUser(user);
        loginRTO.setJWT(loginService.generateJWT(user));
        return loginRTO;
    }

}
