package com.holichenko.cinema.repository.specification;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OrderSpecification {

    public Specification<Order> build(OrderSearchCriteria searchCriteria) {
        return andMovieId(searchCriteria)
                .and(andTotalSeats(searchCriteria))
                .and(andTotalAmount(searchCriteria));
    }

    private Specification<Order> andMovieId(OrderSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order.Fields.movie).get(Movie.Fields.id), searchCriteria.getMovieId());
    }

    private Specification<Order> andTotalSeats(OrderSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order.Fields.totalSeats), searchCriteria.getTotalSeats());
    }

    private Specification<Order> andTotalAmount(OrderSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order.Fields.totalAmount), searchCriteria.getTotalAmount());
    }

}
