package com.holichenko.cinema.repository.specification;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

@RequiredArgsConstructor
public class MovieSpecification implements Specification<Movie> {

    private final MovieSearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (Objects.nonNull(searchCriteria.getStartTime())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("startTime"), searchCriteria.getStartTime()));
        }

        if (Objects.nonNull(searchCriteria.getDuration())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("duration"), searchCriteria.getDuration()));
        }

        if (Objects.nonNull(searchCriteria.getTitle())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("title"), searchCriteria.getTitle()));
        }


        if (Objects.nonNull(searchCriteria.getHall())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("hall"), searchCriteria.getHall()));
        }

        return predicate;
    }
}
