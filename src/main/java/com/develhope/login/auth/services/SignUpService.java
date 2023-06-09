package com.develhope.login.auth.services;

import com.develhope.login.auth.entities.SignUpActivationDTO;
import com.develhope.login.auth.entities.SignUpDTO;
import com.develhope.login.notification.MailNotificationService;
import com.develhope.login.users.entities.Role;
import com.develhope.login.users.entities.User;
import com.develhope.login.users.repositories.RoleRepository;
import com.develhope.login.users.repositories.UserRepository;
import com.develhope.login.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User signUp(SignUpDTO signUpDTO) throws Exception{
        return this.signUp(signUpDTO, Roles.REGISTERED);
    }
    public User signUp(@RequestBody SignUpDTO signUpDTO, String role) throws Exception {
        User userInDB = userRepository.findByEmail(signUpDTO.getEmail());
        if (userInDB != null )throw new Exception("User already exists!");
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setSurname(signUpDTO.getSurname());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(role.toUpperCase());
        if (!userRole.isPresent())throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);

    }
    public User activate(SignUpActivationDTO signUpActivationDTO) throws Exception {
        User user = userRepository.getByActivationCode(signUpActivationDTO.getActivationCode());
        if (user == null) throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
