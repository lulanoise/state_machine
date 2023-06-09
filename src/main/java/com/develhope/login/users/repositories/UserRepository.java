package com.develhope.login.users.repositories;

import com.develhope.login.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User getByActivationCode(String activationCode);

    User findByPasswordResetCode(String passwordResetCode);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM (\n"+
            "\tSELECT u.*, COUNT(busyOrders.id) AS numberOfOrders\n" +
            "\tFROM `user` AS u\n" +
            "\tLEFT JOIN user_roles AS ur ON ur.user_id = u.id \n" +
            "\tLEFT JOIN (SELECT * FROM `orders` WHERE `status` IN(4)) AS busyOrders ON busyOrders.rider_id = u.id\n" +
            "\tWHERE ur.role_id = 2 AND u.is_active = 1\n" +
            "\tGROUP BY u.id\n" +
            ") AS allRiders\n" +
            "WHERE allRiders.numberOfOrders = 0" +
            "LIMIT 1")
    Optional<User> pickRider ();
}
