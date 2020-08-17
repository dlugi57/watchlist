package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.watchlist.controller.WatchlistController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
@Service
@ConditionalOnProperty(name="app.environment", havingValue="prod")
public class MovieRatingServiceImpl implements MovieRatingService {

    String apiUrl = "http://www.omdbapi.com/?apikey=d82bea36&t=";

    private final Logger logger = LoggerFactory.getLogger(MovieRatingServiceImpl.class);

    @Override
    public String getMovieRating(String title) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            logger.info("OMDb API called with URL: {}", apiUrl + title);

            URI uri = new URI(apiUrl + title);

            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(uri, ObjectNode.class);

            ObjectNode jsonObject = response.getBody();

            logger.debug("OMDb API response: {}", jsonObject);

            if (jsonObject.path("imdbRating").asText() != ""){
                return jsonObject.path("imdbRating").asText();
            }else{
                logger.warn("There is no movie like this");
                return null;
            }


        } catch (Exception e) {
            logger.warn("Something went wrong when calling OMDb API" + e.getMessage());
            return null;
        }
    }
}
