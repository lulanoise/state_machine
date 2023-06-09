package com.develhope.login.order.repositories;

import com.develhope.login.order.entities.Order;
import com.develhope.login.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCreatedBy (User user);
    List<Order> findByRestaurant (User user);
    List<Order> findByRider (User user);
}
