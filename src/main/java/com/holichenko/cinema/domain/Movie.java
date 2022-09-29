package com.holichenko.cinema.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(generator = "movie_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movie_seq_gen", sequenceName = "movies_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer hall;

    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order){
        order.setMovie(this);
        orders.add(order);
    }

    public void removeOrder(Order order){
        order.setMovie(null);
        orders.remove(order);
    }

    public Movie updateMovieData(Movie movie) {
        this.title = movie.title;
        this.duration = movie.duration;
        this.hall = movie.hall;
        this.startTime = movie.startTime;
        return this;
    }
}
