package com.tomatoma.controller;

import com.tomatoma.dto.FoodPlaceDTO;
import com.tomatoma.dto.ResponseDTO;
import com.tomatoma.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
public class FoodPlaceController {

    private static final Logger log = LoggerFactory.getLogger(FoodPlaceController.class);

    private final FoodService foodService;

    public FoodPlaceController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * GET /api/places/{trendFoodId}
     * Get places selling a specific food
     */
    @GetMapping("/{trendFoodId}")
    public ResponseEntity<ResponseDTO<List<FoodPlaceDTO>>> getPlacesForFood(@PathVariable Long trendFoodId) {
        log.info("Fetching places for food ID: {}", trendFoodId);

        List<FoodPlaceDTO> places = foodService.getPlacesForFood(trendFoodId);

        return ResponseEntity.ok(ResponseDTO.success(places));
    }

    /**
     * GET /api/places/{trendFoodId}/rated
     * Get places for food sorted by rating
     */
    @GetMapping("/{trendFoodId}/rated")
    public ResponseEntity<ResponseDTO<List<FoodPlaceDTO>>> getPlacesByRating(@PathVariable Long trendFoodId) {
        log.info("Fetching places by rating for food ID: {}", trendFoodId);

        List<FoodPlaceDTO> places = foodService.getPlacesForFoodByRating(trendFoodId);

        return ResponseEntity.ok(ResponseDTO.success(places));
    }

    /**
     * GET /api/places/{trendFoodId}/nearest
     * Get nearest places for food
     */
    @GetMapping("/{trendFoodId}/nearest")
    public ResponseEntity<ResponseDTO<List<FoodPlaceDTO>>> getNearestPlaces(
            @PathVariable Long trendFoodId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        log.info("Fetching nearest places for food ID: {} at ({}, {})", trendFoodId, latitude, longitude);

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO.error("유효하지 않은 좌표입니다. 위도: -90~90, 경도: -180~180", 400));
        }

        List<FoodPlaceDTO> places = foodService.getNearestPlacesForFood(trendFoodId, latitude, longitude);

        return ResponseEntity.ok(ResponseDTO.success(places));
    }

}
