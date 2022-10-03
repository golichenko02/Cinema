package com.holichenko.cinema.repository.specification;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieSpecification {

    public Specification<Movie> build(MovieSearchCriteria searchCriteria) {
        return andTitle(searchCriteria)
                .and(andStartTime(searchCriteria))
                .and(andDuration(searchCriteria))
                .and(andHall(searchCriteria));
    }

    private Specification<Movie> andTitle(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> Objects.nonNull(searchCriteria.getTitle()) ?
                criteriaBuilder.equal(root.get(Movie.Fields.title), searchCriteria.getTitle()) : criteriaBuilder.conjunction();
    }

    private Specification<Movie> andStartTime(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> Objects.nonNull(searchCriteria.getStartTime()) ?
                criteriaBuilder.equal(root.get(Movie.Fields.startTime), searchCriteria.getStartTime()) : criteriaBuilder.conjunction();
    }

    private Specification<Movie> andDuration(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> Objects.nonNull(searchCriteria.getDuration()) ?
                criteriaBuilder.equal(root.get(Movie.Fields.duration), searchCriteria.getDuration()) : criteriaBuilder.conjunction();
    }

    private Specification<Movie> andHall(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> Objects.nonNull(searchCriteria.getHall()) ?
                criteriaBuilder.equal(root.get(Movie.Fields.hall), searchCriteria.getHall()) : criteriaBuilder.conjunction();
    }
}
