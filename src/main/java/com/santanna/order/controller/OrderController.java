package com.santanna.order.controller;

import com.santanna.order.domain.dto.OrderDTO;
import com.santanna.order.domain.dto.StatusDTO;

import com.santanna.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping()
    public ResponseEntity<Page<OrderDTO>> listAllOrderist(@PageableDefault(size = 10) Pageable page) {
        service.getAllOrders(page);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> orderById(@PathVariable @NotNull Long id) {
        OrderDTO dto = service.getOrderById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
        OrderDTO orderCreate = service.createOrderData(dto);
        URI endereco = uriBuilder.path("/order/{id}").buildAndExpand(orderCreate.getId()).toUri();
        return ResponseEntity.created(endereco).body(orderCreate);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable @NotNull Long id, @RequestBody StatusDTO statusDTO) {
        OrderDTO updateOrderStatus = service.updateStatus(id, statusDTO);
        return ResponseEntity.ok(updateOrderStatus);
    }
    @PutMapping("/{id}/paid-out")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id) {
        service.approvePayment(id);
        return ResponseEntity.ok().build();

    }
}
