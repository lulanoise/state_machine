package com.develhope.login.users.services;

import com.develhope.login.users.entities.User;
import com.develhope.login.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;

    public User pickRider() {
        Optional<User> rider = userRepository.pickRider();
        if (rider.isPresent()){
            return rider.get();
        }else {
            return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
        }
    }
}
