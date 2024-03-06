package com.santanna.order.repository;

import com.santanna.order.domain.entity.Orders;
import com.santanna.order.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Orders p set p.status = :status where p = :order")
    void updateStatus(Status status, Orders order);

    @Query(value = "SELECT p from Orders p LEFT JOIN FETCH p.itens where p.id = :id")
    Orders byIdWithItens(Long id);


}
