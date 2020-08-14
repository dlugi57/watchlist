package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
@Service
public class MovieRatingService {
    String apiUrl = "http://www.omdbapi.com/?apikey=d82bea36&t=";

    public String getMovieRating(String title) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            URI uri = new URI(apiUrl + title);

            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(uri, ObjectNode.class);

            ObjectNode jsonObject = response.getBody();

            return jsonObject.path("imdbRating").asText();
        } catch (Exception e) {
            System.out.println("Something went wrong when calling OMDb API" + e.getMessage());
            return null;
        }
    }
}
