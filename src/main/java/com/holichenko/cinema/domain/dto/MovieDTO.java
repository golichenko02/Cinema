package com.holichenko.cinema.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long id;
    @NotBlank(message = "Title mustn't be blank")
    private String title;
    @NotNull(message = "Start time mustn't be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @NotNull(message = "Duration mustn't be null")
    private Integer duration;
    @NotNull(message = "Hall mustn't be null")
    private Integer hall;
    private Set<OrderDTO> orders;
}
