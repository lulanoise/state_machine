package com.develhope.login.order.services;

import com.develhope.login.order.entities.Order;
import com.develhope.login.order.entities.OrderStateEnum;
import com.develhope.login.order.repositories.OrderRepository;
import com.develhope.login.users.entities.User;
import com.develhope.login.users.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderStateService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RiderService riderService;


    public Order setAccept(Order order) throws Exception {
        if (order == null) throw new NullPointerException();
        if (order.getOrderState() != OrderStateEnum.CREATED) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.ACCEPTED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setInPreparation(Order order) throws Exception {
        if (order == null) throw new NullPointerException();
        if (order.getOrderState() != OrderStateEnum.ACCEPTED) throw new Exception("Cannot edit order ");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.IN_PREPARATION);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setReady(Order order) throws Exception {
        if (order == null) throw new NullPointerException();
        if (order.getOrderState() != OrderStateEnum.IN_PREPARATION) throw new Exception("Cannot edit order ");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order");
        User rider = riderService.pickRider();
        order.setRider(rider);
        order.setOrderState(OrderStateEnum.READY);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setDelivering(Order order) throws Exception {
        if (order == null) throw new NullPointerException();
        if (order.getOrderState() != OrderStateEnum.READY) throw new Exception("Cannot edit order ");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRider().getId() != user.getId())throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.DELIVERING);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setComplete(Order order) throws Exception {
        if (order == null) throw new NullPointerException();
        if (order.getOrderState() != OrderStateEnum.DELIVERING) throw new Exception("Cannot edit order ");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRider().getId() != user.getId())throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }
}
