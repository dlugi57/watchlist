package com.openclassrooms.watchlist.actuator;

import com.openclassrooms.watchlist.service.MovieRatingService;
import com.openclassrooms.watchlist.service.MovieRatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    private MovieRatingServiceImpl movieRatingService;


    @Autowired
    public HealthCheck( MovieRatingServiceImpl movieRatingService) {
    super();
        this.movieRatingService = movieRatingService;
    }

    @Override
    public Health health() {
    if (movieRatingService.getMovieRating("Up")==null){
        return Health.down().withDetail("Cause", "OMDb API is not available").build();
    }
        return Health.up().build();
    }
}
