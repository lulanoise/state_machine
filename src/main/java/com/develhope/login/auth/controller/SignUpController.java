package com.develhope.login.auth.controller;

import com.develhope.login.auth.entities.SignUpActivationDTO;
import com.develhope.login.auth.entities.SignUpDTO;
import com.develhope.login.auth.services.SignUpService;
import com.develhope.login.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
       return signUpService.signUp(signUpDTO);
    }

    @PostMapping("/signup/{role}")
    public User signupRole(@RequestBody SignUpDTO signUpDTO, @PathVariable String role) throws Exception {
        return signUpService.signUp(signUpDTO, role);
    }

    @PostMapping("/signup/activation")
    public User signup (@RequestBody SignUpActivationDTO signUpActivationDTO) throws Exception {
        return signUpService.activate(signUpActivationDTO);
    }

}
