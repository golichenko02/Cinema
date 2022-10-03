package com.holichenko.cinema.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldNameConstants
public class OrderDTO {
    private Long id;

    @Positive(message = "Total amount cannot be zero or less")
    @NotNull(message = "Total amount mustn't be null")
    private BigDecimal totalAmount;

    @Positive(message = "Total seats cannot be zero or less")
    @NotNull(message = "Total seats mustn't be null")
    private Long totalSeats;

    @NotNull(message = "Movie id mustn't be null")
    private Long movieId;
}
