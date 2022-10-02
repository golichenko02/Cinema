package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MovieRepository extends BaseRepository<Movie, Long> {

    @QueryHints(
            @QueryHint(name = org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH, value = "false")
    )
    @Query("select distinct m from Movie m left join fetch m.orders where m.id = :id")
    Optional<Movie> findByIdJoinFetchOrders(Long id);
}
