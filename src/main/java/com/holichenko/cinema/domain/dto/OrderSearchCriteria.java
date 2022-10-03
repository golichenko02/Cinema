package com.holichenko.cinema.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderSearchCriteria {

    private BigDecimal totalAmount;
    private Long totalSeats;
    private Long movieId;
}
