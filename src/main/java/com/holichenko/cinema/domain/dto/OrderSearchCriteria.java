package com.holichenko.cinema.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSearchCriteria {

    private Long totalAmount;
    private Long totalSeats;
    private Long movieId;
}
