package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, Long> {

    @QueryHints(
            @QueryHint(name = org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH, value = "false")
    )
    @Query("select distinct o from Order o left join fetch o.movie where o.id = :id")
    Optional<Order> findByIdJoinFetchMovie(Long id);
}
