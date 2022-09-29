package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select o from Order o left join fetch o.movie where o.id = :id")
    Optional<Order> findByIdJoinFetchMovie(Long id);
}
