package com.santanna.order.service;

import com.santanna.order.domain.dto.OrderDTO;
import com.santanna.order.domain.dto.StatusDTO;
import com.santanna.order.domain.entity.Orders;
import com.santanna.order.domain.enums.Status;
import com.santanna.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository repository;
   @Autowired
   private final ModelMapper modelMapper;
    public Page<OrderDTO> getAllOrders(Pageable page) {
        var listOrders = repository.findAll(page).map(p -> modelMapper.map(p, OrderDTO.class));
        return listOrders;
    }
    public OrderDTO getOrderById(Long id) {
        var orderById = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        Orders orders = orderById;
        var entityToDto = modelMapper.map(orders, OrderDTO.class);
        return entityToDto;
    }

    public OrderDTO createOrderData(OrderDTO dto) {
        var dtoToEntity = modelMapper.map(dto, Orders.class);
        Orders orders = dtoToEntity;
        orders.setDateTime(LocalDateTime.now());
        orders.setStatus(Status.ACCOMPLISHED);
        orders.getItens().forEach(item -> item.setOrders(orders));
       repository.save(orders);
        var entityToDto = modelMapper.map(orders, OrderDTO.class);
        return entityToDto;
    }
    public OrderDTO updateStatus(Long id, StatusDTO dto) {
        Orders orders = repository.byIdWithItens(id);
        if (orders == null) {
            throw new EntityNotFoundException();
        }
        orders.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), orders);
        var entityToDto = modelMapper.map(orders, OrderDTO.class);
        return entityToDto;
    }

    public void approvePayment(Long id) {
        Orders orders = repository.byIdWithItens(id);

        if (orders == null) {
            throw new EntityNotFoundException();
        }
        orders.setStatus(Status.PAIDOUT);
        repository.updateStatus(Status.PAIDOUT, orders);
    }
}