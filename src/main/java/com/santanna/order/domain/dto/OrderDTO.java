package com.santanna.order.domain.dto;

import com.santanna.order.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemDTO> itens = new ArrayList<>();

}
