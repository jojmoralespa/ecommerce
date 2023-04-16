package com.demo.ecommerce.web_controller;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Order;
import com.demo.ecommerce.repository.OrderRepository;
import com.demo.ecommerce.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order orderNew = orderService.save(order);
        return ResponseEntity.ok(orderNew);
    }
}
