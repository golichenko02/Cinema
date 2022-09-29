package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @Query("select m from Movie m left join fetch m.orders where m.id = :id")
    Optional<Movie> findByIdJoinFetchOrders(Long id);
}
