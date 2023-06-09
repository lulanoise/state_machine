package com.develhope.login.order.contollers;

import com.develhope.login.order.entities.Order;
import com.develhope.login.order.repositories.OrderRepository;
import com.develhope.login.order.services.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/orders/{id}/state")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @Autowired
    private OrderRepository orderRepository;

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/accepted")
    public ResponseEntity<Order> accepted (@PathVariable Long id) throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setAccept(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/in-Preparation")
    public ResponseEntity<Order> inPreparation (@PathVariable Long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();
        orderStateService.setInPreparation(order.get());
        return ResponseEntity.ok(orderStateService.setInPreparation(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/ready")
    public ResponseEntity<Order> ready (@PathVariable Long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setReady(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/delivering")
    public ResponseEntity<Order> delivering (@PathVariable Long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();
        orderStateService.setDelivering(order.get());
        return ResponseEntity.ok(orderStateService.setDelivering(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/complete")
    public ResponseEntity<Order> complete (@PathVariable Long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();
        orderStateService.setComplete(order.get());
        return ResponseEntity.ok(orderStateService.setComplete(order.get()));
    }

}
