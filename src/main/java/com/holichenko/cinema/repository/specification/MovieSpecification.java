package com.holichenko.cinema.repository.specification;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieSpecification {

    public Specification<Movie> build(MovieSearchCriteria searchCriteria) {
        return andTitle(searchCriteria)
                .and(andStartTime(searchCriteria))
                .and(andDuration(searchCriteria))
                .and(andHall(searchCriteria));
    }

    private Specification<Movie> andTitle(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Movie.Fields.title), searchCriteria.getTitle());
    }

    private Specification<Movie> andStartTime(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Movie.Fields.startTime), searchCriteria.getStartTime());
    }

    private Specification<Movie> andDuration(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Movie.Fields.duration), searchCriteria.getDuration());
    }

    private Specification<Movie> andHall(MovieSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Movie.Fields.hall), searchCriteria.getHall());
    }
}
