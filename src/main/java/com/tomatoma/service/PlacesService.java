package com.tomatoma.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomatoma.dto.FoodPlaceDTO;
import com.tomatoma.entity.FoodPlace;
import com.tomatoma.repository.FoodPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
public class PlacesService {

    private static final Logger log = LoggerFactory.getLogger(PlacesService.class);

    private final FoodPlaceRepository foodPlaceRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${google.places.api-key}")
    private String googlePlacesApiKey;

    public PlacesService(FoodPlaceRepository foodPlaceRepository) {
        this.foodPlaceRepository = foodPlaceRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Search for places selling a specific food using Google Places API
     */
    public List<FoodPlaceDTO> searchPlacesForFood(String foodName, Double latitude, Double longitude) {
        log.info("Searching places for food: {} at location ({}, {})", foodName, latitude, longitude);

        List<FoodPlaceDTO> results = new ArrayList<>();

        try {
            // Google Places API Text Search endpoint
            String query = foodName + " restaurant";
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json" +
                    "?query=" + java.net.URLEncoder.encode(query, StandardCharsets.UTF_8) +
                    "&location=" + latitude + "," + longitude +
                    "&radius=5000" + // 5km radius
                    "&key=" + googlePlacesApiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode results_node = root.path("results");

                if (results_node.isArray()) {
                    int count = 0;
                    for (JsonNode place : results_node) {
                        if (count >= 15) break; // Limit to 15 results

                        FoodPlaceDTO placeDTO = parsePlaceData(place);
                        results.add(placeDTO);
                        count++;
                    }
                }

                log.info("Found {} places for food: {}", results.size(), foodName);

            } else {
                log.error("Failed to search places. Status: {}", response.statusCode());
            }

        } catch (Exception e) {
            log.error("Error searching places: {}", e.getMessage(), e);
        }

        return results;
    }

    /**
     * Nearby search for food places
     */
    public List<FoodPlaceDTO> nearbySearch(String foodType, Double latitude, Double longitude, Integer radiusMeters) {
        log.info("Performing nearby search for: {} at ({}, {})", foodType, latitude, longitude);

        List<FoodPlaceDTO> results = new ArrayList<>();

        try {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=" + latitude + "," + longitude +
                    "&radius=" + (radiusMeters != null ? radiusMeters : 2500) +
                    "&type=restaurant" +
                    "&keyword=" + java.net.URLEncoder.encode(foodType, StandardCharsets.UTF_8) +
                    "&key=" + googlePlacesApiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode results_node = root.path("results");

                if (results_node.isArray()) {
                    for (JsonNode place : results_node) {
                        FoodPlaceDTO placeDTO = parsePlaceData(place);
                        results.add(placeDTO);
                    }
                }

                log.info("Found {} nearby places", results.size());

            } else {
                log.error("Nearby search failed. Status: {}", response.statusCode());
            }

        } catch (Exception e) {
            log.error("Error in nearby search: {}", e.getMessage(), e);
        }

        return results;
    }

    /**
     * Parse Google Places API response
     */
    private FoodPlaceDTO parsePlaceData(JsonNode place) {
        FoodPlaceDTO dto = new FoodPlaceDTO();

        dto.setGoogle_place_id(place.path("place_id").asText());
        dto.setName(place.path("name").asText());
        dto.setAddress(place.path("formatted_address").asText());

        JsonNode geometry = place.path("geometry").path("location");
        dto.setLatitude(geometry.path("lat").asDouble());
        dto.setLongitude(geometry.path("lng").asDouble());

        dto.setRating(place.path("rating").asDouble(0.0));

        String businessStatus = place.path("business_status").asText();
        if ("OPERATIONAL".equals(businessStatus)) {
            dto.setOperating_hours("영업 중");
        } else if ("CLOSED_TEMPORARILY".equals(businessStatus)) {
            dto.setOperating_hours("임시 휴점");
        } else if ("CLOSED_PERMANENTLY".equals(businessStatus)) {
            dto.setOperating_hours("폐점");
        }

        // Estimate price if available
        int priceLevel = place.path("price_level").asInt(0);
        if (priceLevel > 0) {
            dto.setPrice_approx(priceLevel * 10000); // Rough estimation
        }

        return dto;
    }

    /**
     * Save places to database
     */
    public void savePlaces(Long trendFoodId, List<FoodPlaceDTO> places) {
        log.info("Saving {} places for trend food ID: {}", places.size(), trendFoodId);

        for (FoodPlaceDTO placeDTO : places) {
            Optional<FoodPlace> existing = foodPlaceRepository.findByGooglePlaceId(placeDTO.getGoogle_place_id());

            if (existing.isEmpty()) {
                FoodPlace foodPlace = new FoodPlace();
                foodPlace.setTrend_food_id(trendFoodId);
                foodPlace.setName(placeDTO.getName());
                foodPlace.setAddress(placeDTO.getAddress());
                foodPlace.setLatitude(placeDTO.getLatitude());
                foodPlace.setLongitude(placeDTO.getLongitude());
                foodPlace.setRating(placeDTO.getRating());
                foodPlace.setPrice_approx(placeDTO.getPrice_approx());
                foodPlace.setOperating_hours(placeDTO.getOperating_hours());
                foodPlace.setGoogle_place_id(placeDTO.getGoogle_place_id());

                foodPlaceRepository.save(foodPlace);
            }
        }

        log.info("Saved places to database");
    }

}
