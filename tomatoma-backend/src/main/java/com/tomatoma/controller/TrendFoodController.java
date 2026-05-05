package com.tomatoma.controller;

import com.tomatoma.dto.TrendFoodDTO;
import com.tomatoma.dto.ResponseDTO;
import com.tomatoma.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trends")
public class TrendFoodController {

    private static final Logger log = LoggerFactory.getLogger(TrendFoodController.class);

    private final FoodService foodService;

    public TrendFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * GET /api/trends
     * Get trending foods with optional filters and sorting
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<TrendFoodDTO>>> getTrendingFoods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "search_frequency") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction) {

        log.info("Fetching trending foods - page: {}, size: {}, category: {}, sortBy: {}, direction: {}",
                page, size, category, sortBy, direction);

        Sort sort = JpaSort.unsafe(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TrendFoodDTO> trends;

        if (category != null && !category.isEmpty()) {
            trends = foodService.getTrendingFoodsByCategory(category, pageable);
        } else {
            trends = foodService.getTrendingFoods(pageable);
        }

        return ResponseEntity.ok(ResponseDTO.success(trends));
    }

    /**
     * GET /api/trends/search
     * Search for trending foods by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<Page<TrendFoodDTO>>> searchTrendingFoods(
            @RequestParam(required = true) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("Searching trending foods - keyword: {}", q);

        Pageable pageable = PageRequest.of(page, size);
        Page<TrendFoodDTO> results = foodService.searchTrendingFoods(q, pageable);

        return ResponseEntity.ok(ResponseDTO.success(results));
    }

    /**
     * GET /api/trends/{trendFoodId}
     * Get a specific trending food
     */
    @GetMapping("/{trendFoodId}")
    public ResponseEntity<ResponseDTO<TrendFoodDTO>> getTrendingFood(@PathVariable Long trendFoodId) {
        log.info("Fetching trending food - ID: {}", trendFoodId);

        // Implementation would fetch by ID
        // For now, return success response
        return ResponseEntity.ok(ResponseDTO.success(null, "Trending food fetched"));
    }

}
