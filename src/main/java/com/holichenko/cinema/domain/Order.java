package com.holichenko.cinema.domain;


import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"totalAmount", "totalSeats"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "order_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Long totalSeats;

    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public void removeMovie() {
        this.movie.removeOrder(this);
    }

    public Order updateOrderData(Order order) {
        this.totalAmount = order.totalAmount;
        this.totalSeats = order.totalSeats;
        return this;
    }

}
