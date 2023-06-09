package com.develhope.login.order.contollers;

import com.develhope.login.order.entities.Order;
import com.develhope.login.order.entities.OrderDTO;
import com.develhope.login.order.repositories.OrderRepository;
import com.develhope.login.order.services.OrderService;
import com.develhope.login.users.entities.User;
import com.develhope.login.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('"+ Roles.REGISTERED +"')")

public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) throws Exception {
       return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getSingle(@PathVariable Long id,Principal principal){
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return ResponseEntity.notFound().build();

        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (Roles.hasRole(user,Roles.REGISTERED) && order.get().getCreatedBy().getId() == user.getId()){{
                return ResponseEntity.ok(order.get());
            }
        } else if ((Roles.hasRole(user,Roles.RESTAURANT) && order.get().getRestaurant().getId() == user.getId())) {
            return ResponseEntity.ok(order.get());
        } else if ((Roles.hasRole(user,Roles.RIDER) && order.get().getRider().getId() == user.getId())) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping
    public ResponseEntity<List<Order>> getAll(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (Roles.hasRole(user,Roles.REGISTERED)){
            return ResponseEntity.ok(orderRepository.findByCreatedBy(user));
        } else if (Roles.hasRole(user,Roles.RESTAURANT)) {
            return ResponseEntity.ok(orderRepository.findByRestaurant(user));
        } else if ((Roles.hasRole(user,Roles.RIDER))) {
            return ResponseEntity.ok(orderRepository.findByRider(user));
        }
        return null;
        }

    /*@PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order,@PathVariable Long id) {
        if (!orderService.canEdit(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
        return ResponseEntity.ok(orderService.updateOrder(id,order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        if (!orderService.canEdit(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }*/
}
