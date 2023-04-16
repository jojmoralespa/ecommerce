package com.demo.ecommerce.service;

import com.demo.ecommerce.model.Order;
import com.demo.ecommerce.model.OrderProduct;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.OrderRepository;
import com.demo.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order save(Order order){
        List<OrderProduct> orderProductList = order.getProductOrderInterList();
        for(OrderProduct orderProduct: orderProductList){
            orderProduct.setOrderId(order);
            Product productAux = orderProduct.getProductId();
            orderProduct.setProductId(productRepository.findById(productAux.getId()).get());
        }

        return orderRepository.save(order);
    }
}
