package com.holichenko.cinema.repository.specification;

import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

@RequiredArgsConstructor
public class OrderSpecification implements Specification<Order> {

    private final OrderSearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (Objects.nonNull(searchCriteria.getMovieId())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("movie").get("id"), searchCriteria.getMovieId()));
        }

        if (Objects.nonNull(searchCriteria.getTotalSeats())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("totalSeats"), searchCriteria.getTotalSeats()));
        }

        if (Objects.nonNull(searchCriteria.getTotalAmount())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("totalAmount"), searchCriteria.getTotalAmount()));
        }

        return predicate;
    }
}
